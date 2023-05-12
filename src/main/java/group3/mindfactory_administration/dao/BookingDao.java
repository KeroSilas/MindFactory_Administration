package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.SQLException;

public interface BookingDao {

    void editBooking (Booking booking) throws SQLException;

    void deleteBooking (int bookingID);

}
