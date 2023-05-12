package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDaoImpl implements BookingDao {

    private final DatabaseConnector databaseConnector;

    public BookingDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public void editBooking(Booking booking) throws SQLException {
        Connection con = databaseConnector.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement("UPDATE Booking SET catering = ?, activity = ?, organization = ?, åbenSkoleForløb = ?,firstName = ?, lastName = ?, position = ?, afdeling = ?, phone = ?, email = ?,assistance = ?, transportType = ?, transportArrival = ?,transportDeparture = ?, participants = ?, bookingDateTime = ?, messageToAS = ?, personalNote = ?, bookingType = ? WHERE bookingID = ?);");

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
            ps.setDate(18, Date.valueOf(Booking.getInstance().getStartDate()));
            ps.setTime(19, Time.valueOf(Booking.getInstance().getStartTime()));
            ps.setTime(20, Time.valueOf(Booking.getInstance().getEndTime()));
            ps.setBoolean(21, Booking.getInstance().isWholeDay());
            ps.setBoolean(22, Booking.getInstance().isHalfDayEarly());
            ps.setBoolean(23, Booking.getInstance().isNoShow());
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
            //ps.setInt(1, category.getCategoryID());  change this to reflect Booking search
            ResultSet rs = ps.executeQuery();

            Booking booking;
            while (rs.next()) {

                int bookingID = rs.getInt(1);
                String bookingType = rs.getString(2);
                String catering = rs.getString(2);
                String activity = rs.getString(2);
                String organization = rs.getString(2);
                String åbenSkoleForløb = rs.getString(2);
                String firstName = rs.getString(2);
                String lastName = rs.getString(2);
                String position = rs.getString(2);
                String afdeling = rs.getString(2);
                String phone = rs.getString(2);
                String email = rs.getString(2);
                String assistance = rs.getString(2);
                String transportType = rs.getString(2);
                String transportArrival = rs.getString(2);
                String transportDeparture = rs.getString(2);
                int participants = rs.getInt(9);
                Timestamp bookingDateTime = rs.getTimestamp(2);
                Date startDate = rs.getDate(2);
                Time startTime = rs.getTime(2);
                Time endTime = rs.getTime(9);
                Boolean isWholeDay = rs.getBoolean(9);
                Boolean isHalfDayEarly = rs.getBoolean(23);
                Boolean isNoShow = rs.getBoolean(24);
                Boolean isEmailSent = rs.getBoolean(25);
                String messageToAS = rs.getString(26);
                String personalNote = rs.getString(27);

                booking= new Booking(bookingID, bookingType, catering, activity, organization, åbenSkoleForløb, firstName, lastName, position, afdeling, phone, email, assistance, transportType, transportArrival, transportArrival, transportDeparture, participants, bookingDateTime, startDate, startTime, endTime, isWholeDay, isHalfDayEarly, isNoShow, isEmailSent, messageToAS, personalNote);
                weeksBookings.add(booking);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return weeksBookings;
    }



}


