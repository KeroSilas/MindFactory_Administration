package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingDaoImplTest {

    @DisplayName("Test valid countBookingsByOrg method")
    @Test
    void countBookingsByOrgValid() {
        BookingDao bookingDao = new BookingDaoImpl();

        HashMap<String, Integer> map = bookingDao.countBookingsByOrg(LocalDate.now().minusDays(365), LocalDate.now().plusDays(365));

        assert(map.size() > 0);
    }

    @DisplayName("Test valid time period of editBooking method")
    @Test
    void editBookingValid() {
        BookingDao bookingDao = new BookingDaoImpl();
        List<Booking> bookingList = bookingDao.getAllBookings();

        Booking booking1 = bookingList.get(0);
        booking1.setStartDate(LocalDate.now());
        booking1.setStartTime(LocalTime.parse("10:00"));
        booking1.setEndTime(LocalTime.parse("12:00"));
        Booking booking2 = bookingList.get(1);
        booking2.setStartDate(LocalDate.now());
        booking2.setStartTime(LocalTime.parse("12:00"));
        booking2.setEndTime(LocalTime.parse("14:00"));

        assertDoesNotThrow(() -> {
            bookingDao.editBooking(booking1);
            bookingDao.editBooking(booking2);
        });
    }

    @DisplayName("Test invalid time period of editBooking method")
    @Test
    void editBookingInvalid() {
        BookingDao bookingDao = new BookingDaoImpl();
        List<Booking> bookingList = bookingDao.getAllBookings();

        Booking booking1 = bookingList.get(0);
        booking1.setStartDate(LocalDate.now());
        booking1.setStartTime(LocalTime.parse("10:00"));
        booking1.setEndTime(LocalTime.parse("12:00"));
        Booking booking2 = bookingList.get(1);
        booking2.setStartDate(LocalDate.now());
        booking2.setStartTime(LocalTime.parse("10:00"));
        booking2.setEndTime(LocalTime.parse("12:00"));

        assertThrows(SQLException.class, () -> {
            bookingDao.editBooking(booking1);
            bookingDao.editBooking(booking2);
        });
    }

    @DisplayName("Test invalid bookingID in deleteBooking method")
    @Test
    void deleteBookingInvalid() {
        BookingDao bookingDao = new BookingDaoImpl();

        assertThrows(SQLException.class, () -> {
            bookingDao.deleteBooking(0);
        });
    }
}