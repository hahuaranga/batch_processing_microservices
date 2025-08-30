package com.example.batch.infrastructure.bacth;

import com.example.batch.core.domain.ProcessingRequest;
import com.example.batch.core.port.output.BatchProcessingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:33:48
 * File: BatchProcessingAdapter.java
 */

@Component
@RequiredArgsConstructor
public class BatchProcessingAdapter implements BatchProcessingPort {

    private final JobLauncher jobLauncher;
    
    private final Job accessIdProcessingJob;
    
    private final ProcessingRequestListener requestListener;

    @Override
    public void processBatch(ProcessingRequest request) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

            // ✅ Configurar el request en el listener
            requestListener.setCurrentRequest(request);
            
            // Ejecutar job
            jobLauncher.run(accessIdProcessingJob, jobParameters);
            
        } catch (Exception e) {
            throw new RuntimeException("Error iniciando batch processing", e);
        }
    }
}
