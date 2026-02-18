/*
 * @created 18/02/2026 - 11:17 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.model;

import java.util.List;

public class Reservation {

    private String ticketNumber;
    private Journey journey;
    private List<String> seatNumbers;
    private int totalPrice;

    public Reservation(String ticketNumber, Journey journey,
                       List<String> seatNumbers, int totalPrice) {
        this.ticketNumber = ticketNumber;
        this.journey = journey;
        this.seatNumbers = seatNumbers;
        this.totalPrice = totalPrice;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public Journey getJourney() {
        return journey;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
