package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface BookingDao {

    List<Booking> getAllBookings();

    HashMap<String, Integer> countBookingsByOrg();

    HashMap<String, Integer> countBookingsByActivity();

    void editBooking (Booking booking) throws SQLException;

    void deleteBooking (int bookingID) throws SQLException;

}
