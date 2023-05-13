package group3.mindfactory_administration.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/*
 * Displays a day in the calendar
 * The day is displayed as a VBox.
 * It creates either 1 or 2 CalendarBooking objects to store inside itself
 */

public class CalendarCell extends VBox{

    private final String dayOfMonth;

    public CalendarCell(LocalDate date, List<Booking> bookings) {
        dayOfMonth = String.valueOf(date.getDayOfMonth());

        // Styling
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(4);
        this.setPadding(new Insets(4, 4, 4, 4));
        if (date.isEqual(LocalDate.now())) { // If the date is today, the cell is highlighted
            setStyle("-fx-border-color: #0095ff; -fx-border-width: 1px; -fx-background-color: #dbefff;");
        } else {
            setStyle("-fx-border-color: #dedede; -fx-border-width: 1px; -fx-background-color: white;");
        }

        // Add the day of the month to the cell
        Label label = new Label(dayOfMonth);
        label.setMouseTransparent(true);
        this.getChildren().add(label);

        // Add the bookings to the cell
        Collections.sort(bookings); // Sort the bookings by time, so they are displayed in the correct order
        for (Booking booking : bookings) {
            this.getChildren().add(new CalendarBooking(booking));
        }
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }
}
