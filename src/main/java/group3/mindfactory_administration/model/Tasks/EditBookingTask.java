package group3.mindfactory_administration.model.Tasks;


import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.Booking;
import javafx.concurrent.Task;

public class EditBookingTask extends Task<Boolean> {

    private final BookingDao bookingDao;

    private final Booking booking;

    public EditBookingTask(Booking booking) {
        this.booking = booking;
        bookingDao = new BookingDaoImpl();
    }

    @Override
    protected Boolean call() throws Exception {
        boolean success = true;

        try {
            bookingDao.editBooking(booking);
        } catch (RuntimeException e) {
            success = false;
        }

        return success;
    }
}