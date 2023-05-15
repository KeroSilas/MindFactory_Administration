package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.dao.BookingDao;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.CalendarBooking;
import group3.mindfactory_administration.model.CalendarCell;
import group3.mindfactory_administration.model.UpcomingBooking;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static javafx.stage.Modality.APPLICATION_MODAL;

/*
 * This class controls the dashboard view.
 * It displays a list of upcoming bookings and a bar chart for a quick overview of the bookings.
 * It also displays a picture, which the administrator can change.
 */

public class DashboardController {

    private LocalDate currentDate = LocalDate.now();
    private DashboardController dashboardController;
    private List<Booking> upcomingBookings;
    @FXML
    private HBox hBoxSP;

    @FXML
    private BarChart<?, ?> barChart;
    private List<Booking> bookings;

    public DashboardController() {
    }

    public void initialize() {

        for(Booking booking : bookings){
            if(booking.getStartDate().isBefore(LocalDate.now().plusDays(7))){
                upcomingBookings.add(booking);
            }
        }

            for (Booking booking : upcomingBookings) {
                UpcomingBooking upcomingBooking = new UpcomingBooking(booking);
                hBoxSP.getChildren().add(upcomingBooking);
                System.out.println(upcomingBookings);
            }

        hBoxSP.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof UpcomingBooking cb) {
                //CalendarCell cc = (CalendarCell) cb.getParent();

                // TODO: Temporary, this will be replaced with an FXML file
                // Use this: https://stackoverflow.com/questions/68363535/passing-data-to-another-controller-in-javafx
                FXMLLoader fxmlLoader = new FXMLLoader(AdministrationApplication.class.getResource("editBooking.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    dashboardController = fxmlLoader.getController();
                    //dashboardController.setBooking(cb.getBooking());

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

