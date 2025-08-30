package com.example.batch.infrastructure.rest;

import com.example.batch.core.domain.ProcessingRequest;
import com.example.batch.core.port.input.ProcessRequestUseCase;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:32:03
 * File: ProcessingController.java
 */

@RestController
@RequiredArgsConstructor
public class ProcessingController {

    private final ProcessRequestUseCase processRequestUseCase;

    @PostMapping("/api/process")
    public ResponseEntity<String> processRequest(@RequestBody String jsonRequest) {
        try {
            // Parsear JSON con JsonPath
            String accessId = JsonPath.read(jsonRequest, "$.accessid");
            String fechaHoraStr = JsonPath.read(jsonRequest, "$.fechahora");
            String origen = JsonPath.read(jsonRequest, "$.origen");
            String tipo = JsonPath.read(jsonRequest, "$.tipo");
            String estadoPPoE = JsonPath.read(jsonRequest, "$.EstadoPPoE");
            String estadoInventario = JsonPath.read(jsonRequest, "$.EstadoInventario");
            String agencia = JsonPath.read(jsonRequest, "$.Agencia");
            String ultimoRadiusInterim = JsonPath.read(jsonRequest, "$.UltimoRadiusInterim");
            String ultimoRadiusStart = JsonPath.read(jsonRequest, "$.UltimoRadiusStart");
            String ultimoRadiusStop = JsonPath.read(jsonRequest, "$.UltimoRadiusStop");
            String dataBase64 = JsonPath.read(jsonRequest, "$.data");

            // Decodificar Base64
            byte[] decodedBytes = Base64.getDecoder().decode(dataBase64);
            String dataJson = new String(decodedBytes);
            List<String> accessIds = JsonPath.read(dataJson, "$");

            // Convertir fecha
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, 
                DateTimeFormatter.ISO_DATE_TIME);

            // Crear dominio
            ProcessingRequest request = new ProcessingRequest(
                accessId, fechaHora, origen, tipo, estadoPPoE, estadoInventario,
                agencia, ultimoRadiusInterim, ultimoRadiusStart, ultimoRadiusStop, accessIds
            );
            
            // Procesar async
            processRequestUseCase.processAsync(request);

            return ResponseEntity.accepted().body("ACK - Procesamiento iniciado");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}
