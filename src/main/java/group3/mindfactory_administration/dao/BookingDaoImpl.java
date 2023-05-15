package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.*;

import java.io.File;
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
                    "SELECT cu.firstName, cu.lastName, cu.phone, cu.email, cu.department, cu.position, " +
                            "b.bookingID, b.bookingDateTime, b.startDate, b.startTime, " +
                            "b.endTime, b.isWholeDay, b.isNoShow, b.messageToAS, b.personalNote, " +
                            "bc.cateringID, c.catering, " +
                            "ba.activityID, a.activity," +
                            "bo.organisationID, o.organisation, bo.assistance, bo.participants," +
                            "bf.forløbID, f.forløb, bf.transportType, bf.transportArrival, bf.transportDeparture " +
                            "FROM Booking AS b " +
                            "FULL JOIN Customer AS cu ON b.customerEmail = cu.email " +
                            "FULL JOIN BookingCatering AS bc ON b.bookingID = bc.bookingID " +
                            "FULL JOIN BookingActivity AS ba ON b.bookingID = ba.bookingID " +
                            "FULL JOIN BookingOrganisation AS bo ON b.bookingID = bo.bookingID " +
                            "FULL JOIN BookingForløb AS bf ON b.bookingID = bf.bookingID " +
                            "FULL JOIN Catering AS c ON bc.cateringID = c.cateringID " +
                            "FULL JOIN Activity AS a ON ba.activityID = a.activityID " +
                            "FULL JOIN Organisation AS o ON bo.organisationID = o.organisationID " +
                            "FULL JOIN Forløb AS f ON bf.forløbID = f.forløbID " +
                            "WHERE b.bookingID IS NOT NULL;"
            );

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                    String firstName = rs.getString(1);
                    String lastName = rs.getString(2);
                    String phone = rs.getString(3);
                    String email = rs.getString(4);
                    String department = rs.getString(5);
                    String position = rs.getString(6);

                    int bookingID = rs.getInt(7);
                    LocalDateTime bookingDateTime = rs.getTimestamp(8).toLocalDateTime();
                    LocalDate startDate = rs.getDate(9).toLocalDate();
                    LocalTime startTime = rs.getTime(10).toLocalTime();
                    LocalTime endTime = rs.getTime(11).toLocalTime();
                    boolean isWholeDay = rs.getBoolean(12);
                    boolean isNoShow = rs.getBoolean(13);
                    String messageToAS = rs.getString(14);
                    String personalNote = rs.getString(15);

                    int cateringID = rs.getInt(16);
                    String cateringName = rs.getString(17);

                    int activityID = rs.getInt(18);
                    String activityName = rs.getString(19);

                    int organisationID = rs.getInt(20);
                    String organisationName = rs.getString(21);
                    String assistance = rs.getString(22);
                    int participants = rs.getInt(23);

                    int forløbID = rs.getInt(24);
                    String forløbName = rs.getString(25);
                    String transportType = rs.getString(26);
                    String transportArrival = rs.getString(27);
                    String transportDeparture = rs.getString(28);

                    Customer customer = new Customer(firstName, lastName, position, department, phone, email);
                    Catering catering = new Catering(cateringID, cateringName);
                    Activity activity = new Activity(activityID, activityName);
                    Organization organisation = new Organization(organisationID, organisationName, participants, assistance);
                    Forløb forløb = new Forløb(forløbID, forløbName, transportType, transportArrival, transportDeparture);

                    PreparedStatement ps2 = con.prepareStatement(
                            "SELECT equipment " +
                                    "FROM BookingEquipment " +
                                    "WHERE bookingID = ?;"
                    );
                    ps2.setInt(1, bookingID);
                    ResultSet rs2 = ps2.executeQuery();
                    List<String> equipment = new ArrayList<>();
                    while (rs2.next()) {
                        equipment.add(rs2.getString(1));
                    }

                    PreparedStatement ps3 = con.prepareStatement(
                            "SELECT filePath " +
                                    "FROM BookingFiles " +
                                    "WHERE bookingID = ?;"
                    );
                    ps3.setInt(1, bookingID);
                    ResultSet rs3 = ps3.executeQuery();
                    List<File> fileList = new ArrayList<>();
                    while (rs3.next()) {
                        fileList.add(new File(rs3.getString(1)));
                    }

                    Booking booking = new Booking(bookingID, customer, catering, activity, organisation, forløb, bookingDateTime, startDate, startTime, endTime, isWholeDay, isNoShow, messageToAS, personalNote, equipment, fileList);
                    bookings.add(booking);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return bookings;
    }

    // References:
    // https://stackoverflow.com/questions/36296140/subtract-two-dates-in-microsoft-sql-server
    // https://stackoverflow.com/questions/37559741/convert-timestamp-to-date-in-oracle-sql
    // https://www.sqlservercentral.com/articles/the-output-clause-for-update-statements

    // Gets all bookings that are one week out and sets isEmailSent to 1 at the same time
    @Override
    public List<BookingEmail> getOneWeekOutBookings() {

        List<BookingEmail> oneWeekOutBookings = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE Booking " +
                            "SET isEmailSent = 1 " +
                            "OUTPUT INSERTED.bookingID, INSERTED.customerEmail, INSERTED.startDate " + // OUTPUT INSERTED returns the value of the column after the update
                            "FROM Booking " +
                            "WHERE DATEDIFF(day, CAST(GETDATE() AS DATE), startDate) < 7 AND isEmailSent = 0;" // Cast GETDATE() to date to remove the time (Yes, GETDATE() returns a datetime/timestamp), then subtract the two dates and check if the difference is less than 7 days
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                BookingEmail bookingEmail;
                int bookingID = rs.getInt(1);
                String email = rs.getString(2);
                LocalDate startDate = rs.getDate(3).toLocalDate();

                bookingEmail = new BookingEmail(bookingID, email, startDate);
                oneWeekOutBookings.add(bookingEmail);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return oneWeekOutBookings;
    }

    // Counts the number of bookings made by each organization
    // Uses HashMap to store the organization name and the number of bookings
    @Override
    public HashMap<String, Integer> countBookingsByOrg(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Integer> bookingsGroupedByOrg = new HashMap<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT o.organisation, COUNT(*) AS 'Belægning' " +
                    "FROM Booking AS b " +
                            "JOIN BookingOrganisation AS bo ON b.bookingID = bo.bookingID " +
                            "JOIN Organisation AS o ON bo.organisationID = o.organisationID " +
                            "WHERE b.startDate >= ? AND b.startDate <= ? GROUP BY o.organisation;"
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

    // Same as above, but for activities
    @Override
    public HashMap<String, Integer> countBookingsByActivity(LocalDate startDate, LocalDate endDate) {
        HashMap<String, Integer> bookingsGroupedByActivities = new HashMap<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement(
                    "SELECT a.activity, COUNT(*) AS 'Belægning' " +
                            "FROM Booking AS b " +
                            "JOIN BookingActivity AS ba ON b.bookingID = ba.bookingID " +
                            "JOIN Activity AS a ON ba.activityID = a.activityID " +
                            "WHERE b.startDate >= ? AND b.startDate <= ? GROUP BY a.activity;"
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

    // Updates a given booking
    @Override
    public void editBooking(Booking booking) throws SQLException {
        Connection con = databaseConnector.getConnection();
        try {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement("UPDATE Customer SET firstName = ?, lastName = ?, phone = ?, department = ?, position = ? WHERE email = ?;");

            ps.setString(1, booking.getCustomer().getFirstName());
            ps.setString(2, booking.getCustomer().getLastName());
            ps.setString(3, booking.getCustomer().getPhone());
            ps.setString(4, booking.getCustomer().getDepartment());
            ps.setString(5, booking.getCustomer().getPosition());
            ps.setString(6, booking.getCustomer().getEmail());
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("UPDATE Booking SET startDate = ?, startTime = ?, endTime = ?, isWholeDay = ?, isNoShow = ?, personalNote = ? WHERE bookingID = ?;");

            ps2.setDate(1, Date.valueOf(booking.getStartDate()));
            ps2.setTime(2, Time.valueOf(booking.getStartTime()));
            ps2.setTime(3, Time.valueOf(booking.getEndTime()));
            ps2.setBoolean(4, booking.isWholeDay());
            ps2.setBoolean(5, booking.isNoShow());
            ps2.setString(6, booking.getPersonalNote());
            ps2.setInt(7, booking.getBookingID());
            ps2.executeUpdate();

            if (booking.getCatering() != null) {
                PreparedStatement ps3 = con.prepareStatement("UPDATE BookingCatering SET cateringID = ? WHERE bookingID = ?;");
                ps3.setInt(1, booking.getCatering().getCateringID());
                ps3.setInt(2, booking.getBookingID());
                ps3.executeUpdate();
            }

            if (booking.getActivity() != null) {
                PreparedStatement ps4 = con.prepareStatement("UPDATE BookingActivity SET activityID = ? WHERE bookingID = ?;");
                ps4.setInt(1, booking.getActivity().getActivityID());
                ps4.setInt(2, booking.getBookingID());
                ps4.executeUpdate();
            }

            if (booking.getOrganization() != null) {
                PreparedStatement ps5 = con.prepareStatement("UPDATE BookingOrganisation SET organisationID = ?, assistance = ?, participants = ? WHERE bookingID = ?;");
                ps5.setInt(1, booking.getOrganization().getOrganizationID());
                ps5.setString(2, booking.getOrganization().getAssistance());
                ps5.setInt(3, booking.getOrganization().getParticipants());
                ps5.setInt(4, booking.getBookingID());
                ps5.executeUpdate();
            }

            if (booking.getÅbenSkoleForløb() != null) {
                PreparedStatement ps6 = con.prepareStatement("UPDATE BookingForløb SET forløbID = ?, transportType = ?, transportArrival = ?, transportDeparture = ? WHERE bookingID = ?;");
                ps6.setInt(1, booking.getÅbenSkoleForløb().getForløbID());
                ps6.setString(2, booking.getÅbenSkoleForløb().getTransportType());
                ps6.setString(3, booking.getÅbenSkoleForløb().getTransportArrival());
                ps6.setString(4, booking.getÅbenSkoleForløb().getTransportDeparture());
                ps6.setInt(5, booking.getBookingID());
                ps6.executeUpdate();
            }

            String sql = "DELETE FROM BookingEquipment WHERE booking = ?;";
            PreparedStatement ps6 = con.prepareStatement(sql);
            ps6.setInt(1, booking.getBookingID());
            ps6.executeUpdate();

            String sql2 = "INSERT INTO BookingEquipment VALUES(?,?);";
            PreparedStatement ps7 = con.prepareStatement(sql2);

            for (String equipment : booking.getEquipmentList()) {
                ps7.setInt(1, booking.getBookingID());
                ps7.setString(2, equipment);
                ps7.addBatch();
            }
            ps6.executeBatch();

            String sql3 = "DELETE FROM BookingFiles WHERE booking = ?;";
            PreparedStatement ps8 = con.prepareStatement(sql3);
            ps8.setInt(1, booking.getBookingID());
            ps8.executeUpdate();

            String sql4 = "INSERT INTO BookingFiles VALUES(?,?);";
            PreparedStatement ps9 = con.prepareStatement(sql4);

            for (File file : booking.getFileList()) {
                ps9.setInt(1, booking.getBookingID());
                ps9.setString(2, file.getPath());
                ps9.addBatch();
            }
            ps9.executeBatch();

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

    // References:
    // https://stackoverflow.com/questions/2571915/return-number-of-rows-affected-by-sql-update-statement-in-java

    // Deletes a booking with a given ID
    @Override
    public void deleteBooking(int bookingID) throws SQLException {
        int rowsAffected;
        try (Connection con = databaseConnector.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Booking WHERE BookingID = ?");
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate(); // Returns the number of rows affected by the query, which should be 1 if the booking was deleted

            if (rowsAffected == 0) { // If no rows were affected, the booking does not exist
                throw new SQLException(); // Throw exception to be handled by the task that called this method
            }
        } catch (SQLException e) {
            throw new SQLException(e); // Re-throw exception to be handled by the task that called this method
        }
    }
}


