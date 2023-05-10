package group3.mindfactory_administration.dao;

import group3.mindfactory_booking.model.File;
import group3.mindfactory_booking.model.singleton.Booking;

import java.io.File;
import java.util.List;

public interface BookingFilesDao {

    void addBookingFiles(int bookingID, int fileID);

    void deleteBookingFiles(int fileID);

    List<File> getAllFilesOnBookingFile(Booking booking);

}
