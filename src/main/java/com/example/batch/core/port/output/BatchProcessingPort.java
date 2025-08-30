package com.example.batch.core.port.output;

import com.example.batch.core.domain.ProcessingRequest;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:28:14
 * File: BatchProcessingPort.java
 */

public interface BatchProcessingPort {
    void processBatch(ProcessingRequest request);
}
