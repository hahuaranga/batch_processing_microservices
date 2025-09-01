package com.example.batch.core.service;

import com.example.batch.core.domain.ProcessingRequest;
import com.example.batch.core.port.input.ProcessRequestUseCase;
import com.example.batch.core.port.output.BatchProcessingPort;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:26:06
 * File: ProcessRequestService.java
 */

@Service
@RequiredArgsConstructor
public class ProcessRequestService implements ProcessRequestUseCase {

    private final BatchProcessingPort batchProcessingPort;

    @Async("asyncTaskExecutor") // ✅ Usar executor específico
    @Override
    public CompletableFuture<Void> processAsync(ProcessingRequest request) {
        try {
            batchProcessingPort.processBatch(request);
            return CompletableFuture.completedFuture(null);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
