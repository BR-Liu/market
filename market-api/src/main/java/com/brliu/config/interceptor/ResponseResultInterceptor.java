package com.brliu.config.interceptor;

import com.brliu.annotation.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String PACK_RESULT = "PACK_RESULT";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(PACK_RESULT, clazz.getAnnotation(ResponseResult.class));
            }
            if (method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(PACK_RESULT, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }
}
