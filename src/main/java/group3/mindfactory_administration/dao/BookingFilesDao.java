package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;


import javax.swing.*;
import java.io.File;
import java.util.List;

public interface BookingFilesDao {

    void addBookingFiles(int bookingID, int fileID);

    void addBookingFiles(int bookingID, String filePath);

    void deleteBookingFiles(int fileID);

    List<File> getAllFilesOnBookingFile(Booking booking);

    void saveBookingFileList(List<String> bookingFilesList, int bookingID);

}


