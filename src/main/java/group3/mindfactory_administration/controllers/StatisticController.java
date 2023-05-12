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

        barChart.getData().clear();
        barChart.getData().add(seriesAktivitet);
    }

    @FXML
    void handleOrganisation() {
        organisationBtn.setDisable(true);
        aktivitetBtn.setDisable(false);
        xAxis.setLabel("Organisationer");

        barChart.getData().clear();
        barChart.getData().add(seriesOrg);
    }

    public void initialize() {
        toDP.setValue(LocalDate.now().plusDays(7));
        fromDP.setValue(toDP.getValue().minusDays(365));

        seriesOrg = new XYChart.Series<>();
        seriesAktivitet = new XYChart.Series<>();
        xAxis = barChart.getXAxis();
        yAxis = barChart.getYAxis();
        barChart.setAnimated(false);
        barChart.getData().add(seriesOrg);
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
