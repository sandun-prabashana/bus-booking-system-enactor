/*
 * @created 18/02/2026 - 10:58 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.server;

import com.company.bus.controller.AvailabilityHandler;
import com.company.bus.controller.ReservationHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class BusReservationServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/availability", new AvailabilityHandler());
        server.createContext("/reserve", new ReservationHandler());

        server.start();

        System.out.println("Bus Reservation Server started on port 8080");
    }
}

