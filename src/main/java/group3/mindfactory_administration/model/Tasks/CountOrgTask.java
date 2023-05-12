package group3.mindfactory_administration.model.Tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

import java.util.HashMap;

public class CountOrgTask extends Task<HashMap<String, Integer>> {

    private final BookingDao bookingDao;

    public CountOrgTask() {
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected HashMap<String, Integer> call() {
        return bookingDao.countBookingsByOrg();
    }
}
