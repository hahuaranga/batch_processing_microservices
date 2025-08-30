package com.example.batch.infrastructure.bacth;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.batch.core.JobExecution;
import com.example.batch.core.domain.ProcessingRequest;
import org.springframework.batch.item.ExecutionContext;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 06:27:44
 * File: ProcessingRequestListener.java
 */

@Component
public class ProcessingRequestListener implements JobExecutionListener {

    private ProcessingRequest currentRequest;

    public void setCurrentRequest(ProcessingRequest request) {
        this.currentRequest = request;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (currentRequest != null) {
            ExecutionContext executionContext = jobExecution.getExecutionContext();
            
            executionContext.put("accessIds", currentRequest.accessIds());
            executionContext.put("originalAccessId", currentRequest.accessId());
            executionContext.put("fechaHora", currentRequest.fechaHora().toString());
            executionContext.put("origen", currentRequest.origen());
            executionContext.put("tipo", currentRequest.tipo());
            executionContext.put("estadoPPoE", currentRequest.estadoPPoE());
            executionContext.put("estadoInventario", currentRequest.estadoInventario());
            executionContext.put("agencia", currentRequest.agencia());
            executionContext.put("ultimoRadiusInterim", currentRequest.ultimoRadiusInterim());
            executionContext.put("ultimoRadiusStart", currentRequest.ultimoRadiusStart());
            executionContext.put("ultimoRadiusStop", currentRequest.ultimoRadiusStop());
            
            System.out.println("Datos cargados en ExecutionContext para " + 
                              currentRequest.accessIds().size() + " AccessIds");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        currentRequest = null; // Limpiar
    }
}
