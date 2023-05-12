package group3.mindfactory_administration.model.Tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

import java.util.HashMap;

public class CountActivityTask extends Task<HashMap<String, Integer>> {

    private final BookingDao bookingDao;

    public CountActivityTask() {
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected HashMap<String, Integer> call() throws Exception {
        return bookingDao.countBookingsByActivity();
    }
}