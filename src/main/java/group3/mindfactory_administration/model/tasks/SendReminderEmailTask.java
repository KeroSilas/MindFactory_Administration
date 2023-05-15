package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import group3.mindfactory_administration.model.BookingEmail;
import group3.mindfactory_administration.services.SendEmail;
import javafx.concurrent.Task;

import java.util.List;

/*
 * This class is run in a separate thread and sends reminder emails to customers with a booking one week out
 */

public class SendReminderEmailTask extends Task<Void> {

    private final BookingDao bookingDao;
    private final SendEmail sendEmail;

    public SendReminderEmailTask() {
        bookingDao = new BookingDaoImpl();
        sendEmail = new SendEmail();
    }

    @Override
    protected Void call() {
        while (!isCancelled()) { // Keep task running in a loop until it is cancelled (spoiler: it never is)
            List<BookingEmail> bookingEmailList = bookingDao.getOneWeekOutBookings(); // Get all bookings one week out from the database

            // Send email to everyone with a booking one week out
            for (BookingEmail bookingEmail : bookingEmailList) {
                sendEmail.sendEmail(
                        bookingEmail.getEmail(),
                        "Hej.\n" +
                                "Du har en booking snart her hos os på denne dato: " + bookingEmail.getStartDate() + ".\n" +
                                "Ønsker du at aflyse din booking, kan du bruge dette booking-nummer i booking applikationen: " + bookingEmail.getBookingID(),
                        "Din kommende booking.",
                        true
                );
            }

            try {
                Thread.sleep(60000L);
            } catch (InterruptedException e) {
                if (isCancelled()) {
                    break;
                }
            }
        }
        return null;
    }
}