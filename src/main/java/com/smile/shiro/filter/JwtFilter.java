package com.smile.shiro.filter;

import com.smile.model.sys.SysUser;
import com.smile.shiro.SecurityConsts;
import com.smile.shiro.token.JwtToken;
import com.smile.shiro.util.JwtUtil;
import com.smile.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:14
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {


    /**
     * 检测Header里Authorization字段
     * 判断是否登录
     */
    @Override
    public boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    /**
     * 登录验证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String authzHeader = super.getAuthzHeader(request);
        SysUser sysUser = JwtUtil.checkAndGetUser(authzHeader);
        if (sysUser == null) {
            return false;
        }
        JwtToken token = new JwtToken(authzHeader.replaceFirst("Bearer ", ""));
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        HttpServletRequest req = (HttpServletRequest) request;
//        sysUser.setIp(this.getIpAddress(req));
//        sysUser.setUri(req.getRequestURI());
//        sysUser.setFrom(req.getHeader("From"));
        UserContext.setSysUser(sysUser);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip = null;
        String unknown = "unknown";
        for (String header : headers) {
            ip = request.getHeader(header);
            if (StringUtils.isNotBlank(ip) && !unknown.equalsIgnoreCase(ip)){
                break;
            }
        }
        if (StringUtils.isBlank(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                log.error("登录失败!",e);
                return false;
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    public boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


}
