package group3.mindfactory_administration.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CalendarCell extends VBox{

    private final String dayOfMonth;

    public CalendarCell(LocalDate date, List<Booking> bookings) {
        dayOfMonth = String.valueOf(date.getDayOfMonth());

        // Styling
        this.setAlignment(Pos.TOP_LEFT);
        this.setSpacing(4);
        this.setPadding(new Insets(4, 4, 4, 4));
        if (date.isEqual(LocalDate.now())) {
            setStyle("-fx-border-color: #0095ff; -fx-border-width: 1px; -fx-background-color: #dbefff;");
        } else {
            setStyle("-fx-border-color: #dedede; -fx-border-width: 1px; -fx-background-color: white;");
        }

        // Add the day of the month to the cell
        Label label = new Label(dayOfMonth);
        label.setMouseTransparent(true);
        this.getChildren().add(label);

        /*
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

        Collections.sort(bookings);
        for (Booking booking : bookings) {
            getChildren().add(new CalendarBooking(booking));
        }
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }
}
