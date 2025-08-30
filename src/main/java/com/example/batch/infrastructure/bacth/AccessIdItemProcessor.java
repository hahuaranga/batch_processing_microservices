package com.example.batch.infrastructure.bacth;

import com.example.batch.core.domain.ProcessingRequest;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:38:13
 * File: AccessIdItemProcessor.java
 */

@Component
@StepScope // ✅ Para acceder al ExecutionContext
public class AccessIdItemProcessor implements ItemProcessor<String, String> {

    private final MessageChannel processingChannel;

    // ✅ Inyección del ExecutionContext
    @Value("#{stepExecution.jobExecution.executionContext}")
    private ExecutionContext executionContext;

    public AccessIdItemProcessor(MessageChannel processingChannel) {
        this.processingChannel = processingChannel;
    }

    @Override
    public String process(String accessId) throws Exception {
        // ✅ Reconstruir el ProcessingRequest desde ExecutionContext
        ProcessingRequest request = createProcessingRequestFromExecutionContext();
        
        // Enviar a Spring Integration
        processingChannel.send(MessageBuilder.withPayload(accessId)
            .setHeader("processingRequest", request)
            .build());
        
        System.out.println("📤 Procesando AccessId: " + accessId);
        return accessId;
    }

    private ProcessingRequest createProcessingRequestFromExecutionContext() {
        return new ProcessingRequest(
            (String) executionContext.get("originalAccessId"),
            LocalDateTime.parse((String) executionContext.get("fechaHora")),
            (String) executionContext.get("origen"),
            (String) executionContext.get("tipo"),
            (String) executionContext.get("estadoPPoE"),
            (String) executionContext.get("estadoInventario"),
            (String) executionContext.get("agencia"),
            (String) executionContext.get("ultimoRadiusInterim"),
            (String) executionContext.get("ultimoRadiusStart"),
            (String) executionContext.get("ultimoRadiusStop"),
            List.of() // La lista se maneja por separado en el reader
        );
    }
}
