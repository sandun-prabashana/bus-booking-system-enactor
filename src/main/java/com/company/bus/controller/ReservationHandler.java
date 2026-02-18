/*
 * @created 18/02/2026 - 11:01 AM
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

public class ReservationHandler implements HttpHandler {

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
        int amountPaid;
        try {
            passengers = (int) request.get("passengers");
            amountPaid = (int) request.get("amountPaid");
        } catch (Exception e) {
            UtilMethods.sendError(exchange, 400, "Invalid or missing passengers/amountPaid field");
            return;
        }

        if (passengers <= 0 || passengers > Varlist.MAX_SEATS) {
            UtilMethods.sendError(exchange, 400,
                    "Invalid passenger count. Must be between 1 and " + Varlist.MAX_SEATS + ".");
            return;
        }

        int expectedAmount;
        try {
            expectedAmount = pricingService.getTotalPrice(origin, destination, passengers);
        } catch (IllegalArgumentException e) {
            UtilMethods.sendError(exchange, 400, "Invalid route: " + origin + " -> " + destination);
            return;
        }

        if (amountPaid != expectedAmount) {
            UtilMethods.sendError(exchange, 400,
                    "Invalid payment amount. Expected: " + expectedAmount);
            return;
        }

        List<String> segments   = journeyService.getSegments(origin, destination);
        List<String> bookedSeats = seatService.reserveSeats(segments, passengers);

        if (bookedSeats.isEmpty()) {
            UtilMethods.sendError(exchange, 409, "Not enough seats available.");
            return;
        }

        String ticketNumber   = "TKT-" + System.currentTimeMillis();
        String departureTime  = scheduleService.getDepartureTime(origin, destination);
        String arrivalTime    = scheduleService.getArrivalTime(origin, destination);

        Map<String, Object> journey = new HashMap<>();
        journey.put("origin",        origin);
        journey.put("destination",   destination);
        journey.put("departureTime", departureTime);
        journey.put("arrivalTime",   arrivalTime);

        Map<String, Object> response = new HashMap<>();
        response.put("ticketNumber", ticketNumber);
        response.put("seats",        bookedSeats);
        response.put("journey",      journey);
        response.put("totalPrice",   expectedAmount);

        byte[] responseBytes = mapper.writeValueAsBytes(response);

        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}