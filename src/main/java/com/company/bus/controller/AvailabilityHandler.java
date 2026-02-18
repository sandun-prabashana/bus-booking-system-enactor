/*
 * @created 18/02/2026 - 11:00 AM
 * @project bus-reservation-system
 * @author sandun_p
 */

package com.company.bus.controller;

import com.company.bus.service.JourneyService;
import com.company.bus.service.PricingService;
import com.company.bus.service.ScheduleService;
import com.company.bus.service.SeatService;
import com.company.bus.util.UtilMethods;
import com.company.bus.util.Varlist;
import static com.company.bus.server.ServiceRegistry.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailabilityHandler implements HttpHandler {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final SeatService     seatService     = seatService();
    private final PricingService  pricingService  = pricingService();
    private final JourneyService  journeyService  = journeyService();
    private final ScheduleService scheduleService = scheduleService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        InputStream requestBody = exchange.getRequestBody();
        Map<String, Object> request;
        try {
            request = mapper.readValue(requestBody, Map.class);
        } catch (Exception e) {
            UtilMethods.sendError(exchange, 400, "Invalid JSON request body");
            return;
        }

        String origin      = request.getOrDefault("origin",      "").toString().toUpperCase().trim();
        String destination = request.getOrDefault("destination", "").toString().toUpperCase().trim();

        if (origin.isEmpty() || destination.isEmpty()) {
            UtilMethods.sendError(exchange, 400, "Origin and destination are required");
            return;
        }

        if (origin.equalsIgnoreCase(destination)) {
            UtilMethods.sendError(exchange, 400, "Origin and destination cannot be the same");
            return;
        }

        int passengers;
        try {
            passengers = (int) request.get("passengers");
        } catch (Exception e) {
            UtilMethods.sendError(exchange, 400, "Invalid or missing passengers field");
            return;
        }

        if (passengers <= 0 || passengers > Varlist.MAX_SEATS) {
            UtilMethods.sendError(exchange, 400,
                    "Invalid passenger count. Must be between 1 and " + Varlist.MAX_SEATS + ".");
            return;
        }

        int pricePerTicket;
        int totalPrice;
        try {
            pricePerTicket = pricingService.getPrice(origin, destination);
            totalPrice     = pricingService.getTotalPrice(origin, destination, passengers);
        } catch (IllegalArgumentException e) {
            UtilMethods.sendError(exchange, 400, "Invalid route: " + origin + " -> " + destination);
            return;
        }

        List<String> segments      = journeyService.getSegments(origin, destination);
        List<String> availableSeats = seatService.getAvailableSeats(segments, passengers);

        boolean available = availableSeats.size() == passengers;

        String departureTime = scheduleService.getDepartureTime(origin, destination);
        String arrivalTime   = scheduleService.getArrivalTime(origin, destination);

        Map<String, Object> schedule = new HashMap<>();
        schedule.put("departureTime", departureTime);
        schedule.put("arrivalTime",   arrivalTime);

        Map<String, Object> response = new HashMap<>();
        response.put("available",      available);
        response.put("pricePerTicket", pricePerTicket);
        response.put("totalPrice",     totalPrice);
        response.put("availableSeats", availableSeats);
        response.put("schedule",       schedule);

        byte[] responseBytes = mapper.writeValueAsBytes(response);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}