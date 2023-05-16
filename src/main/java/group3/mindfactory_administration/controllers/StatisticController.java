package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.tasks.CountActivityTask;
import group3.mindfactory_administration.model.tasks.CountOrgTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;

/*
 * This class controls the statistic view.
 * It displays a bar chart with the number of bookings for a given time period.
 * The user can choose between counting the number of bookings for each activity or each organisation.
 */

public class StatisticController {

    private CountOrgTask countOrgTask;
    private CountActivityTask countActivityTask;

    XYChart.Series<String, Integer> seriesOrg, seriesAktivitet;
    Axis<String> xAxis;
    Axis<Integer> yAxis;

    @FXML private BarChart<String, Integer> barChart;
    @FXML private MFXDatePicker fromDP, toDP;
    @FXML private MFXButton aktivitetBtn, organisationBtn;

    @FXML
    void handleFra() {
        // Update the start date for the tasks and force them to update their data, instead of waiting for the set delay to pass
        countActivityTask.setStartDate(fromDP.getValue());
        countOrgTask.setStartDate(fromDP.getValue());
        countActivityTask.updateData();
        countOrgTask.updateData();
    }

    @FXML
    void handleTil() {
        countActivityTask.setEndDate(toDP.getValue());
        countOrgTask.setEndDate(toDP.getValue());
        countActivityTask.updateData();
        countOrgTask.updateData();
    }

    @FXML
    void handleAktivitet() {
        aktivitetBtn.setDisable(true);
        organisationBtn.setDisable(false);
        xAxis.setLabel("Aktiviteter");

        // Clear the bar chart and add the series with the activity data
        barChart.getData().clear();
        barChart.getData().add(seriesAktivitet);
    }

    @FXML
    void handleOrganisation() {
        organisationBtn.setDisable(true);
        aktivitetBtn.setDisable(false);
        xAxis.setLabel("Organisationer");

        // Clear the bar chart and add the series with the organisation data
        barChart.getData().clear();
        barChart.getData().add(seriesOrg);
    }

    public void initialize() {
        // Set the data pickers to be from a year ago to a week from now by default
        toDP.setValue(LocalDate.now().plusDays(31));
        fromDP.setValue(toDP.getValue().minusDays(400));

        seriesOrg = new XYChart.Series<>();
        seriesAktivitet = new XYChart.Series<>();
        xAxis = barChart.getXAxis();
        yAxis = barChart.getYAxis();
        barChart.setAnimated(false); // Disabled animations since the labels would otherwise be displayed incorrectly
        barChart.getData().add(seriesOrg); // Add the series with the organisation data by default
        xAxis.setLabel("Organisationer");

        // Starts a new thread that counts the number an activity has appeared in a given time period
        countActivityTask = new CountActivityTask(fromDP.getValue(), toDP.getValue());
        countActivityTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seriesAktivitet.getData().clear();
                for (String key : newValue.keySet()) {
                    seriesAktivitet.getData().add(new XYChart.Data<>(key, newValue.get(key)));
                }
            }
        });
        Thread thread = new Thread(countActivityTask);
        thread.setDaemon(true);
        thread.start();

        // Starts a new thread that counts the number an organisation has booked in a given time period
        countOrgTask = new CountOrgTask(fromDP.getValue(), toDP.getValue());
        countOrgTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seriesOrg.getData().clear();
                for (String key : newValue.keySet()) {
                    seriesOrg.getData().add(new XYChart.Data<>(key, newValue.get(key)));
                }
            }
        });
        Thread thread2 = new Thread(countOrgTask);
        thread2.setDaemon(true);
        thread2.start();
    }
}
