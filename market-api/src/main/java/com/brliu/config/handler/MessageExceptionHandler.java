package com.brliu.config.handler;


import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 全局自定义rest异常切面
 * @Author gosling
 * @CreateDate 2019/11/30 12:12 上午
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class MessageExceptionHandler {

    @ExceptionHandler(RestMessageException.class)
    public ResponseMessage messageExceptionHandler(RestMessageException exception) {
        log.warn("当前发生业务异常，发生的异常为：", exception);
        return ResponseMessage.of(exception.getState(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseMessage globalExceptionHandler(Exception exception) {
        log.error("当前发生系统异常，发生的异常为：", exception);
        return ResponseMessage.of(ResponseStateEnum.SERVER_ERROR, exception.getMessage());
    }
}
