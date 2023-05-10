package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookingFilesDaoImpl implements BookingFilesDao{
    private final DatabaseConnector databaseConnector;

    public BookingFilesDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public void saveBookingFileList(List<String> bookingFilesList, int bookingID) {
        try (Connection con = databaseConnector.getConnection()) {
            con.setAutoCommit(false);
            String sql = "INSERT INTO BookingFiles VALUES(?,?);";
            PreparedStatement ps = con.prepareStatement(sql);

            for (String file : bookingFilesList) {
                ps.setInt(1, bookingID);
                ps.setString(2, file);
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void addBookingFiles(int bookingID, int fileID) {

    }

    @Override
    public void addBookingFiles(int bookingID, String filePath) {
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO BookingFiles VALUES(?,?);");
            ps.setInt(1, bookingID);
            ps.setString(2, filePath);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("cannot add file (BookingFiles) " + e.getMessage());
        }

    }

    @Override
    public void deleteBookingFiles(int fileID) {
        try (Connection con = databaseConnector.getConnection()){

            PreparedStatement ps = con.prepareStatement("DELETE FROM BookingEquipment WHERE fileID = ?;");
            ps.setInt(1, fileID);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Cannot delete File (BookingFiles) " + e.getMessage());
        }

    }

    @Override
    public List<File> getAllFilesOnBookingFile(Booking booking) {
        return null;
    }


}
