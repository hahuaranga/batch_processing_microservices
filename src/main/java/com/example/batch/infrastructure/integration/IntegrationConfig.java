package com.example.batch.infrastructure.integration;

import com.example.batch.core.domain.ProcessingRequest;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
                log.debug("=== PROCESSING ACCESS ID ===");
                log.debug("AccessID: " + accessId);
                log.debug("Request AccessID: " + request.accessId());
                log.debug("FechaHora: " + request.fechaHora());
                log.debug("Origen: " + request.origen());
                log.debug("Tipo: " + request.tipo());
                log.debug("EstadoPPoE: " + request.estadoPPoE());
                log.debug("EstadoInventario: " + request.estadoInventario());
                log.debug("Agencia: " + request.agencia());
                log.debug("UltimoRadiusInterim: " + request.ultimoRadiusInterim());
                log.debug("UltimoRadiusStart: " + request.ultimoRadiusStart());
                log.debug("UltimoRadiusStop: " + request.ultimoRadiusStop());
                log.debug("Total AccessIDs: " + request.accessIds().size());
                log.debug("============================");
            })
            .get();
    }
}
