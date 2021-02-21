package com.inlife.webhook.services;

import com.inlife.webhook.dto.ClientRequestDto;
import com.inlife.webhook.exception.BadRequestServiceException;
import com.inlife.webhook.exception.ServiceException;

import java.util.concurrent.CompletableFuture;

public interface JsonWebhookService {

    public CompletableFuture<Void> saveClientAsync(ClientRequestDto requestDto, String rawJson) throws ServiceException, BadRequestServiceException;

}
