package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.UpcomingBooking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.*;

import static javafx.stage.Modality.APPLICATION_MODAL;

/*
 * This class controls the dashboard view.
 * It displays a list of upcoming bookings and a bar chart for a quick overview of the bookings.
 * It also displays a picture, which the administrator can change.
 */

public class DashboardController {

    private LocalDate currentDate = LocalDate.now();
    private EditBookingController editBookingController;
    private List<Booking> upcomingBookings;
    @FXML
    private HBox hBoxSP;

    @FXML
    private BarChart<?, ?> barChart;
    private List<Booking> bookings;

    public DashboardController() {
    }

    public void initialize() {
    }

    public void drawUpcomingBookings() {
        hBoxSP.getChildren().clear();
        List<Booking> upcomingBookings= new ArrayList<>();
        for(Booking booking : bookings){
            if(booking.getStartDate().isBefore(LocalDate.now().plusDays(7))){
                upcomingBookings.add(booking);
            }
        }

        Collections.sort(upcomingBookings);
        for (Booking booking : upcomingBookings) {
            UpcomingBooking upcomingBooking = new UpcomingBooking(booking);
            hBoxSP.getChildren().add(upcomingBooking);
        }

        hBoxSP.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof UpcomingBooking ub) {
                FXMLLoader fxmlLoader = new FXMLLoader(AdministrationApplication.class.getResource("editBooking.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    editBookingController = fxmlLoader.getController();
                    editBookingController.setBooking(ub.getBooking());

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(APPLICATION_MODAL);
                    stage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}

