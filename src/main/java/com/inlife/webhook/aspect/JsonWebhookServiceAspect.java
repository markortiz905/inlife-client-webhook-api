package com.inlife.webhook.aspect;

import com.inlife.webhook.dto.ClientRequestDto;
import com.inlife.webhook.entities.JsonWebhook;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class JsonWebhookServiceAspect {

    @Before(value = "execution(* com.inlife.webhook.services.JsonWebhookService.saveAsync(..)) " +
            "and args(objectMap)")
    public void createAdviceBefore(JoinPoint joinPoint, Map<String, Object> objectMap) {
        log.info("saveAsync service started  - " + joinPoint.getSignature());
    }

    @AfterReturning(
            pointcut="execution(* com.inlife.webhook.services.JsonWebhookService.saveAsync(..))",
            returning="computableObject")
    public void createAdviceAfterReturning(JoinPoint joinPoint, CompletableFuture<Void> computableObject) {
        log.info("saveAsync service ended - " + joinPoint.getSignature());
    }

    @Before(value = "execution(* com.inlife.webhook.services.JsonWebhookService.saveEntry(..)) " +
            "and args(objectMap)")
    public void createAdviceBefore1(JoinPoint joinPoint, Map<String, Object> objectMap) {
        log.info("saveEntry service started  - " + joinPoint.getSignature());
    }

    @AfterReturning(
            pointcut="execution(* com.inlife.webhook.services.JsonWebhookService.saveEntry(..))",
            returning="computableObject")
    public void createAdviceAfterReturning1(JoinPoint joinPoint, CompletableFuture<Void> computableObject) {
        log.info("saveEntry service ended - " + joinPoint.getSignature());
    }

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