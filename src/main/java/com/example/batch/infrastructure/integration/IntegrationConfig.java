package com.example.batch.infrastructure.integration;

import com.example.batch.core.domain.ProcessingRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:40:15
 * File: IntegrationConfig.java
 */

@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    MessageChannel processingChannel() {
        return new DirectChannel();
    }

    @Bean
    IntegrationFlow processingFlow() {
        return IntegrationFlow.from(processingChannel())
            .handle(message -> {
                String accessId = (String) message.getPayload();
                ProcessingRequest request = (ProcessingRequest) message.getHeaders()
                    .get("processingRequest");
                
                // Loggear toda la información
                System.out.println("=== PROCESSING ACCESS ID ===");
                System.out.println("AccessID: " + accessId);
                System.out.println("Request AccessID: " + request.accessId());
                System.out.println("FechaHora: " + request.fechaHora());
                System.out.println("Origen: " + request.origen());
                System.out.println("Tipo: " + request.tipo());
                System.out.println("EstadoPPoE: " + request.estadoPPoE());
                System.out.println("EstadoInventario: " + request.estadoInventario());
                System.out.println("Agencia: " + request.agencia());
                System.out.println("UltimoRadiusInterim: " + request.ultimoRadiusInterim());
                System.out.println("UltimoRadiusStart: " + request.ultimoRadiusStart());
                System.out.println("UltimoRadiusStop: " + request.ultimoRadiusStop());
                System.out.println("Total AccessIDs: " + request.accessIds().size());
                System.out.println("============================");
            })
            .get();
    }
}
