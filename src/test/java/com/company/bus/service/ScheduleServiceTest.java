/*
 * @created 18/02/2026 - 9:35 PM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScheduleServiceTest {

    private final ScheduleService scheduleService = new ScheduleService();

    @Test
    void shouldReturnCorrectDepartureTimeForForwardJourney() {
        assertEquals("06:00", scheduleService.getDepartureTime("A", "D"));
        assertEquals("07:00", scheduleService.getDepartureTime("B", "D"));
    }

    @Test
    void shouldReturnCorrectArrivalTimeForForwardJourney() {
        assertEquals("08:00", scheduleService.getArrivalTime("A", "C"));
        assertEquals("09:00", scheduleService.getArrivalTime("A", "D"));
    }

    @Test
    void shouldReturnCorrectTimesForReturnJourney() {
        assertEquals("14:00", scheduleService.getDepartureTime("D", "A"));
        assertEquals("17:00", scheduleService.getArrivalTime("D", "A"));
    }

    @Test
    void shouldThrowForUnknownStop() {
        assertThrows(IllegalArgumentException.class,
                () -> scheduleService.getDepartureTime("X", "A"));
    }
}