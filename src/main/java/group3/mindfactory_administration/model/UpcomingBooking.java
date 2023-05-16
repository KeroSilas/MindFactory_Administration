package group3.mindfactory_administration.model;

import javafx.geometry.Insets;
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

public class UpcomingBooking extends VBox {

    private final Booking booking;


    public UpcomingBooking(Booking booking) {
        this.booking = booking;

        //List<Booking> upcomingBookings = bookingDao.getOneWeekOutBookings();
        // Styling
        this.setPrefWidth(190);
        this.setPrefHeight(USE_COMPUTED_SIZE);
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
        HBox orgHB = new HBox();
        HBox logoHB = new HBox();
        logoHB.setSpacing(4);
        logoHB.setMouseTransparent(true);

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
            orgHB.setSpacing(6);
            switch (booking.getÅbenSkoleForløb().getÅbenSkoleForløb()) {
                case "Idéfabrikken" -> logoHB.getChildren().add(ideaImageView);
                case "Digital fabrikation med laserskærer" -> logoHB.getChildren().add(virtualImageView);
                case "Robot på job" -> logoHB.getChildren().add(robotImageView);
                case "Robotten rydder op" -> logoHB.getChildren().add(vacuumImageView);
                case "Naturturisme ved Vadehavet" -> logoHB.getChildren().add(planetImageView);
                case "Skab sikkerhed i Vadehavet" -> logoHB.getChildren().add(shieldImageView);
            }
        }

        orgHB.getChildren().add(logoHB);

        orgHB.setMouseTransparent(true); // Makes it possible to click on the upcomingBooking object itself, even though the user is clicking on this HBox
        Label org;
        if (booking.getOrganization().getOrganization().equals("Andet udleje") || booking.getOrganization().getOrganization().equals("Tønder Kommune - organisation") || booking.getOrganization().getOrganization().equals("Tønder Kommune - skole")) {
            org = new Label(booking.getCustomer().getDepartment());
        } else {
            org = new Label(booking.getOrganization().getOrganization());
        }
        org.setFont(Font.font("System", FontWeight.BOLD, 14)); // Bold font for the organization name
        org.setStyle("-fx-text-fill: #ffffff");
        orgHB.getChildren().add(org);

        HBox timeHB = new HBox();
        timeHB.setMouseTransparent(true);
        Label time = new Label(booking.getStartTime().toString() + " - " + booking.getEndTime().toString());
        time.setStyle("-fx-text-fill: #ffffff");
        timeHB.getChildren().add(time);

        HBox dateHB = new HBox();
        dateHB.setMouseTransparent(true);
        Label date = new Label(booking.getStartDate().toString());
        date.setStyle("-fx-text-fill: #ffffff");
        dateHB.getChildren().add(date);

        HBox nameHB = new HBox();
        nameHB.setMouseTransparent(true);
        Label name = new Label(booking.getCustomer().getFirstName() + " " + booking.getCustomer().getLastName());
        name.setStyle("-fx-text-fill: #ffffff");
        nameHB.getChildren().add(name);

        HBox emailHB = new HBox();
        emailHB.setMouseTransparent(true);
        Label email = new Label(booking.getCustomer().getEmail());
        email.setStyle("-fx-text-fill: #ffffff");
        emailHB.getChildren().add(email);

        HBox phoneHB = new HBox();
        phoneHB.setMouseTransparent(true);
        Label phone = new Label(booking.getCustomer().getPhone());
        phone.setStyle("-fx-text-fill: #ffffff");
        phoneHB.getChildren().add(phone);

        HBox persNoteHB = new HBox();
        persNoteHB.setMouseTransparent(true);
        Label persNote = new Label(booking.getPersonalNote());
        persNote.setStyle("-fx-text-fill: #ffffff");
        persNote.setWrapText(true);
        persNoteHB.getChildren().add(persNote);

        this.getChildren().addAll(orgHB, timeHB, dateHB, nameHB, emailHB, phoneHB, persNoteHB);

    }

    public Booking getBooking(){
        return booking;
    }
}