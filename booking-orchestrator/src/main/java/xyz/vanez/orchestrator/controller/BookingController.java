package xyz.vanez.orchestrator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vanez.common.messages.booking.BookingRequest;
import xyz.vanez.orchestrator.service.OrchestrationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final OrchestrationService orchestrationService;

    public BookingController(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest request) {
        if (request.getClientId() == null || request.getBookingId() == null) {
            log.error("Invalid request: clientId or bookingId is null");
            return ResponseEntity.badRequest().body("clientId and bookingId are required");
        }
        orchestrationService.startBookingProcess(request);
        return ResponseEntity.accepted().body("Booking process started");
    }
}