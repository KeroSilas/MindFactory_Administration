package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.CalendarBooking;
import group3.mindfactory_administration.model.CalendarCell;
import group3.mindfactory_administration.model.Tasks.GetBookingsTask;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Calendar idea inspired by https://gist.github.com/Da9el00/f4340927b8ba6941eb7562a3306e93b6
// Drag and drop inspired by https://stackoverflow.com/questions/38172278/drag-and-drop-a-node-into-another-node
public class CalendarController {

    @FXML private GridPane calendarGrid;
    @FXML private MFXComboBox<String> monthComboBox;
    @FXML private MFXComboBox<String> yearComboBox;

    private LocalDate date = LocalDate.now();

    @FXML
    private void handleNextMonth() {
        nextMonth();
    }

    @FXML
    private void handlePreviousMonth() {
        previousMonth();
    }

    public void initialize() {
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December");
        monthComboBox.selectIndex(date.getMonthValue() - 1);
        monthComboBox.setOnAction(event -> {
            date = date.withMonth(monthComboBox.getSelectionModel().getSelectedIndex() + 1);
            drawCalendar();
        });
        yearComboBox.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
                "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035");
        yearComboBox.selectIndex(date.getYear() - 2020);
        yearComboBox.setOnAction(event -> {
            date = date.withYear(yearComboBox.getSelectionModel().getSelectedIndex() + 2020);
            drawCalendar();
        });

        drawCalendar();

        calendarGrid.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof CalendarBooking ce) {
                ce.setClicked(true);
                CalendarCell cc = (CalendarCell) ce.getParent();
                Alert alert = new Alert(Alert.AlertType.NONE, ce + "\n" + cc, ButtonType.NO, ButtonType.YES);
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

        GetBookingsTask getBookingsTask = new GetBookingsTask();
        getBookingsTask.setOnSucceeded(e -> {
            LocalDate firstDayOfMonth = date.withDayOfMonth(1);
            int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
            int dayOfMonth = 1;
            for (int i = 0; i < 6; i++) {
                for (int j = dayOfWeek - 1; j < 7; j++) {
                    if (dayOfMonth > date.lengthOfMonth()) {
                        break;
                    }
                    List<Booking> bookingsToday = new ArrayList<>();
                    for (Booking booking : getBookingsTask.getValue()) {
                        if (booking.getStartDate().equals(date.withDayOfMonth(dayOfMonth))) {
                            bookingsToday.add(booking);
                        }
                    }
                    CalendarCell cell = new CalendarCell(date.withDayOfMonth(dayOfMonth), bookingsToday);
                    calendarGrid.add(cell, j, i);

                    dayOfMonth++;
                }
                dayOfWeek = 1;
            }
        });
        Thread thread = new Thread(getBookingsTask);
        thread.setDaemon(true);
        thread.start();
    }
}
