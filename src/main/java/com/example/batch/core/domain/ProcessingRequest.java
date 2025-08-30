package com.example.batch.core.domain;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: hahuaranga@indracompany.com
 * Created on: 30-08-2025 at 03:20:54
 * File: ProcessingRequest.java
 */

public record ProcessingRequest(
	    String accessId,
	    LocalDateTime fechaHora,
	    String origen,
	    String tipo,
	    String estadoPPoE,
	    String estadoInventario,
	    String agencia,
	    String ultimoRadiusInterim,
	    String ultimoRadiusStart,
	    String ultimoRadiusStop,
	    List<String> accessIds
	) {}
