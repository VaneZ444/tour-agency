package xyz.vanez.orchestrator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vanez.common.messages.booking.BookingRequest;
import xyz.vanez.orchestrator.service.OrchestrationService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final OrchestrationService orchestrationService;

    public BookingController(OrchestrationService orchestrationService) {
        this.orchestrationService = orchestrationService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequest request) {
        orchestrationService.startBookingProcess(request);
        return ResponseEntity.accepted().body("Booking process started");
    }
}