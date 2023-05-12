package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private final DatabaseConnector databaseConnector;

    public BookingDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM Booking;"
            );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookingID = rs.getInt(1);
                String bookingType = rs.getString(2);
                String catering = rs.getString(3);
                String activity = rs.getString(4);
                String organizarion = rs.getString(5);
                String åbenSkoleForløb = rs.getString(6);
                String firstName = rs.getString(7);
                String lastName = rs.getString(8);
                String position = rs.getString(9);
                String department = rs.getString(10);
                String phone = rs.getString(11);
                String email = rs.getString(12);
                String assistance = rs.getString(13);
                String transportType = rs.getString(14);
                String transportArrival = rs.getString(15);
                String transportDeparture = rs.getString(16);
                int participants = rs.getInt(17);
                LocalDateTime bookingDateTime = rs.getTimestamp(18).toLocalDateTime();
                LocalDate startDate = rs.getDate(19).toLocalDate();
                LocalTime startTime = rs.getTime(20).toLocalTime();
                LocalTime endTime = rs.getTime(21).toLocalTime();
                boolean isWholeDay = rs.getBoolean(22);
                boolean isHalfDayEarly = rs.getBoolean(23);
                boolean isNoShow = rs.getBoolean(24);
                boolean isEmailSent = rs.getBoolean(25);
                String messageToAS = rs.getString(26);
                String personalNote = rs.getString(27);

                bookings.add(new Booking(bookingID, bookingType, catering, activity, organizarion, åbenSkoleForløb, firstName, lastName, position, department, phone, email, assistance, transportType, transportArrival, transportDeparture, participants, bookingDateTime, startDate, startTime, endTime, isWholeDay, isHalfDayEarly, isNoShow, isEmailSent, messageToAS, personalNote));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookings;
    }

    @Override
    public HashMap<String, Integer> countBookingsByOrg(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Integer> bookingsGroupedByOrg = new HashMap<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT organization, COUNT(*) AS 'Belægning' " +
                    "FROM Booking WHERE startDate >= ? AND startDate <= ? GROUP BY organization;"
            );
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String organization = rs.getString(1);
                int belægning = rs.getInt(2);
                bookingsGroupedByOrg.put(organization, belægning);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookingsGroupedByOrg;
    }

    @Override
    public HashMap<String, Integer> countBookingsByActivity(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Integer> bookingsGroupedByActivities = new HashMap<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT activity, COUNT(*) AS 'Belægning' " +
                            "FROM Booking WHERE startDate >= ? AND startDate <= ? GROUP BY activity;"
            );
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String activity = rs.getString(1);
                int belægning = rs.getInt(2);
                bookingsGroupedByActivities.put(activity, belægning);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookingsGroupedByActivities;
    }

    @Override
    public void editBooking(Booking booking) throws SQLException {
        Connection con = databaseConnector.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE Booking SET Catering = ?, Activity = ?, Organization = ?, ÅbenSkoleForløb = ?,FirstName = ?, LastName = ?, Position = ?, Afdeling = ?, Phone = ?, Email = ?,Assistance = ?, TransportType = ?, TransportArrival = ?,TransportDeparture = ?, Participants = ?, BookingDateTime = ?, MessageToAS = ?, PersonalNote = ?, BookingType = ? WHERE bookingID = ?);");

            ps.setString(1, booking.getBookingType());
            ps.setString(2, booking.getCatering());
            ps.setString(3, booking.getActivity());
            ps.setString(4, booking.getOrganization());
            ps.setString(5, booking.getÅbenSkoleForløb());
            ps.setString(6, booking.getFirstName());
            ps.setString(7, booking.getLastName());
            ps.setString(8, booking.getPosition());
            ps.setString(9, booking.getAfdeling());
            ps.setString(10, booking.getPhone());
            ps.setString(11, booking.getEmail());
            ps.setString(12, booking.getAssistance());
            ps.setString(13, booking.getTransportType());
            ps.setString(14, booking.getTransportArrival());
            ps.setString(15, booking.getTransportDeparture());
            ps.setInt(16, booking.getParticipants());
            ps.setTimestamp(17, Timestamp.valueOf(booking.getBookingDateTime()));
            ps.setDate(18, Date.valueOf(booking.getStartDate()));
            ps.setTime(19, Time.valueOf(booking.getStartTime()));
            ps.setTime(20, Time.valueOf(booking.getEndTime()));
            ps.setBoolean(21, booking.isWholeDay());
            ps.setBoolean(22, booking.isHalfDayEarly());
            ps.setBoolean(23, booking.isNoShow());
            ps.setString(24, booking.getMessageToAS());
            ps.setString(25, booking.getPersonalNote());
            ps.setInt(26, booking.getBookingID());
            ps.executeUpdate();

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
                throw new SQLException("Booking with ID " + bookingID + " does not exist");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    // https://stackoverflow.com/questions/36296140/subtract-two-dates-in-microsoft-sql-server
    // https://stackoverflow.com/questions/37559741/convert-timestamp-to-date-in-oracle-sql
    // https://www.sqlservercentral.com/articles/the-output-clause-for-update-statements
    @Override
    public List<Booking> getWeeksBookings() {

        List<Booking> weeksBookings = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * from Booking WHERE DATEDIFF(day, CAST(GETDATE() AS DATE),Booking.startDate) < 7;"
            );
            ResultSet rs = ps.executeQuery();

            Booking booking;
            while (rs.next()) {

                int bookingID = rs.getInt(1);
                String bookingType = rs.getString(2);
                String catering = rs.getString(3);
                String activity = rs.getString(4);
                String organization = rs.getString(5);
                String åbenSkoleForløb = rs.getString(6);
                String firstName = rs.getString(7);
                String lastName = rs.getString(8);
                String position = rs.getString(9);
                String afdeling = rs.getString(10);
                String phone = rs.getString(11);
                String email = rs.getString(12);
                String assistance = rs.getString(13);
                String transportType = rs.getString(14);
                String transportArrival = rs.getString(15);
                String transportDeparture = rs.getString(16);
                int participants = rs.getInt(17);
                LocalDateTime bookingDateTime = rs.getTimestamp(18).toLocalDateTime();
                LocalDate startDate = rs.getDate(19).toLocalDate();
                LocalTime startTime = rs.getTime(20).toLocalTime();
                LocalTime endTime = rs.getTime(21).toLocalTime();
                Boolean isWholeDay = rs.getBoolean(22);
                Boolean isHalfDayEarly = rs.getBoolean(23);
                Boolean isNoShow = rs.getBoolean(24);
                Boolean isEmailSent = rs.getBoolean(25);
                String messageToAS = rs.getString(26);
                String personalNote = rs.getString(27);

                booking= new Booking(bookingID, bookingType, catering, activity, organization, åbenSkoleForløb,
                        firstName, lastName, position, afdeling, phone, email, assistance,
                        transportType, transportArrival, transportDeparture,
                        participants, bookingDateTime, startDate, startTime, endTime,
                        isWholeDay, isHalfDayEarly, isNoShow, isEmailSent, messageToAS, personalNote);
                weeksBookings.add(booking);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return weeksBookings;
    }


}


