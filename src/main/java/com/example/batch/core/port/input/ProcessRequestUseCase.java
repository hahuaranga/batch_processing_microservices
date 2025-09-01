package com.example.batch.core.port.input;

import java.util.concurrent.CompletableFuture;

import com.example.batch.core.domain.ProcessingRequest;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:24:04
 * File: ProcessRequestUseCase.java
 */

public interface ProcessRequestUseCase {
	CompletableFuture<Void> processAsync(ProcessingRequest request);
}
