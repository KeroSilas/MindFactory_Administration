package group3.mindfactory_administration.model.Tasks;


import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import javafx.concurrent.Task;

import java.awt.print.Book;

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