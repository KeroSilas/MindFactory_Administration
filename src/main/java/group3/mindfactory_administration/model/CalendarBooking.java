package group3.mindfactory_administration.model;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class CalendarBooking extends HBox {

    private BookingTime time;
    private Booking booking;

    public CalendarBooking(BookingTime time, Booking booking) {
        this.time = time;
        this.booking = booking;

        getContent();

        /* TODO: Drag and drop
        // Starts the drag and drop process
        setOnDragDetected(e -> {
            Dragboard db = startDragAndDrop(TransferMode.ANY);

            // Generated with GitHub Copilot
            ClipboardContent content = new ClipboardContent();
            content.putString(?);
            db.setContent(content);

            e.consume();
        });

        // Removes the event from the cell when it is dragged out
        setOnDragDone(e -> {
            if (e.getTransferMode() == TransferMode.MOVE) {
                // Needed a way to access the CalendarCell from the CalendarEvent so that it can delete itself
                CalendarCell cell = (CalendarCell) getParent();
                cell.getChildren().remove(this);
            }
            e.consume();
        });
        */
    }

    public void setClicked(boolean clicked) {
        if (clicked)
            setStyle("-fx-background-color: #0051ff; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #000000; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
        else
            setStyle("-fx-background-color: #0051ff; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #0051ff; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
    }

    public void getContent() {
        // Styling
        setPrefHeight(15);
        setPrefWidth(USE_COMPUTED_SIZE);
        setStyle("-fx-background-color: #0051ff; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #0051ff; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");

        // Temporary code
        Label eventName = new Label(booking.getAfdeling());
        eventName.setStyle("-fx-text-fill: #ffffff");
        eventName.setMouseTransparent(true);

        getChildren().add(eventName);
    }

    public BookingTime getTime() {
        return time;
    }

    public Booking getBooking() {
        return booking;
    }

    @Override
    public String toString() {
        return booking.getAfdeling();
    }
}
