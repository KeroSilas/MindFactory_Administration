package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.tasks.SendReminderEmailTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/*
 * This class controls the navigation bar.
 * Depending on which button the user clicks, the corresponding view is loaded.
 * The navigation bar also contains a notification center.
 */

// References:
// https://stackoverflow.com/questions/18619394/loading-new-fxml-in-the-same-scene
// Specifically the answer by Clite Tailor and exceptionsAreBad
public class NavigationController {

    private BorderPane view1, view2, view3;

    @FXML
    private MFXNotificationCenter notificationCenter;

    @FXML
    private MFXButton dashboardBtn;

    @FXML
    private MFXButton kalenderBtn;

    @FXML
    private StackPane stackPane;

    @FXML
    private MFXButton statistikBtn;

    @FXML
    void handleDashboard() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        kalenderBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
        statistikBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");

        // Clear the stack pane and add the view
        stackPane.getChildren().clear();
        stackPane.getChildren().add(view1);

        // Test notification
        //Notification notification = new Notification("Test", "Test");
        //notificationCenter.getNotifications().add(notification);
    }

    @FXML
    void handleKalender() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
        kalenderBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        statistikBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view2);
    }

    @FXML
    void handleStatistik() {
        // Style the buttons to indicate which view is active
        dashboardBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
        kalenderBtn.setStyle("-fx-background-color:  #ffffff; -fx-text-fill: #000000");
        statistikBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view3);
    }

    public void initialize() {
        // Sends reminder emails to users within 7 days of their booking startDate. Checks every 60 seconds.
        SendReminderEmailTask sendReminderEmailTask = new SendReminderEmailTask();
        Thread thread = new Thread(sendReminderEmailTask);
        thread.setDaemon(true);
        thread.start();

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

        // Insert view1 into the stack pane by default
        dashboardBtn.setStyle("-fx-background-color:  #94c83d; -fx-text-fill: #ffffff");
        stackPane.getChildren().add(view1);
    }

}
