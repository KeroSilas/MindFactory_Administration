package group3.mindfactory_administration.controllers;

import group3.mindfactory_administration.model.*;
import group3.mindfactory_administration.model.tasks.*;
import group3.mindfactory_administration.model.tasks.EditBookingTask;
import io.github.palexdev.materialfx.controls.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;

public class EditBookingController {
    private Booking booking;

    private List<BookingTime> bookedTimes;
    private final ObservableList<LocalDate> dateList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

    @FXML
    private ToggleGroup assistanceType;

    @FXML
    private MFXRadioButton læringsRB, annesofieRB, ingenRB;

    @FXML
    private MFXProgressSpinner progressSpinner;

    @FXML
    private MFXTextField telefonTF, stillingTF, fornavnTF, emailTF, efternavnTF, deltagereTF, ankomstTF, afgangTF, afdelingTF;

    @FXML
    private Label tilLabel, fraLabel, alertLabel;

    @FXML
    private MFXButton tilføjBtn, sletEquipBtn, sletBtn, gemBtn, tilføjFilBtn, sletFilBtn;

    @FXML
    private MFXComboBox<LocalTime> fraCB, tilCB;

    @FXML
    private MFXComboBox<LocalDate> datoCB;

    @FXML
    private TextArea beskedTA, personligTA;

    @FXML
    private MFXListView<File> filLV;


    @FXML
    private MFXCheckbox noShow;

    @FXML
    private MFXComboBox<Catering> forplejningCB;

    @FXML
    private MFXComboBox<Organization> organisationCB;

    @FXML
    private MFXComboBox<Forløb> forløbCB;

    @FXML
    private MFXComboBox<Activity> aktivitetCB;
    @FXML
    private MFXComboBox<String> transportCB, udstyrCB;

    @FXML
    private MFXListView<String> udstyrLV;


    public void initialize(){
        GetOrganisationsTask getOrganisationsTask = new GetOrganisationsTask();
        getOrganisationsTask.setOnSucceeded(e -> {
            List<Organization> organisations = getOrganisationsTask.getValue();
            organisationCB.getItems().addAll(organisations);
        });
        Thread thread = new Thread(getOrganisationsTask);
        thread.start();

        GetForløbTask getForløbTask = new GetForløbTask();
        getForløbTask.setOnSucceeded(e -> {
            List<Forløb> forløb = getForløbTask.getValue();
            forløbCB.getItems().addAll(forløb);
        });
        Thread thread2 = new Thread(getForløbTask);
        thread2.start();

        GetActivitiesTask getActivitiesTask = new GetActivitiesTask();
        getActivitiesTask.setOnSucceeded(e -> {
            List<Activity> activities = getActivitiesTask.getValue();
            aktivitetCB.getItems().addAll(activities);
        });
        Thread thread3 = new Thread(getActivitiesTask);
        thread3.start();

        GetCateringTask getCateringTask = new GetCateringTask();
        getCateringTask.setOnSucceeded(e -> {
            List<Catering> catering = getCateringTask.getValue();
            forplejningCB.getItems().addAll(catering);
        });
        Thread thread4 = new Thread(getCateringTask);
        thread4.start();

        transportCB.getItems().addAll("Jeg kommer i lejet bus", "Jeg kommer i offentlig transport");
        udstyrCB.getItems().addAll("Robotter", "Sakse");

        // Add the ObservableLists to the ComboBoxes
        datoCB.setItems(dateList);
        fraCB.setItems(startTimeList);
        tilCB.setItems(endTimeList);

        // Gets the already booked times from the database every 5 seconds
        GetBookingTimesTask getBookingTimesTask = new GetBookingTimesTask();
        getBookingTimesTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bookedTimes = newValue;

                // Clear the dateList and add all the dates from today to 365 days from now
                // This is added inside the listener because a booking time can be deleted, and that needs to be added back to the dateList
                if (oldValue == null || newValue.size() != oldValue.size()) { // This is to prevent the dateList from being cleared every 5 seconds, if nothing has changed. But it still needs to be populated the first time
                    dateList.clear();
                    for (int i = 0; i < 365; i++) {
                        dateList.add(LocalDate.now().plusDays((1 + i)));
                    }
                }

                Iterator<LocalDate> dateIterator = dateList.iterator();
                while (dateIterator.hasNext()) {
                    LocalDate ld = dateIterator.next();
                    for (BookingTime bt : bookedTimes) {
                        if (bt.isWholeDay() && bt.getStartDate().equals(ld)) {
                            dateIterator.remove();
                            break;
                        }
                    }
                }

            }
        });
        Thread thread5 = new Thread(getBookingTimesTask);
        thread5.setDaemon(true);
        thread5.start();

        datoCB.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fraCB.getSelectionModel().clearSelection();
                tilCB.getSelectionModel().clearSelection();
                startTimeList.clear();
                endTimeList.clear();

                fraCB.setDisable(false);
                fraLabel.setDisable(false);
                tilCB.setDisable(true);
                tilLabel.setDisable(true);

                for (int i = 7; i < 23; i++) {
                    startTimeList.add(LocalTime.of(i,0));
                }
                Iterator<LocalTime> startTimeIterator = startTimeList.iterator();
                while (startTimeIterator.hasNext()) {
                    LocalTime lt = startTimeIterator.next();
                    for (BookingTime bt : bookedTimes) {
                        if (datoCB.getValue().equals(bt.getStartDate())) {
                            if ((lt.isAfter(bt.getStartTime()) || lt.equals(bt.getStartTime())) && (lt.isBefore(bt.getEndTime()) || lt.equals(bt.getEndTime()))) {
                                startTimeIterator.remove();
                                break;
                            }
                        }
                    }
                }

            }
        });

        // If the selected time is a halfday booking before 12, then don't add the hours for the first half of the day
        fraCB.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tilCB.getSelectionModel().clearSelection();
                endTimeList.clear();

                tilCB.setDisable(false);
                tilLabel.setDisable(false);

                // I heard you like spaghetti, so I put spaghetti in your spaghetti
                int hour = newValue.plusHours(1).getHour();
                for (int i = hour; i < 24; i++) {
                    endTimeList.add(LocalTime.of(i,0));
                }
                Iterator<LocalTime> endTimeIterator = endTimeList.iterator();
                while (endTimeIterator.hasNext()) {
                    LocalTime lt = endTimeIterator.next();
                    for (BookingTime bt : bookedTimes) {
                        if (newValue.isBefore(bt.getStartTime()) && datoCB.getValue().equals(bt.getStartDate())) {
                            if ((lt.isAfter(bt.getStartTime()))) {
                                endTimeIterator.remove();
                                break;
                            }
                        }
                    }
                }

            }
        });
    }

    public void exportFromBooking() {
        /*organisationCB.selectItem(booking.getOrganization());
        aktivitetCB.selectItem(booking.getActivity());
        forplejningCB.selectItem(booking.getCatering());
        forløbCB.selectItem(booking.getÅbenSkoleForløb());*/

        afdelingTF.setText(booking.getCustomer().getDepartment());
        stillingTF.setText(booking.getCustomer().getPosition());

        fornavnTF.setText(booking.getCustomer().getFirstName());
        efternavnTF.setText(booking.getCustomer().getLastName());
        telefonTF.setText(booking.getCustomer().getPhone());
        if (booking.getOrganization().getAssistance() == null || booking.getOrganization().getAssistance().equals("Ingen")) {
            ingenRB.setSelected(true);
        } else if (booking.getOrganization().getAssistance().equals("Anne-Sofie Didriksen")){
            annesofieRB.setSelected(true);
        } else if (booking.getOrganization().getAssistance().equals("Læringskonsulent")) {
            læringsRB.setSelected(true);
        }
        emailTF.setText(booking.getCustomer().getEmail());
        deltagereTF.setText(String.valueOf(booking.getOrganization().getParticipants()));
        fraCB.setText(String.valueOf(booking.getStartTime()));
        tilCB.setText(String.valueOf(booking.getEndTime()));
        datoCB.setText(String.valueOf(booking.getStartDate()));
        noShow.setSelected(booking.isNoShow());
        personligTA.setText(booking.getPersonalNote());
        beskedTA.setText(booking.getMessageToAS());
        udstyrLV.getItems().addAll(booking.getEquipmentList());
        filLV.getItems().addAll(booking.getFileList());
        afgangTF.setText(String.valueOf(booking.getEndTime()));
        ankomstTF.setText(String.valueOf(booking.getStartTime()));

        //transportCB.selectItem(String.valueOf(booking.getÅbenSkoleForløb()));

    }

    private void importToBooking() {
        System.out.println("IMPORT");
        booking.setOrganization(organisationCB.getSelectionModel().getSelectedItem());
        booking.getCustomer().setDepartment(afdelingTF.getText());
        booking.getCustomer().setPosition(stillingTF.getText());
        
        booking.getCustomer().setFirstName(fornavnTF.getText());
        booking.getCustomer().setLastName(efternavnTF.getText());
        booking.getCustomer().setPhone(telefonTF.getText());
        if (læringsRB.isSelected()) {
            booking.getOrganization().setAssistance("Læring konsulent");
        } else if (annesofieRB.isSelected()) {
            booking.getOrganization().setAssistance("Anne-Sofie Dideriksen");
        } else if (ingenRB.isSelected()) {
            booking.getOrganization().setAssistance("Ingen");
        }
        booking.getOrganization().setParticipants(Integer.parseInt(deltagereTF.getText()));
        booking.setStartTime(fraCB.getValue());
        booking.setEndTime(tilCB.getValue());
        booking.setStartDate(datoCB.getValue());
        booking.setNoShow(noShow.isSelected());
        booking.setPersonalNote(personligTA.getText());
        booking.setMessageToAS(beskedTA.getText());
        booking.setActivity(aktivitetCB.getValue());
        booking.setCatering(forplejningCB.getValue());
        booking.setEquipmentList(udstyrLV.getItems());
        booking.setFileList(filLV.getItems());

        booking.setÅbenSkoleForløb(forløbCB.getSelectionModel().getSelectedItem());
        booking.getÅbenSkoleForløb().setTransportDeparture(afgangTF.getText());
        booking.getÅbenSkoleForløb().setTransportArrival(ankomstTF.getText());
        booking.getÅbenSkoleForløb().setTransportType(transportCB.getSelectionModel().getSelectedItem());
    }

    @FXML
    void handleTilføjFil(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        filLV.getItems().addAll(files);
    }


    @FXML
    void handleSletFil(ActionEvent event) {
        List<File> filesList = filLV.getSelectionModel().getSelectedValues();
        for (File file : filesList) {
            filLV.getItems().remove(file);
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (isInputValid()) {
            importToBooking();
            // Starts a new thread to edit a booking
            // Once the thread is done the editBtn will be enabled again
            EditBookingTask editBookingTask = new EditBookingTask(booking);
            editBookingTask.setOnSucceeded(e -> {
                System.out.println("DONE");
            });
            Thread thread = new Thread(editBookingTask);
            thread.setDaemon(true);
            thread.start();
            System.out.println("SAVE");
        }
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


    public void setBooking(Booking booking) {
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