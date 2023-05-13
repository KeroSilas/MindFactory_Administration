package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import javafx.concurrent.Task;

import java.util.List;

/*
 * This class is run in a separate thread and updates the value of the task with data from the database
 */

public class GetBookingsTask extends Task<List<Booking>> {

    private final BookingDao bookingDao;

    public GetBookingsTask() {
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected List<Booking> call() {
        while (!isCancelled()) { // Keep task running in a loop until it is cancelled (spoiler: it never is)
            updateValue(bookingDao.getAllBookings()); // Update the value of the task with data from the database

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookingDao.getAllBookings();
    }
}
