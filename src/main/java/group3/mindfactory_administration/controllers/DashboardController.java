package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.Booking;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

import java.util.List;

/*
 * This class controls the dashboard view.
 * It displays a list of upcoming bookings and a bar chart for a quick overview of the bookings.
 * It also displays a picture, which the administrator can change.
 */

public class DashboardController {

    private List<Booking> bookings;

    @FXML private BarChart<?, ?> barChart;

    public void initialize() {

    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}
