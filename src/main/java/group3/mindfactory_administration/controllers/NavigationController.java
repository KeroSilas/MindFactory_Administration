package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.tasks.GetBookingsTask;
import group3.mindfactory_administration.model.tasks.SendReminderEmailTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

/*
 * This class controls the navigation bar.
 * Depending on which button the user clicks, the corresponding view is loaded.
 * The navigation bar also contains a notification center.
 */

// References:
// https://stackoverflow.com/questions/18619394/loading-new-fxml-in-the-same-scene
// Specifically the answer by Clite Tailor and exceptionsAreBad
public class NavigationController {

    private List<Booking> bookings;

    private BorderPane view1, view2, view3;
    private CalendarController calendarController;
    private DashboardController dashboardController;
    private Image notificationOn, notificationOff;

    @FXML private ImageView notificationImageView;
    @FXML private MFXButton dashboardBtn, kalenderBtn, statistikBtn;
    @FXML private StackPane stackPane;

    @FXML
    void handleDashboard() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        kalenderBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");
        statistikBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");


        // Clear the stack pane and add the view
        stackPane.getChildren().clear();
        stackPane.getChildren().add(view1);
    }

    @FXML
    void handleKalender() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");
        kalenderBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        statistikBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");


        stackPane.getChildren().clear();
        stackPane.getChildren().add(view2);
    }

    @FXML
    void handleStatistik() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");
        kalenderBtn.setStyle("-fx-background-color:  #111c24; -fx-text-fill: #ffffff");
        statistikBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view3);
    }

    @FXML
    void handleNotification() {
        notificationImageView.setImage(notificationOff);
    }

    public void initialize() {
        // Get the views from the FXML files
        FXMLLoader view1Loader = new FXMLLoader(AdministrationApplication.class.getResource("dashboard-view.fxml"));
        FXMLLoader view2Loader = new FXMLLoader(AdministrationApplication.class.getResource("calendar-view.fxml"));
        FXMLLoader view3Loader = new FXMLLoader(AdministrationApplication.class.getResource("statistic-view.fxml"));

        // Load the views into memory
        try {
            view1 = view1Loader.load();
            view2 = view2Loader.load();
            view3 = view3Loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dashboardController = view1Loader.getController();
        calendarController = view2Loader.getController();

        // Insert view1 into the stack pane by default
        dashboardBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        stackPane.getChildren().add(view1);

        notificationOff = new Image("file:src/main/resources/group3/mindfactory_administration/icons/notification-off.png");
        notificationOn = new Image("file:src/main/resources/group3/mindfactory_administration/icons/notification-on.png");

        // Start a thread to get the bookings from the database. Updates every 3 seconds.
        // Send the bookings to the dashboard and calendar controllers.
        GetBookingsTask getBookingsTask = new GetBookingsTask();
        getBookingsTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bookings = newValue;
                Platform.runLater(() -> {
                    dashboardController.setBookings(bookings);
                    dashboardController.drawUpcomingBookings();
                    calendarController.setBookings(bookings);
                    calendarController.drawCalendar();
                });

                if (oldValue != null && newValue.size() > oldValue.size()) {
                    notificationImageView.setImage(notificationOn);
                }
            }
        });
        Thread thread = new Thread(getBookingsTask);
        thread.setDaemon(true);
        thread.start();

        // Sends reminder emails to users within 7 days of their booking startDate. Checks every 60 seconds.
        SendReminderEmailTask sendReminderEmailTask = new SendReminderEmailTask();
        Thread thread2 = new Thread(sendReminderEmailTask);
        thread2.setDaemon(true);
        thread2.start();

    }

}
