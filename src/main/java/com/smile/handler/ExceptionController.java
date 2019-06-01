package com.smile.handler;

import com.smile.param.Result;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.smile.param.ResultStatusEnum.AUTHENTICATE_FAILED;
import static com.smile.param.ResultStatusEnum.INTER_ERROR;

/**
 * 统一异常处理
 * @author shimingen
 * @date 2019/5/30 17:58
 */
@RestControllerAdvice
public class ExceptionController {
    /**
     * 捕捉shiro的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return Result.build().error(AUTHENTICATE_FAILED);
    }

    /**
     * 捕捉UnauthorizedException
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handle401() {
        return Result.build().error(AUTHENTICATE_FAILED);
    }

    /**
     * 捕捉其他所有异常
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        return Result.build().error(INTER_ERROR).withMessage(ex.getMessage());
    }

}
