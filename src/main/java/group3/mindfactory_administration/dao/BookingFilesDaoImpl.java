package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.File;
import group3.mindfactory_administration.model.singleton.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingFilesDaoImpl implements BookingFilesDao{
    private final DatabaseConnector databaseConnector;

    public BookingFilesDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }
    @Override
    public void addBookingFiles(int bookingID, int fileID) {
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO BookingFiles VALUES(?,?);");
            ps.setInt(1, bookingID);
            ps.setInt(2, fileID);

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


    public List<File> getAllFilesOnBookingFile(Booking booking) {
        List<File> files = new ArrayList<>();
        try(Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Booking, BookingFiles, Files WHERE Booking.bookingID = " +
                    "BookingFiles.bookingID AND BookingFiles.fileID = Files.fileID;");
            ResultSet rs = ps.executeQuery();

            File file;
            while (rs.next()) {
                int fileID = rs.getInt(1);
                String filePath = rs.getString(2);
                String fileName = rs.getString(3);

                file = new File(fileID, filePath,fileName);
                files.add(file);
            }

        } catch (SQLException e) {
            System.err.println("cannot access AllFiles (BookingFilesDaoImpl) " + e.getMessage());
        }
        return files;
    }
}
