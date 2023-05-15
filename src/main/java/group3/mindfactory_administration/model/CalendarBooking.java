package group3.mindfactory_administration.model;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
 * Displays a booking in the calendar
 * The booking is displayed as a VBox, which is then added to a CalendarCell
 */

public class CalendarBooking extends VBox {

    private final Booking booking;

    public CalendarBooking(Booking booking) {
        this.booking = booking;

        // Styling
        if (booking.isWholeDay()) { // If the booking is a whole day booking, the VBox is made taller
            this.setPrefHeight(100);
        } else {
            this.setPrefHeight(45);
        }
        this.setPrefWidth(USE_COMPUTED_SIZE);
        this.setAlignment(Pos.TOP_LEFT);
        this.setCursor(Cursor.HAND);
        if (booking.getBookingType().equals("Skole")) { // Different colors for different booking types
            this.setStyle("-fx-background-color: #2f56ad; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #2f56ad; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
        } else {
            this.setStyle("-fx-background-color: #653da1; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #653da1; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
        }

        // Inserting information
        HBox title = new HBox();
        title.setMouseTransparent(true); // Makes it possible to click on the CalendarBooking object itself, even though the user is clicking on this HBox
        Label org;
        if (booking.getOrganization().getOrganization().equals("Andet udleje") || booking.getOrganization().getOrganization().equals("Tønder Kommune - organisation") || booking.getOrganization().getOrganization().equals("Tønder Kommune - skole")) {
            org = new Label(booking.getCustomer().getDepartment());
        } else {
            org = new Label(booking.getOrganization().getOrganization());
        }
        org.setFont(Font.font("System", FontWeight.BOLD, 14)); // Bold font for the organization name
        org.setStyle("-fx-text-fill: #ffffff");
        title.getChildren().add(org);

        HBox body = new HBox();
        body.setMouseTransparent(true);
        Label time = new Label(booking.toString());
        time.setStyle("-fx-text-fill: #ffffff");
        body.getChildren().add(time);

        this.getChildren().addAll(title, body);
    }

    public Booking getBooking() {
        return booking;
    }
}
