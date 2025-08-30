package com.example.batch.core.service;

import com.example.batch.core.domain.ProcessingRequest;
import com.example.batch.core.port.input.ProcessRequestUseCase;
import com.example.batch.core.port.output.BatchProcessingPort;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void processAsync(ProcessingRequest request) {
        batchProcessingPort.processBatch(request);
    }
}
