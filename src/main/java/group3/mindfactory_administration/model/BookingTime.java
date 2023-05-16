package group3.mindfactory_administration.model;

import java.time.LocalDate;
import java.time.LocalTime;

/*
 * This class holds the information about the time of a booking.
 * It is used to filter out all the booked times when editing a booking.
 * This way the administrator can only choose a time that is not already booked.
 */

public class BookingTime {

    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final boolean isWholeDay;

    public BookingTime(LocalDate startDate, LocalTime startTime, LocalTime endTime, boolean isWholeDay) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isWholeDay = isWholeDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isWholeDay() {
        return isWholeDay;
    }
}
