package group3.mindfactory_administration.model.Tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import javafx.concurrent.Task;

import java.util.List;

public class GetBookingsTask extends Task<List<Booking>> {

    private final BookingDao bookingDao;

    public GetBookingsTask() {
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected List<Booking> call() {
        while (!isCancelled()) {
            updateValue(bookingDao.getAllBookings());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookingDao.getAllBookings();
    }
}
