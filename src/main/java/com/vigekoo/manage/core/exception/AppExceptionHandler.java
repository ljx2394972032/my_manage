package com.vigekoo.manage.core.exception;

import com.vigekoo.manage.core.response.moreMsg.ResponseMoreMsg;
import com.vigekoo.manage.utils.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ljx
 * @Description: (异常处理器)
 * @date 2017-6-23 15:07
 */
@RestControllerAdvice
public class AppExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异常
     */
    @ExceptionHandler(AppException.class)
    public Result handleAppException(AppException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return Result.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseMoreMsg RuntimeException(RuntimeException e) {
        log.error("运行异常:" + e.getMessage(), e);
        return ResponseMoreMsg.ERR("系统错误!", "System error!").build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseMoreMsg Exception(Exception e) {
        log.error("系统异常:" + e.getMessage(), e);
        return ResponseMoreMsg.ERR("系统错误!", "System error!").build();
    }
}
