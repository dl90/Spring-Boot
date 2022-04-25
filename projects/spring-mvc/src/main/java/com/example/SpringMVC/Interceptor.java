package com.example.SpringMVC;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class Interceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LookupService.class);

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {

        logger.info("req: {} {}", req.getRemoteHost(), req.getContextPath());
        logger.info("res: {} {}", res.getStatus(), res.getLocale());
        logger.info("handler: {}", handler);
        return true;
    }

    @Override
    // before view
    public void postHandle(HttpServletRequest req,
                           HttpServletResponse res,
                           Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest req,
                                HttpServletResponse res,
                                Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
