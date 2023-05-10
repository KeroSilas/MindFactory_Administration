package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

import java.sql.SQLException;

public class DeleteBookingTask extends Task<Boolean> {

    private final int bookingID;
    private final BookingDao bookingDao;

    public DeleteBookingTask(int bookingID) {
        this.bookingID = bookingID;
        bookingDao = new BookingDaoImpl();
    }

    @Override
    public Boolean call() {
        boolean success = true;

        try {
            bookingDao.deleteBooking(bookingID);
        }
        catch (SQLException e) {
            success = false;
        }

        return success;
    }
}