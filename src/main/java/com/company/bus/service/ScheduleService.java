/*
 * @created 18/02/2026 - 9:34 PM
 * @project bus-reservation-system
 * @author sandun_p
 */


package com.company.bus.service;

import java.util.HashMap;
import java.util.Map;


public class ScheduleService {

    private static final Map<String, String> FORWARD_TIMES = new HashMap<>();
    private static final Map<String, String> RETURN_TIMES  = new HashMap<>();

    static {
        FORWARD_TIMES.put("A", "06:00");
        FORWARD_TIMES.put("B", "07:00");
        FORWARD_TIMES.put("C", "08:00");
        FORWARD_TIMES.put("D", "09:00");

        RETURN_TIMES.put("D", "14:00");
        RETURN_TIMES.put("C", "15:00");
        RETURN_TIMES.put("B", "16:00");
        RETURN_TIMES.put("A", "17:00");
    }


    public String getDepartureTime(String origin, String destination) {
        boolean isForward = origin.compareTo(destination) < 0;
        Map<String, String> times = isForward ? FORWARD_TIMES : RETURN_TIMES;
        String time = times.get(origin);
        if (time == null) throw new IllegalArgumentException("Unknown stop: " + origin);
        return time;
    }


    public String getArrivalTime(String origin, String destination) {
        boolean isForward = origin.compareTo(destination) < 0;
        Map<String, String> times = isForward ? FORWARD_TIMES : RETURN_TIMES;
        String time = times.get(destination);
        if (time == null) throw new IllegalArgumentException("Unknown stop: " + destination);
        return time;
    }
}
