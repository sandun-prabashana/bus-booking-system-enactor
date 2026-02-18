/*
 * @created 18/02/2026 - 11:36 AM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import java.util.ArrayList;
import java.util.List;

public class JourneyService {

    public List<String> getSegments(String origin, String destination) {

        List<String> segments = new ArrayList<>();

        if (origin.compareTo(destination) < 0) {
            char start = origin.charAt(0);
            char end = destination.charAt(0);

            for (char c = start; c < end; c++) {
                segments.add(c + "-" + (char)(c + 1));
            }
        }else {
            char start = origin.charAt(0);
            char end = destination.charAt(0);

            for (char c = start; c > end; c--) {
                segments.add(c + "-" + (char)(c - 1));
            }
        }

        return segments;
    }
}
