package com.inlife.webhook.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inlife.webhook.common.SuccessResponse;
import com.inlife.webhook.dto.ClientRequestDto;
import com.inlife.webhook.exception.BadRequestServiceException;
import com.inlife.webhook.exception.ServiceException;
import com.inlife.webhook.services.JsonWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mark ortiz
 */
@Slf4j
@RestController
public class JsonWebhookController {

    @Autowired
    private JsonWebhookService jsonWebhookService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/webhook/client")
    ResponseEntity<SuccessResponse> createClientEntry(HttpEntity<String> rawJson) throws ServiceException, BadRequestServiceException, JsonProcessingException {
        ClientRequestDto request = mapper.readValue(rawJson.getBody(), ClientRequestDto.class);
        jsonWebhookService.saveClientAsync(request, rawJson.getBody());
        SuccessResponse response = new SuccessResponse();
        response.setStatus("success");
        return ResponseEntity.ok(response);
    }
}
