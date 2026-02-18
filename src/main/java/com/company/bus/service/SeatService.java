/*
 * @created 18/02/2026 - 11:33 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import com.company.bus.model.Seat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeatService {

    private final List<Seat> seats = new ArrayList<>();
    private final Object seatLock = new Object();

    public SeatService() {
        initializeSeats();
    }

    private void initializeSeats() {
        for (int row = 1; row <= 10; row++) {
            seats.add(new Seat(row + "A"));
            seats.add(new Seat(row + "B"));
            seats.add(new Seat(row + "C"));
            seats.add(new Seat(row + "D"));
        }
    }


    public List<String> getAvailableSeats(List<String> segments, int passengers) {

        List<String> availableSeats = new ArrayList<>();

        synchronized (seatLock) {
            for (Seat seat : seats) {
                boolean free = true;

                for (String segment : segments) {
                    if (!seat.isAvailable(segment)) {
                        free = false;
                        break;
                    }
                }

                if (free) {
                    availableSeats.add(seat.getSeatNumber());
                }

                if (availableSeats.size() == passengers) {
                    break;
                }
            }
        }

        return availableSeats;
    }


    public List<String> reserveSeats(List<String> segments, int passengers) {

        synchronized (seatLock) {

            List<String> availableSeats = getAvailableSeats(segments, passengers);

            if (availableSeats.size() < passengers) {
                return Collections.emptyList();
            }

            for (Seat seat : seats) {
                if (availableSeats.contains(seat.getSeatNumber())) {
                    for (String segment : segments) {
                        seat.bookSegment(segment);
                    }
                }
            }

            return availableSeats;
        }
    }
}
