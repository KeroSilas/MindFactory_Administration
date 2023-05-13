package group3.mindfactory_administration.model.tasks;


import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import javafx.concurrent.Task;

import java.sql.SQLException;

/*
 * This class is run in a separate thread and edits a booking in the database
 */

public class EditBookingTask extends Task<Boolean> {

    private final BookingDao bookingDao;

    private final Booking booking;

    public EditBookingTask(Booking booking) {
        this.booking = booking;
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected Boolean call() throws Exception {
        boolean success = true;

        try {
            bookingDao.editBooking(booking);
        } catch (SQLException e) {
            success = false;
        }

        return success;
    }
}