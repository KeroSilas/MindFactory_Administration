package group3.mindfactory_administration.model;

import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.dao.BookingDaoImpl;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class UpcomingBooking extends VBox {

    private final Booking booking;

    //BookingDao bookingDao = new BookingDaoImpl();

    public UpcomingBooking(Booking booking) {
        this.booking = booking;

        //List<Booking> upcomingBookings = bookingDao.getOneWeekOutBookings();

        // Inserting information
        HBox orgHB = new HBox();
        orgHB.setMouseTransparent(true); // Makes it possible to click on the upcomingBooking object itself, even though the user is clicking on this HBox
        Label org = new Label(booking.getCustomer().getDepartment());
        org.setFont(Font.font("System", FontWeight.BOLD, 14)); // Bold font for the organization name
        org.setStyle("-fx-text-fill: #ffffff");
        //orgHB.getChildren().add(org);

        HBox timeHB = new HBox();
        timeHB.setMouseTransparent(true);
        Label time = new Label(booking.getStartTime().toString());
        time.setStyle("-fx-text-fill: #ffffff");
        //timeHB.getChildren().add(time);

        HBox dateHB = new HBox();
        dateHB.setMouseTransparent(true);
        Label date = new Label(booking.getStartDate().toString());
        date.setStyle("-fx-text-fill: #ffffff");
        //dateHB.getChildren().add(date);

        HBox nameHB = new HBox();
        nameHB.setMouseTransparent(true);
        Label name = new Label(booking.getCustomer().getFirstName());
        name.setStyle("-fx-text-fill: #ffffff");
        //nameHB.getChildren().add(name);

        HBox emailHB = new HBox();
        emailHB.setMouseTransparent(true);
        Label email = new Label(booking.getCustomer().getEmail());
        email.setStyle("-fx-text-fill: #ffffff");
        //emailHB.getChildren().add(email);

        HBox phoneHB = new HBox();
        phoneHB.setMouseTransparent(true);
        Label phone = new Label(booking.getCustomer().getPhone());
        phone.setStyle("-fx-text-fill: #ffffff");
       //phoneHB.getChildren().add(phone);

        HBox persNoteHB = new HBox();
        persNoteHB.setMouseTransparent(true);
        Label persNote = new Label(booking.getPersonalNote());
        persNote.setStyle("-fx-text-fill: #ffffff");
        //persNoteHB.getChildren().add(persNote);

        this.getChildren().addAll(org, time, date, name, email, phone, persNote);


    }
    public Booking getUpcomingBookings() {
        return booking;
    }

}