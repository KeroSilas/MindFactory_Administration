package group3.mindfactory_administration.model.singleton;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.BookingTime;
import group3.mindfactory_administration.model.tasks.DeleteBookingTask;
import group3.mindfactory_administration.model.tasks.EditBookingTask;
import group3.mindfactory_administration.model.tasks.GetBookingsTask;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class BookingManager {

    private final PropertyChangeSupport support;

    public static BookingManager instance;
    private HashMap<BookingTime, Booking> bookings;

    private BookingManager() {
        support = new PropertyChangeSupport(this);
        // Get bookings from database
        GetBookingsTask getBookingsTask = new GetBookingsTask();
        getBookingsTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bookings = newValue;
                support.firePropertyChange("bookings", oldValue, newValue);
            }
        });
        Thread thread = new Thread(getBookingsTask);
        thread.setDaemon(true);
        thread.start();
    }

    public static BookingManager getInstance() {
        if (instance == null) {
            instance = new BookingManager();
        }
        return instance;
    }

    public HashMap<BookingTime, Booking> getBookings() {
        return bookings;
    }

    public void editBooking(Booking booking, BookingTime bookingTime) {
        EditBookingTask editBookingTask = new EditBookingTask(booking, bookingTime);
        editBookingTask.setOnSucceeded(e -> {
            if (editBookingTask.getValue()) {
                // Edit files and equipment
            }
        });
        Thread thread = new Thread(editBookingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void deleteBooking(int bookingID) {
        DeleteBookingTask deleteBookingTask = new DeleteBookingTask(bookingID);
        Thread thread = new Thread(deleteBookingTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
