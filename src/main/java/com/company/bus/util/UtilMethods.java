/*
 * @created 18/02/2026 - 11:51 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UtilMethods {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendError(HttpExchange exchange, int statusCode, String message)
            throws IOException {

        Map<String, String> error = new HashMap<>();
        error.put("error", message);

        byte[] response = mapper.writeValueAsBytes(error);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

}
