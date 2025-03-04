package com.test.api.marvel.web.interceptor;

import com.test.api.marvel.exception.ApiErrorException;
import com.test.api.marvel.persistence.entity.UserInteractionLog;
import com.test.api.marvel.persistence.repository.UserInteractionLogRepository;
import com.test.api.marvel.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class UserInteractionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInteractionLogRepository userInteractionLogRepository;

    @Autowired
    @Lazy
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("UserInteractionInterceptor preHandle");

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/comics") || requestURI.startsWith("/characters")) {

            UserInteractionLog userInteractionLog = new UserInteractionLog();
            userInteractionLog.setMethod(request.getMethod());
            userInteractionLog.setRemoteAddress(request.getRemoteAddr());
            userInteractionLog.setTimestamp(LocalDateTime.now());
            userInteractionLog.setUrl(requestURI);
            userInteractionLog.setUsername(authenticationService.getUserLoggedIn().getUsername());

            try {
                userInteractionLogRepository.save(userInteractionLog);
                return true;
            } catch (Exception ex) {
                throw new ApiErrorException("No se logro guardar el log");
            }
        }

        return true;
    }
}