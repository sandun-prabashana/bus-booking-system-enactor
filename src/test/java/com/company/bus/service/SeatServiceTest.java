/*
 * @created 18/02/2026 - 12:27 PM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SeatServiceTest {

    @Test
    void shouldReserveSeatsSuccessfully() {
        SeatService seatService = new SeatService();

        List<String> seats =
                seatService.reserveSeats(
                        Arrays.asList("A-B", "B-C"), 2);

        assertEquals(2, seats.size());
    }

    @Test
    void shouldNotAllowOverbooking() {
        SeatService seatService = new SeatService();

        List<String> seats =
                seatService.reserveSeats(
                        Arrays.asList("A-B"), 40);

        assertEquals(40, seats.size());

        List<String> overbook =
                seatService.reserveSeats(
                        Arrays.asList("A-B"), 1);

        assertTrue(overbook.isEmpty());
    }
}
