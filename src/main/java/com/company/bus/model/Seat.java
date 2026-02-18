/*
 * @created 18/02/2026 - 11:16 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.model;

import java.util.HashSet;
import java.util.Set;

public class Seat {

    private final String seatNumber;
    private final Set<String> bookedSegments = new HashSet<>();

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable(String segment) {
        return !bookedSegments.contains(segment);
    }

    public void bookSegment(String segment) {
        bookedSegments.add(segment);
    }
}