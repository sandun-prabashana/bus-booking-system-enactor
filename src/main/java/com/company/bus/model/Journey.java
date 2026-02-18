/*
 * @created 18/02/2026 - 11:19 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.model;

import java.util.List;

public class Journey {

    private String origin;
    private String destination;
    private List<String> segments;

    public Journey(String origin, String destination, List<String> segments) {
        this.origin = origin;
        this.destination = destination;
        this.segments = segments;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getSegments() {
        return segments;
    }
}
