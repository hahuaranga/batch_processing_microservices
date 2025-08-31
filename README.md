Test

curl --location 'http://localhost:8080/api/process' \
--header 'Content-Type: application/json' \
--data '{
    "accessid": "abc123xyz",
    "fechahora": "2025-04-05T14:30:45Z",
    "origen": "raddius",
    "tipo": "individual",
    "EstadoPPoE": "Active",
    "EstadoInventario": "Ocupado",
    "Agencia": "La Florida",
    "UltimoRadiusInterim": "2025-08-13 20:58:07",
    "UltimoRadiusStart": "2025-08-13 20:58:02",
    "UltimoRadiusStop": "2025-08-13 20:58:02",
    "data": "WyIwMDAwMDAwIiwiMDAwMDAwMSIsIjAwMDAwMDIiLCIwMDAwMDAzIiwiMDAwMDAwNCIsIjAwMDAwMDUiLCIwMDAwMDA2IiwiMDAwMDAwNyIsIjAwMDAwMDgiLCIwMDAwMDA5IiwiMDAwMDAxMCIsIjAwMDAwMTEiLCIwMDAwMDEyIiwiMDAwMDAxMyIsIjAwMDAwMTQiLCIwMDAwMDE1IiwiMDAwMDAxNiIsIjAwMDAwMTciLCIwMDAwMDE4IiwiMDAwMDAxOSIsIjAwMDAwMjAiLCIwMDAwMDIxIiwiMDAwMDAyMiIsIjAwMDAwMjMiLCIwMDAwMDI0IiwiMDAwMDAyNSJd"
  }'
