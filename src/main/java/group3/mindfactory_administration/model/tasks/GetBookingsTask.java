package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;
import javafx.concurrent.Task;

import java.util.HashMap;

public class GetBookingsTask extends Task<HashMap<BookingTime, Booking>> {

    private final BookingDao bookingDao;

    public GetBookingsTask() {
        bookingDao = new BookingDaoImpl();
    }

    @Override
    public HashMap<BookingTime, Booking> call() {
        while (!isCancelled()) { // Keep running until the task is cancelled
            updateValue(bookingDao.getBookings());
            System.out.println("Booked times updated");
            try {
                Thread.sleep(5000L); // Sleep for the specified delay
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    break;
                }
            }
        }
        return bookingDao.getBookings();
    }
}
