package group3.mindfactory_administration.model;

import group3.mindfactory_administration.model.singleton.BookingManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.HashMap;

public class CalendarCell extends VBox{

    private BookingManager bookingManager;
    private final String dayOfMonth;

    public CalendarCell(LocalDate date) {
        bookingManager = BookingManager.getInstance();

        dayOfMonth = String.valueOf(date.getDayOfMonth());
        // Styling
        setAlignment(Pos.TOP_LEFT);
        setSpacing(4);
        setPadding(new Insets(4, 4, 4, 4));
        if (date.isEqual(LocalDate.now())) {
            setStyle("-fx-border-color: #0095ff; -fx-border-width: 1px; -fx-background-color: #dbefff;");
        } else {
            setStyle("-fx-border-color: #dedede; -fx-border-width: 1px; -fx-background-color: white;");
        }

        // Add the day of the month to the cell
        Label label = new Label(dayOfMonth);
        label.setMouseTransparent(true);
        getChildren().add(label);

        /* TODO: Drag and drop
        setOnDragOver(e -> {
            if (e.getGestureSource() != this && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        // Adds the event to the cell when it is dropped
        setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            // Generated with GitHub Copilot
            boolean success = false;
            if (db.hasString()) {
                getChildren().add(new CalendarBooking(db.getString()));
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();
        });
        */

        // Adds all the events for the day
        HashMap<BookingTime, Booking> bookings = bookingManager.getBookings();
        for (BookingTime time : bookings.keySet()) {
            if (time.getDate().isEqual(date)) {
                getChildren().add(new CalendarBooking(time, bookings.get(time)));
            }
        }
    }

    @Override
    public String toString() {
        return dayOfMonth;
    }
}
