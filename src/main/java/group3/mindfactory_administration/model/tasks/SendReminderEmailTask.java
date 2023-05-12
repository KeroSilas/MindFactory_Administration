package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.BookingEmail;
import group3.mindfactory_administration.services.SendEmail;
import javafx.concurrent.Task;

import java.util.List;

public class SendReminderEmailTask extends Task<Void> {

    private final BookingDao bookingDao;
    private final SendEmail sendEmail;

    public SendReminderEmailTask() {
        bookingDao = new BookingDaoImpl();
        sendEmail = new SendEmail();
    }

    @Override
    protected Void call() {
        while (!isCancelled()) { // Keep running until the task is cancelled
            try {
                Thread.sleep(30000L); // Sleep for the specified delay
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    break;
                }
            }
            List<BookingEmail> bookingEmailList = bookingDao.getOneWeekOutBookings();
            for (BookingEmail bookingEmail : bookingEmailList) {
                sendEmail.sendEmail(
                        bookingEmail.getEmail(),
                        "Hej " + bookingEmail.getName() + ".\n" +
                                "Du har en booking snart her hos os på denne dato: " + bookingEmail.getStartDate() + ".\n" +
                                "Ønsker du at aflyse din booking, kan du bruge dette booking-nummer i booking applikationen: " + bookingEmail.getBookingID(),
                        "Din kommende booking.",
                        true
                );
            }
        }
        return null;
    }
}