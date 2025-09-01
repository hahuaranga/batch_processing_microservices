package com.example.batch.infrastructure.bacth;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:47:53
 * File: CustomAccessIdReader.java
 */

@Slf4j
@Component
@StepScope // ✅ CRUCIAL: Para acceder al StepExecution
public class CustomAccessIdReader implements ItemReader<String> {

    private List<String> accessIds;
    
    private int currentIndex = 0;

    // ✅ Inyección del ExecutionContext via Spring Expression Language (SpEL)
    @Value("#{stepExecution.jobExecution.executionContext}")
    private ExecutionContext executionContext;

    @Override
    public String read() {
        // Inicializar si es la primera vez
        if (accessIds == null) {
            initializeAccessIds();
        }

        // Leer el siguiente elemento
        if (currentIndex < accessIds.size()) {
            String accessId = accessIds.get(currentIndex);
            currentIndex++;
            return accessId;
        }
        
        // Fin de la lectura
        return null;
    }

    @SuppressWarnings("unchecked")
	private void initializeAccessIds() {
        // ✅ Obtener la lista desde ExecutionContext
        accessIds = (List<String>) executionContext.get("accessIds");
        
        if (accessIds == null) {
            accessIds = List.of();
            log.info("⚠️ No se encontraron accessIds en ExecutionContext");
        } else {
        	log.info("✅ Reader inicializado con " + accessIds.size() + " AccessIds");
        }
    }

    // Método para testing
    public void reset() {
        accessIds = null;
        currentIndex = 0;
    }
}
