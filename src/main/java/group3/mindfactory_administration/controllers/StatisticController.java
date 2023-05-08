package group3.mindfactory_administration.controllers;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

public class StatisticController {

    @FXML
    private MFXDatePicker fromDP, toDP;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    void handleReset() {

    }
}
