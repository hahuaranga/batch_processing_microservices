package com.example.batch.infrastructure.bacth;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import lombok.RequiredArgsConstructor;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:36:50
 * File: BatchConfig.java
 */

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final AccessIdItemProcessor accessIdItemProcessor;
    
    private final ItemReader<String> customAccessIdReader;

    @Value("${batch.chunk.size:100}")
    private int chunkSize;
    
    @Value("${batch.sleep.time:0}")
    private long sleepTime;
    
    @Bean
    Step processAccessIdStep(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   TaskExecutor batchTaskExecutor) {
        
        return new StepBuilder("processAccessIdStep", jobRepository)
            .<String, String>chunk(chunkSize, transactionManager)
            .reader(customAccessIdReader)
            .processor(accessIdItemProcessor)
            .writer(items -> {
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            })
            .taskExecutor(batchTaskExecutor) // Control de concurrencia aquí
            .build();
    }

    @Bean
    TaskExecutor batchTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Configuración para controlar la concurrencia
        executor.setCorePoolSize(5);        // Threads siempre activos
        executor.setMaxPoolSize(10);        // Máximo threads permitidos
        executor.setQueueCapacity(25);      // Items en cola antes de crear nuevos threads
        executor.setThreadNamePrefix("batch-");
        executor.initialize();
        
        return executor;
    }
    
    @Bean
    Job accessIdProcessingJob(JobRepository jobRepository, 
                                   Step processAccessIdStep,
                                   ProcessingRequestListener requestListener) {
        return new JobBuilder("accessIdProcessingJob", jobRepository)
            .start(processAccessIdStep)
            .listener(requestListener) // ✅ Agregar listener
            .build();
    }
    
    // Spring Boot ya crea un SimpleJobLauncher por defecto
}
