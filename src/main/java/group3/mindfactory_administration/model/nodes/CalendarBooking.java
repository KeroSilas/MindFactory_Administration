package group3.mindfactory_administration.model.nodes;

import group3.mindfactory_administration.model.Booking;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;

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
        if (!booking.isNoShow()) {
            if (booking.getBookingType().equals("Skole")) { // Different colors for different booking types
                this.setStyle("-fx-background-color: #2f56ad; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #2f56ad; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
            } else {
                this.setStyle("-fx-background-color: #653da1; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #653da1; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
            }
        } else {
            this.setStyle("-fx-background-color: #737373; -fx-background-radius: 5px; -fx-border-radius: 5px; -fx-border-color: #737373; -fx-border-width: 1px; -fx-padding: 2px 5px 2px 5px;");
        }

        // Inserting information
        HBox title = new HBox();
        HBox logoHB = new HBox();
        logoHB.setSpacing(4);

        File notepadFile = new File("src/main/resources/group3/mindfactory_administration/icons/notepad.png");
        File ideaFile = new File("src/main/resources/group3/mindfactory_administration/icons/idea.png");
        File planetFile = new File("src/main/resources/group3/mindfactory_administration/icons/planet-earth.png");
        File shieldFile = new File("src/main/resources/group3/mindfactory_administration/icons/shield.png");
        File virtualFile = new File("src/main/resources/group3/mindfactory_administration/icons/virtual.png");
        File vacuumFile = new File("src/main/resources/group3/mindfactory_administration/icons/vacuum-cleaner.png");
        File robotFile = new File("src/main/resources/group3/mindfactory_administration/icons/robot.png");

        ImageView notepadImageView = new ImageView(new Image(notepadFile.toURI().toString(), 18, 18, true, true));
        ImageView ideaImageView = new ImageView(new Image(ideaFile.toURI().toString(), 18, 18, true, true));
        ImageView planetImageView = new ImageView(new Image(planetFile.toURI().toString(), 18, 18, true, true));
        ImageView shieldImageView = new ImageView(new Image(shieldFile.toURI().toString(), 18, 18, true, true));
        ImageView virtualImageView = new ImageView(new Image(virtualFile.toURI().toString(), 18, 18, true, true));
        ImageView vacuumImageView = new ImageView(new Image(vacuumFile.toURI().toString(), 18, 18, true, true));
        ImageView robotImageView = new ImageView(new Image(robotFile.toURI().toString(), 18, 18, true, true));

        if (booking.getPersonalNote() != null) {
            logoHB.getChildren().add(notepadImageView);
        }

        if (booking.getÅbenSkoleForløb().getÅbenSkoleForløb() != null) {
            title.setSpacing(6);
            switch (booking.getÅbenSkoleForløb().getÅbenSkoleForløb()) {
                case "Idéfabrikken" -> logoHB.getChildren().add(ideaImageView);
                case "Digital fabrikation med laserskærer" -> logoHB.getChildren().add(virtualImageView);
                case "Robot på job" -> logoHB.getChildren().add(robotImageView);
                case "Robotten rydder op" -> logoHB.getChildren().add(vacuumImageView);
                case "Naturturisme ved Vadehavet" -> logoHB.getChildren().add(planetImageView);
                case "Skab sikkerhed i Vadehavet" -> logoHB.getChildren().add(shieldImageView);
            }
        }

        title.getChildren().add(logoHB);

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
