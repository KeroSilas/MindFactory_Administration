package group3.mindfactory_administration.model;

import java.time.LocalDate;

/*
 * This class is used to store the data of a booking that is used to send reminder emails
 */

public class BookingEmail {

    private final int bookingID;
    private final String email;
    private final LocalDate startDate;

    public BookingEmail(int bookingID, String email, LocalDate startDate) {
        this.bookingID = bookingID;
        this.email = email;
        this.startDate = startDate;
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

}
