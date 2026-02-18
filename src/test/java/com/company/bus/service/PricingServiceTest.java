/*
 * @created 18/02/2026 - 12:21 PM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private final PricingService pricingService = new PricingService();

    @Test
    void shouldCalculateForwardPrice() {
        assertEquals(100,
                pricingService.getPrice("A", "C"));
    }

    @Test
    void shouldCalculateReturnPrice() {
        assertEquals(100,
                pricingService.getPrice("C", "A"));
    }
}