package xyz.vanez.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.vanez.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
