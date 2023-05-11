package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.*;

public class BookingDaoImpl implements BookingDao {

    private final DatabaseConnector databaseConnector;

    public BookingDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public void editBooking(Booking booking, BookingTime bookingTime) throws SQLException {
        Connection con = databaseConnector.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE Booking SET Catering = ?, Activity = ?, Organization = ?, ÅbenSkoleForløb = ?,FirstName = ?, LastName = ?, Position = ?, Afdeling = ?, Phone = ?, Email = ?,Assistance = ?, TransportType = ?, TransportArrival = ?,TransportDeparture = ?, Participants = ?, BookingDateTime = ?, MessageToAS = ?, PersonalNote = ?, BookingType = ? WHERE bookingID = ?);");

            ps.setString(1, booking.getCatering());
            ps.setString(2, booking.getActivity());
            ps.setString(3, booking.getOrganization());
            ps.setString(4, booking.getÅbenSkoleForløb());
            ps.setString(5, booking.getFirstName());
            ps.setString(6, booking.getLastName());
            ps.setString(7, booking.getPosition());
            ps.setString(8, booking.getAfdeling());
            ps.setString(9, booking.getPhone());
            ps.setString(10, booking.getEmail());
            ps.setString(11, booking.getAssistance());
            ps.setString(12, booking.getTransportType());
            ps.setString(13, booking.getTransportArrival());
            ps.setString(14, booking.getTransportDeparture());
            ps.setInt(15, booking.getParticipants());
            ps.setTimestamp(16, Timestamp.valueOf(booking.getBookingDateTime()));
            ps.setString(17, booking.getMessageToAS());
            ps.setString(18, booking.getPersonalNote());
            ps.setString(19, booking.getBookingType());
            ps.setInt(20, booking.getBookingID());
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("UPDATE BookingTimes SET (?,?,?,?,?,?,?);");

            ps2.setInt(1, booking.getBookingID());
            ps2.setDate(2, Date.valueOf(bookingTime.getDate()));
            ps2.setTime(3, Time.valueOf(bookingTime.getStartTime()));
            ps2.setTime(4, Time.valueOf(bookingTime.getEndTime()));
            ps2.setBoolean(5, bookingTime.isWholeDay());
            ps2.setBoolean(6, bookingTime.isHalfDayEarly());
            ps2.setBoolean(7, bookingTime.isNoShow());
            ps2.executeUpdate();

            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException e) {
            con.rollback();
            throw new SQLException(e);
        } finally {
            con.close();
        }
    }

    @Override
    public void deleteBooking(int bookingID) {
        int rowsAffected;
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Booking WHERE BookingID = ?");
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate(); // https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java

            if (rowsAffected == 0) {
                throw new RuntimeException("Booking with ID " + bookingID + " does not exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


