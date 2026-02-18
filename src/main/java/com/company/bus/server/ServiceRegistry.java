/*
 * @created 18/02/2026 - 12:05 PM
 * @project bus-reservation-system
 * @author sandun_p
 */

package com.company.bus.server;

import com.company.bus.service.JourneyService;
import com.company.bus.service.PricingService;
import com.company.bus.service.ScheduleService;
import com.company.bus.service.SeatService;

public class ServiceRegistry {

    private static final SeatService     SEAT_SERVICE     = new SeatService();
    private static final PricingService  PRICING_SERVICE  = new PricingService();
    private static final JourneyService  JOURNEY_SERVICE  = new JourneyService();
    private static final ScheduleService SCHEDULE_SERVICE = new ScheduleService();

    private ServiceRegistry() {}

    public static SeatService     seatService()     { return SEAT_SERVICE;     }
    public static PricingService  pricingService()  { return PRICING_SERVICE;  }
    public static JourneyService  journeyService()  { return JOURNEY_SERVICE;  }
    public static ScheduleService scheduleService() { return SCHEDULE_SERVICE; }
}