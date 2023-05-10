package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.SQLException;
import java.util.HashMap;

public interface BookingDao {

    HashMap<BookingTime, Booking> getBookings();
    void editBooking (Booking booking, BookingTime bookingTime) throws SQLException;

    void deleteBooking (int bookingID) throws SQLException;

    void deleteBookingTime (int timeID) throws SQLException;

}
