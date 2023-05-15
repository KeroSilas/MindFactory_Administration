package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.Booking;
import group3.mindfactory_administration.model.tasks.DeleteBookingTask;
import group3.mindfactory_administration.model.tasks.EditBookingTask;
import group3.mindfactory_administration.model.tasks.EditBookingTask;
import io.github.palexdev.materialfx.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EditBookingController {
    private Booking booking;

    @FXML
    private ToggleGroup assistanceType;

    @FXML
    private MFXRadioButton læringsRB, annesofieRB;

    @FXML
    private MFXProgressSpinner progressSpinner;

    @FXML
    private MFXTextField telefonTF, stillingTF, fornavnTF, emailTF, efternavnTF, deltagereTF, ankomstTF, afgangTF, afdelingTF;

    @FXML
    private Label tilLabel,fraLabel, alertLabel;

    @FXML
    private MFXButton tilføjBtn, sletEquipBtn, sletBtn, gemBtn;

    @FXML
    private MFXComboBox<String> transportCB, udstyrCB, tilCB, organisationCB, fraCB, forplejningCB, forløbCB, datoCB, aktivitetCB;

    @FXML
    private MFXListView<String> udstyrLV;


    @FXML
    void handleSave(ActionEvent event){

        // Starts a new thread to edit a booking
        // Once the thread is done the editBtn will be enabled again
        EditBookingTask editBookingTask = new EditBookingTask(booking);
        editBookingTask.setOnSucceeded(e -> {

        });
        Thread thread = new Thread(editBookingTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void handleSlet(ActionEvent event) {

        // Shows the progressSpinner and disables the sletBtn
        progressSpinner.setVisible(true);
        sletBtn.setDisable(true);
        sletBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        // Starts a new thread to delete the booking
        // Once the thread is done, the progressSpinner will be hidden and the sletBtn will be enabled again
        DeleteBookingTask deleteBookingTask = new DeleteBookingTask((booking.getBookingID()));
        deleteBookingTask.setOnSucceeded(e -> {


            progressSpinner.setVisible(false);
            sletBtn.setDisable(false);
            sletBtn.setStyle("-fx-background-color: #BD2323FF; -fx-text-fill: #ffffff;");

        });
        Thread thread = new Thread(deleteBookingTask);
        thread.setDaemon(true);
        thread.start();
    }


    @FXML
    void handleSletEquip(ActionEvent event) {
        List<String> equipmentList = udstyrLV.getSelectionModel().getSelectedValues();
        for (String equipment : equipmentList) {
            udstyrLV.getItems().remove(equipment);
        }
    }
    @FXML
    void handleTilføjEquip(ActionEvent event) {
        udstyrLV.getItems().add(udstyrCB.getSelectionModel().getSelectedItem());
    }


    private void setBooking(Booking booking) {
        this.booking = booking;
    }

    private boolean isInputValid() {
        boolean success = true;

        if (fornavnTF.getText().isEmpty()) {
            fornavnTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            fornavnTF.setStyle("-fx-border-color: lightgrey");
        }

        if (efternavnTF.getText().isEmpty()) {
            efternavnTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            efternavnTF.setStyle("-fx-border-color: lightgrey");
        }

        // https://stackoverflow.com/questions/60282362/regex-pattern-for-email
        if (emailTF.getText().isEmpty() || !emailTF.getText().matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) {
            emailTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            emailTF.setStyle("-fx-border-color: lightgrey");
        }

        if (telefonTF.getText().isEmpty() || !telefonTF.getText().matches("^[0-9]+$")) {
            telefonTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            telefonTF.setStyle("-fx-border-color: lightgrey");
        }

        if (datoCB.getValue() == null) {
            datoCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            datoCB.setStyle("-fx-border-color: lightgrey");
        }

        if (fraCB.getValue() == null) {
            fraCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            fraCB.setStyle("-fx-border-color: lightgrey");
        }

        if (tilCB.getValue() == null) {
            tilCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            tilCB.setStyle("-fx-border-color: lightgrey");
        }

        if (forløbCB.getValue() == null) {
            forløbCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            forløbCB.setStyle("-fx-border-color: lightgrey");
        }

        if (transportCB.getValue() == null) {
            transportCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            transportCB.setStyle("-fx-border-color: lightgrey");
        }

        if (ankomstTF.getText().isEmpty()) {
            ankomstTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            ankomstTF.setStyle("-fx-border-color: lightgrey");
        }

        if (afgangTF.getText().isEmpty()) {
            afgangTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            afgangTF.setStyle("-fx-border-color: lightgrey");
        }

        if (deltagereTF.getText().isEmpty()) {
            deltagereTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            deltagereTF.setStyle("-fx-border-color: lightgrey");
        }

        if (forplejningCB.getValue() == null) {
            forplejningCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            forplejningCB.setStyle("-fx-border-color: lightgrey");
        }

        if (organisationCB.getValue() == null) {
            organisationCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            organisationCB.setStyle("-fx-border-color: lightgrey");
        }

        if (afdelingTF.getText().isEmpty()) {
            afdelingTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            afdelingTF.setStyle("-fx-border-color: lightgrey");
        }

        if (stillingTF.getText().isEmpty()) {
            stillingTF.setStyle("-fx-border-color: red");
            success = false;
        } else {
            stillingTF.setStyle("-fx-border-color: lightgrey");
        }

        if (aktivitetCB.getValue() == null) {
            aktivitetCB.setStyle("-fx-border-color: red");
            success = false;
        } else {
            aktivitetCB.setStyle("-fx-border-color: lightgrey");
        }

        return success;
    }
}