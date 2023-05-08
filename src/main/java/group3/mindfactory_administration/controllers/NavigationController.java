package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.Notification;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

// Inspired by https://stackoverflow.com/questions/18619394/loading-new-fxml-in-the-same-scene
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
        kalenderBtn.setDisable(false);
        dashboardBtn.setDisable(true);
        statistikBtn.setDisable(false);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view1);

        // Test notification
        Notification notification = new Notification("Test", "Test");
        notificationCenter.getNotifications().add(notification);
    }

    @FXML
    void handleKalender() {
        kalenderBtn.setDisable(true);
        dashboardBtn.setDisable(false);
        statistikBtn.setDisable(false);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view2);
    }

    @FXML
    void handleStatistik() {
        kalenderBtn.setDisable(false);
        dashboardBtn.setDisable(false);
        statistikBtn.setDisable(true);

        stackPane.getChildren().clear();
        stackPane.getChildren().add(view3);
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

        // Insert view1 into the stack pane by default
        dashboardBtn.setDisable(true);
        stackPane.getChildren().clear();
        stackPane.getChildren().add(view1);
    }

}
