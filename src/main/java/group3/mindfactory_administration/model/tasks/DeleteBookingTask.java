package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

import java.sql.SQLException;

/*
 * This class is run in a separate thread and deletes a booking from the database
 */

public class DeleteBookingTask extends Task<Boolean> {

    private final BookingDao bookingDao;

    private final int bookingID;

    public DeleteBookingTask(int bookingID) {
        this.bookingID = bookingID;
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected Boolean call() throws Exception {
        boolean success = true;

        try {
            bookingDao.deleteBooking(bookingID);
        } catch (SQLException e) {
            success = false; // If the deletion fails or a booking wasn't found, return false
        }

        return success;
    }
}