package com.inlife.webhook.aspect;

import com.inlife.webhook.dto.ClientRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class JsonWebhookServiceAspect {

    @Before(value = "execution(* com.inlife.webhook.services.JsonWebhookService.saveClientAsync(..)) " +
            "and args(request)")
    public void saveClientAsyncBefore(JoinPoint joinPoint, ClientRequestDto request) {
        log.info("saveClientAsync service started  - item_id: " + request.getItem_id());
    }

    @AfterReturning(
            pointcut="execution(* com.inlife.webhook.services.JsonWebhookService.saveClientAsync(..))",
            returning="computableObject")
    public void saveClientAsyncAfter(JoinPoint joinPoint, CompletableFuture<Void> computableObject) {
        log.info("saveClientAsync service ended - " + joinPoint.getSignature());
    }
}