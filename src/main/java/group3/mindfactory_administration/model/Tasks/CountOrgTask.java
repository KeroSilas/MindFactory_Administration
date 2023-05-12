package group3.mindfactory_administration.model.Tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.concurrent.Task;

import java.time.LocalDate;
import java.util.HashMap;

public class CountOrgTask extends Task<HashMap<String, Integer>> {

    private final BookingDao bookingDao;
    private LocalDate startDate, endDate;

    public CountOrgTask(LocalDate startDate, LocalDate endDate) {
        bookingDao = new BookingDaoImpl();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    protected HashMap<String, Integer> call() {
        while (!isCancelled()) {
            updateValue(bookingDao.countBookingsByOrg(startDate, endDate));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return bookingDao.countBookingsByOrg(startDate, endDate);
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void updateData() {
        updateValue(bookingDao.countBookingsByOrg(startDate, endDate));
    }
}
