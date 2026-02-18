/*
 * @created 18/02/2026 - 11:19 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import java.util.HashMap;
import java.util.Map;

public class PricingService {

    private static final Map<String, Integer> PRICE_MAP = new HashMap<>();

    static {
        PRICE_MAP.put("A-B", 50);
        PRICE_MAP.put("A-C", 100);
        PRICE_MAP.put("A-D", 150);
        PRICE_MAP.put("B-C", 50);
        PRICE_MAP.put("B-D", 100);
        PRICE_MAP.put("C-D", 50);

        PRICE_MAP.put("D-C", 50);
        PRICE_MAP.put("D-B", 100);
        PRICE_MAP.put("D-A", 150);
        PRICE_MAP.put("C-B", 50);
        PRICE_MAP.put("C-A", 100);
        PRICE_MAP.put("B-A", 50);
    }

    public int getPrice(String origin, String destination) {
        String key = origin + "-" + destination;
        Integer price = PRICE_MAP.get(key);

        if (price == null) {
            throw new IllegalArgumentException(
                    "Invalid journey: " + origin + " -> " + destination
            );
        }
        return price;
    }

    public int getTotalPrice(String origin, String destination, int passengers) {
        return getPrice(origin, destination) * passengers;
    }
}
