package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface BookingDao {

    List<Booking> getAllBookings();

    HashMap<String, Integer> countBookingsByOrg(LocalDate startDate, LocalDate endDate);

    HashMap<String, Integer> countBookingsByActivity(LocalDate startDate, LocalDate endDate);

    void editBooking (Booking booking) throws SQLException;

    void deleteBooking (int bookingID) throws SQLException;

    // https://stackoverflow.com/questions/36296140/subtract-two-dates-in-microsoft-sql-server
// https://stackoverflow.com/questions/37559741/convert-timestamp-to-date-in-oracle-sql
// https://www.sqlservercentral.com/articles/the-output-clause-for-update-statements
    List<Booking> getWeeksBookings();
}
