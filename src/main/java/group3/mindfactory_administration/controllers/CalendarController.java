package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.*;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.Optional;

// Calendar idea inspired by https://gist.github.com/Da9el00/f4340927b8ba6941eb7562a3306e93b6
// Drag and drop inspired by https://stackoverflow.com/questions/38172278/drag-and-drop-a-node-into-another-node
public class CalendarController {

    private BookingManager bookingManager;
    private LocalDate date = LocalDate.now();

    @FXML private GridPane calendarGrid;
    @FXML private MFXComboBox<String> monthComboBox;
    @FXML private MFXComboBox<String> yearComboBox;

    @FXML
    private void handleNextMonth() {
        nextMonth();
    }

    @FXML
    private void handlePreviousMonth() {
        previousMonth();
    }

    public void initialize() {
        bookingManager = BookingManager.getInstance();
        bookingManager.addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("bookings"))
                drawCalendar();
        });

        // Draw calendar when month is changed
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December");
        monthComboBox.selectIndex(date.getMonthValue() - 1);
        monthComboBox.setOnAction(event -> {
            date = date.withMonth(monthComboBox.getSelectionModel().getSelectedIndex() + 1);
            drawCalendar();
        });

        // Draw calendar when year is changed
        yearComboBox.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
                "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035");
        yearComboBox.selectIndex(date.getYear() - 2020);
        yearComboBox.setOnAction(event -> {
            date = date.withYear(yearComboBox.getSelectionModel().getSelectedIndex() + 2020);
            drawCalendar();
        });

        // Open new window when clicked on booking
        // TODO: Change this to open an fxml file
        calendarGrid.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof CalendarBooking ce) {
                ce.setClicked(true);
                Alert alert = new Alert(Alert.AlertType.NONE, ce.getBooking() + "\n" + ce.getTime(), ButtonType.NO, ButtonType.YES);
                alert.setTitle("Event Clicked");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    ce.setClicked(false);
                }
            }
        });
    }

    private void nextMonth() {
        date = date.plusMonths(1);
        monthComboBox.selectIndex(date.getMonthValue() - 1);
        if (date.getYear() != yearComboBox.getSelectionModel().getSelectedIndex() + 2020) {
            yearComboBox.selectIndex(date.getYear() - 2020);
        }
    }

    private void previousMonth() {
        date = date.minusMonths(1);
        monthComboBox.selectIndex(date.getMonthValue() - 1);
        if (date.getYear() != yearComboBox.getSelectionModel().getSelectedIndex() + 2020) {
            yearComboBox.selectIndex(date.getYear() - 2020);
        }
    }

    private void drawCalendar() {
        calendarGrid.getChildren().clear();

        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int dayOfMonth = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = dayOfWeek - 1; j < 7; j++) {
                if (dayOfMonth > date.lengthOfMonth()) {
                    break;
                }
                CalendarCell cell = new CalendarCell(date.withDayOfMonth(dayOfMonth));
                calendarGrid.add(cell, j, i);

                dayOfMonth++;
            }
            dayOfWeek = 1;
        }
    }

}
