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
        return bookingDao.getAllBookings();
    }
}
