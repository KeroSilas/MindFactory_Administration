package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

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
        } catch (RuntimeException e) {
            success = false;
        }

        return success;
    }
}