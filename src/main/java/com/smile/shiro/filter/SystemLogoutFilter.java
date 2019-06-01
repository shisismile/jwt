package com.smile.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.smile.param.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * TODO
 *
 * @author shimingen
 * @date 2019/5/30 17:45
 */
@Slf4j
public class SystemLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        try {
            subject.logout();
        } catch (Exception ex) {
            log.error("退出登录错误",ex);
        }
        this.writeResult(response);
        //不执行后续的过滤器
        return false;
    }

    private void writeResult(ServletResponse response){
        //响应Json结果
        PrintWriter out = null;
        try {
            out = response.getWriter();
            Result result = Result.build().ok();
            out.append(JSONObject.toJSONString(result));
        } catch (IOException e) {
            log.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}