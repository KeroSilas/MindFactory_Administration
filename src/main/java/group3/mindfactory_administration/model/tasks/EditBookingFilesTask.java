package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;
import javafx.concurrent.Task;

import java.sql.SQLException;

public class EditBookingFilesTask extends Task<Boolean> {

    private final Booking booking;
    private final BookingTime bookingTime;
    private final BookingDao bookingDao;

    public EditBookingFilesTask(Booking booking, BookingTime bookingTime) {
        this.booking = booking;
        this.bookingTime = bookingTime;
        bookingDao = new BookingDaoImpl();
    }

    @Override
    public Boolean call() {
        boolean success = true;

        try {
            bookingDao.editBooking(booking, bookingTime); // TODO: Implement editBookingFiles
        }
        catch (SQLException e) {
            success = false;
        }

        return success;
    }
}