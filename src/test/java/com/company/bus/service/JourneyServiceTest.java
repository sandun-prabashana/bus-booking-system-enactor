/*
 * @created 18/02/2026 - 12:22 PM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JourneyServiceTest {

    private final JourneyService journeyService = new JourneyService();

    @Test
    void shouldReturnSegmentsForForwardJourney() {
        List<String> segments =
                journeyService.getSegments("A", "C");

        assertEquals(Arrays.asList("A-B", "B-C"), segments);
    }

    @Test
    void shouldReturnSegmentsForReturnJourney() {
        List<String> segments =
                journeyService.getSegments("D", "B");

        assertEquals(Arrays.asList("D-C", "C-B"), segments);
    }

}
