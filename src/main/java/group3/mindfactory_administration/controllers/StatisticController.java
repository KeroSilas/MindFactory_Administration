package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.Tasks.CountActivityTask;
import group3.mindfactory_administration.model.Tasks.CountOrgTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.HashMap;

public class StatisticController {

    private HashMap<String, Integer> activityMap;
    private HashMap<String, Integer> orgMap;

    private String status;

    XYChart.Series<String, Integer> series;
    Axis<String> xAxis;
    Axis<Integer> yAxis;

    @FXML
    private MFXDatePicker fromDP, toDP;

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private MFXButton aktivitetBtn, organisationBtn;

    @FXML
    void handleReset() {

    }

    @FXML
    void handleAktivitet() {
        aktivitetBtn.setDisable(true);
        organisationBtn.setDisable(false);

        //drawActivityData();
        status = "aktivitet";
    }

    @FXML
    void handleOrganisation() {
        organisationBtn.setDisable(true);
        aktivitetBtn.setDisable(false);

        //drawOrgData();
        status = "organisation";
    }

    public void initialize() {
        status = "organisation";

        series = new XYChart.Series<>();
        xAxis = barChart.getXAxis();
        yAxis = barChart.getYAxis();

        CountActivityTask countActivityTask = new CountActivityTask();
        countActivityTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                activityMap = newValue;
                if (status.equals("aktivitet")) {
                    drawActivityData();
                }
            }
        });
        Thread thread = new Thread(countActivityTask);
        thread.setDaemon(true);
        thread.start();

        CountOrgTask countOrgTask = new CountOrgTask();
        countOrgTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orgMap = newValue;
                if (status.equals("organisation")) {
                    drawOrgData();
                }
            }
        });
        Thread thread2 = new Thread(countOrgTask);
        thread2.setDaemon(true);
        thread2.start();
    }

    private void drawActivityData() {
        series.getData().clear();
        for (String key : activityMap.keySet()) {
            series.getData().add(new XYChart.Data<>(key, activityMap.get(key)));
        }
        barChart.getData().add(series);
    }

    private void drawOrgData() {
        series.getData().clear();
        for (String key : orgMap.keySet()) {
            series.getData().add(new XYChart.Data<>(key, orgMap.get(key)));
        }
        barChart.getData().add(series);
    }

}
