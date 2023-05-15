package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.AdministrationApplication;
import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.CalendarBooking;
import group3.mindfactory_administration.model.CalendarCell;
import group3.mindfactory_administration.model.tasks.GetBookingsTask;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javafx.stage.Modality.APPLICATION_MODAL;

/*
 * This class controls the calendar view.
 * It displays a calendar in a grid, and stores a CalendarCell for each day of the month.
 * Each CalendarCell stores either 1 or 2 CalendarBooking objects inside of it, which are displayed in the cell.
 * Each CalendarBooking object stores a Booking object inside of it, which contains all the information about the booking.
 */

// References:
// https://gist.github.com/Da9el00/f4340927b8ba6941eb7562a3306e93b6
// https://stackoverflow.com/questions/38172278/drag-and-drop-a-node-into-another-node
public class CalendarController {

    private List<Booking> bookings;
    private EditBookingController editBookingController;

    @FXML private GridPane calendarGrid;
    @FXML private MFXComboBox<String> monthComboBox;
    @FXML private MFXComboBox<String> yearComboBox;

    private LocalDate currentDate = LocalDate.now();

    @FXML
    private void handleNextMonth() {
        nextMonth();
    }

    @FXML
    private void handlePreviousMonth() {
        previousMonth();
    }

    public void initialize() {
        bookings = new ArrayList<>();

        monthComboBox.getItems().addAll("Januar", "Februar", "Marts", "April", "Maj", "Juni", "Juli", "August",
                "September", "Oktober", "November", "December");
        monthComboBox.selectIndex(currentDate.getMonthValue() - 1); // Months are 1-12, not 0-11, so -1 is needed.
        // When the user selects a month, the calendar is redrawn, and currentDate is updated.
        monthComboBox.setOnAction(event -> {
            currentDate = currentDate.withMonth(monthComboBox.getSelectionModel().getSelectedIndex() + 1); // Months are 1-12, not 0-11, so +1 is needed.
            drawCalendar();
        });
        yearComboBox.getItems().addAll("2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
                "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035");
        yearComboBox.selectIndex(currentDate.getYear() - 2020); // Years are 2020-2035, so -2020 is needed to get the index.
        // When the user selects a year, the calendar is redrawn, and currentDate is updated.
        yearComboBox.setOnAction(event -> {
            currentDate = currentDate.withYear(yearComboBox.getSelectionModel().getSelectedIndex() + 2020); // Years are 2020-2035, so +2020 is needed to get the year.
            drawCalendar();
        });

        // When the user clicks on a CalendarBooking, open the edit booking window.
        calendarGrid.setOnMouseClicked(e -> {
            if (e.getTarget() instanceof CalendarBooking cb) {
                FXMLLoader fxmlLoader = new FXMLLoader(AdministrationApplication.class.getResource("editBooking.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    editBookingController = fxmlLoader.getController();
                    editBookingController.setBooking(cb.getBooking());

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

    private void nextMonth() {
        currentDate = currentDate.plusMonths(1);
        monthComboBox.selectIndex(currentDate.getMonthValue() - 1);
        if (currentDate.getYear() != yearComboBox.getSelectionModel().getSelectedIndex() + 2020) {
            yearComboBox.selectIndex(currentDate.getYear() - 2020);
        }
    }

    private void previousMonth() {
        currentDate = currentDate.minusMonths(1);
        monthComboBox.selectIndex(currentDate.getMonthValue() - 1);
        if (currentDate.getYear() != yearComboBox.getSelectionModel().getSelectedIndex() + 2020) {
            yearComboBox.selectIndex(currentDate.getYear() - 2020);
        }
    }

    public void drawCalendar() {
        calendarGrid.getChildren().clear(); // Clear the grid before drawing the calendar

        // Find the first day of the month and the day of the week it is on, so that we know where on the grid to start drawing the calendar
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1); // First day of the month
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday

        int dayOfMonth = 1;
        int lengthOfMonth = currentDate.lengthOfMonth();

        // Draws the calendar itself by adding CalendarCells to the grid for each day of the month
        // The calendar is 6 rows by 7 columns.
        for (int i = 0; i < 6; i++) {
            for (int j = dayOfWeek - 1; j < 7; j++) {
                if (dayOfMonth > lengthOfMonth) {
                    break; // Reached the end of the month, so stop the loop
                }

                // Get all the bookings that are on the current dayOfMonth
                List<Booking> bookingsToday = new ArrayList<>();
                for (Booking booking : bookings) {
                    // Takes currentDate together with dayOfMonth and compares it to the bookings startDate.
                    // This is done because currentDate is only adjusted for the month and year, not the day, and we need a full LocalDate to compare, not just dayOfMonth.
                    if (booking.getStartDate().equals(currentDate.withDayOfMonth(dayOfMonth))) {
                        bookingsToday.add(booking);
                    }
                }

                // Create a new CalendarCell and add it to the grid
                CalendarCell cell = new CalendarCell(currentDate.withDayOfMonth(dayOfMonth), bookingsToday);
                calendarGrid.add(cell, j, i);

                dayOfMonth++;
            }
            dayOfWeek = 1; // Reset the day of the week to Monday after the first row has been drawn
        }

    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
