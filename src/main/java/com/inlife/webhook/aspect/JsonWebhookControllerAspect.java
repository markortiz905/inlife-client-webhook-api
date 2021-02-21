package com.inlife.webhook.aspect;

import com.inlife.webhook.common.SuccessResponse;
import com.inlife.webhook.dto.ClientRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class JsonWebhookControllerAspect {


    @Before(value = "execution(* com.inlife.webhook.controllers.JsonWebhookController.createClientEntry(..)) " +
            "and args(request)")
    public void createAdviceBefore(JoinPoint joinPoint, ClientRequestDto request) {
        log.info("Inbound ClientRequestDto Started  - " + joinPoint.getSignature());
    }

    @AfterReturning(
            pointcut="execution(* com.inlife.webhook.controllers.JsonWebhookController.createClientEntry(..))",
            returning="jsonWebhook")
    public void createAdviceAfterReturning(ResponseEntity<SuccessResponse> jsonWebhook) {
        log.info("Returning ClientRequestDto - status code:" + jsonWebhook.getStatusCodeValue());
    }
}