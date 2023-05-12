package group3.mindfactory_administration.model;

import java.time.LocalDate;

public class BookingEmail {

    private final int bookingID;
    private final String name;
    private final String email;
    private final LocalDate startDate;

    public BookingEmail(int bookingID, String name, String email, LocalDate startDate) {
        this.bookingID = bookingID;
        this.name = name;
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

    public String getName() {
        return name;
    }

}
