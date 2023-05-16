package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.UpcomingBooking;
import group3.mindfactory_administration.model.tasks.CountOrgTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static javafx.stage.Modality.APPLICATION_MODAL;

/*
 * This class controls the dashboard view.
 * It displays a list of upcoming bookings and a bar chart for a quick overview of the bookings.
 * It also displays a picture, which the administrator can change.
 */

public class DashboardController {

    private EditBookingController editBookingController;

    XYChart.Series<String, Integer> series;
    Axis<String> xAxis;
    Axis<Integer> yAxis;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private HBox hBoxSP;
    @FXML
    private ImageView imageView;

    @FXML
    Button chooseImageBtn;

    private List<Booking> bookings;

    public DashboardController() {
    }

    public void initialize() {
        series = new XYChart.Series<>();
        xAxis = barChart.getXAxis();
        yAxis = barChart.getYAxis();
        barChart.setAnimated(false); // Disabled animations since the labels would otherwise be displayed incorrectly
        barChart.getData().add(series);
        xAxis.setLabel("Organisationer");

        // Starts a new thread that counts the number an organisation has booked in a given time period
        CountOrgTask countOrgTask = new CountOrgTask(LocalDate.now().minusDays(365), LocalDate.now());
        countOrgTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                series.getData().clear();
                for (String key : newValue.keySet()) {
                    series.getData().add(new XYChart.Data<>(key, newValue.get(key)));
                }
            }
        });
        Thread thread = new Thread(countOrgTask);
        thread.setDaemon(true);
        thread.start();
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
                    Platform.runLater(() -> editBookingController.exportFromBooking());

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

    @FXML
    void chooseImage(ActionEvent event) throws IOException {

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll();
            new FileChooser.ExtensionFilter("jpeg File", ".jpg");

            Node source = (Node) event.getSource();
            File file = fileChooser.showOpenDialog(source.getScene().getWindow());
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }

    }



    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

}



