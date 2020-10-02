package com.health.controller;

import com.health.HealthException;
import com.health.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class HealthExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(HealthExceptionHandler.class);

    // try catch catch(异常的类型)
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e) {
        log.error("违反业务逻辑", e);
        return new Result(false, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("发生未知异常", e);
        return new Result(false, "操作失败，发生未知异常，请联系管理员");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e){
        log.error("莫得权限",e);
        return new Result(false, "权限不够");
    }
}
