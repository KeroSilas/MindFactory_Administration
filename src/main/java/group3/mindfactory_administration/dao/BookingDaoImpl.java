package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private final DatabaseConnector databaseConnector;

    public BookingDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    // Get all booking data from the database and return it as a HashMap with BookingTime as key and Booking as value
    // This method is used to populate the calendar with bookings
    @Override
    public HashMap<BookingTime, Booking> getBookings() {

        HashMap<BookingTime, Booking> bookings = new HashMap<>();
        List<BookingTime> tempBookingTimes = new ArrayList<>();
        List<Booking> tempBookings = new ArrayList<>();

        try (Connection con = databaseConnector.getConnection()) {
            // Get the bookings
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Booking;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("bookingID"),
                        rs.getString("package"),
                        rs.getString("activity"),
                        rs.getString("organization"),
                        rs.getString("åbenSkoleForløb"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("assistance"),
                        rs.getString("transportType"),
                        rs.getString("transportArrival"),
                        rs.getString("transportDeparture"),
                        rs.getInt("participants"),
                        rs.getTimestamp("bookingDate").toLocalDateTime(),
                        rs.getString("messageToAS"),
                        rs.getString("personalNote"),
                        rs.getString("bookingType")
                );
                tempBookings.add(booking);
            }

            // Get the bookingEquipment and add it to the booking
            ps = con.prepareStatement("SELECT * FROM BookingEquipment;");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (Booking booking : tempBookings) {
                    if (rs.getInt("bookingID") == booking.getBookingID()) {
                        booking.getEquipmentList().add(rs.getString("equipment"));
                    }
                }
            }

            // Get the bookingFiles and add it to the booking
            ps = con.prepareStatement("SELECT * FROM BookingFiles;");
            rs = ps.executeQuery();
            while (rs.next()) {
                for (Booking booking : tempBookings) {
                    if (rs.getInt("bookingID") == booking.getBookingID()) {
                        booking.getBookingFilesList().add(new File(rs.getString("filePath")));
                    }
                }
            }

            // Get the bookingTimes
            ps = con.prepareStatement("SELECT * FROM BookingTimes;");
            rs = ps.executeQuery();
            while (rs.next()) {
                BookingTime bookingTime = new BookingTime(
                        rs.getDate("startDate").toLocalDate(),
                        rs.getTime("startTime").toLocalTime(),
                        rs.getTime("endTime").toLocalTime(),
                        rs.getBoolean("isWholeDay"),
                        rs.getBoolean("isHalfDayEarly"),
                        rs.getBoolean("isNoShow"),
                        rs.getInt("bookingID")
                );
                tempBookingTimes.add(bookingTime);
            }

            // Put the bookingTime and booking together in a HashMap
            for (BookingTime bookingTime : tempBookingTimes) {
                for (Booking booking : tempBookings) {
                    if (bookingTime.getBookingID() == booking.getBookingID()) {
                        bookings.put(bookingTime, booking);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("cannot get bookings " + e.getMessage());
        }
        return bookings;
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
    public void deleteBooking(int bookingID) throws SQLException {
        int rowsAffected;
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Booking WHERE BookingID = ?");
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate(); // https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java

            if (rowsAffected == 0) {
                throw new RuntimeException("Booking with ID " + bookingID + " does not exist");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}


