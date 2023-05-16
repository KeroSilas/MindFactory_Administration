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
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class EditBookingController {
    private Booking booking;

    private String bookingType;

    private List<BookingTime> bookedTimes;
    private final ObservableList<LocalDate> dateList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();

    @FXML private VBox skoleVBox, virksomhedVBox;
    @FXML private MFXRadioButton læringsRB, annesofieRB, ingenRB;
    @FXML private MFXProgressSpinner progressSpinner;
    @FXML private MFXTextField telefonTF, stillingTF, fornavnTF, emailTF, efternavnTF, deltagereTF, ankomstTF, afgangTF, afdelingTF, bookingNummerTF;
    @FXML private Label tilLabel, fraLabel, alertLabel;
    @FXML private MFXButton tilføjBtn, sletEquipBtn, sletBtn, gemBtn, tilføjFilBtn, sletFilBtn;
    @FXML private MFXComboBox<LocalTime> fraCB, tilCB;
    @FXML private MFXComboBox<LocalDate> datoCB;
    @FXML private TextArea beskedTA, personligTA;
    @FXML private MFXListView<File> filLV;
    @FXML private MFXCheckbox noShow, åbenSkoleCheckBox;
    @FXML private MFXComboBox<Catering> forplejningCB;
    @FXML private MFXComboBox<Organization> organisationCB;
    @FXML private MFXComboBox<Forløb> forløbCB;
    @FXML private MFXComboBox<Activity> aktivitetCB;
    @FXML private MFXComboBox<String> transportCB, udstyrCB;
    @FXML private MFXListView<String> udstyrLV;


    public void initialize(){
        transportCB.getItems().addAll("Jeg kommer i lejet bus", "Jeg kommer i offentlig transport");
        udstyrCB.getItems().addAll("Robotter", "Sakse");

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
                fraCB.clear();
                tilCB.clear();
                // Prevents these fields from passing isInputValid() if they are not selected, because exportFromBooking() sets these values.
                fraCB.setValue(null);
                tilCB.setValue(null);

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
                tilCB.clear();
                tilCB.setValue(null);
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

        organisationCB.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadBookingTypeOptions(newValue.getOrganization());
        });
    }

    public void exportFromBooking() {
        loadBookingTypeOptions(booking.getOrganization().getOrganization());

        bookingNummerTF.setText(String.valueOf(booking.getBookingID()));

        fornavnTF.setText(booking.getCustomer().getFirstName());
        efternavnTF.setText(booking.getCustomer().getLastName());
        telefonTF.setText(booking.getCustomer().getPhone());
        emailTF.setText(booking.getCustomer().getEmail());
        afdelingTF.setText(booking.getCustomer().getDepartment());
        stillingTF.setText(booking.getCustomer().getPosition());

        fraCB.setValue(booking.getStartTime());
        tilCB.setValue(booking.getEndTime());
        datoCB.setValue(booking.getStartDate());
        noShow.setSelected(booking.isNoShow());
        personligTA.setText(booking.getPersonalNote());
        beskedTA.setText(booking.getMessageToAS());
        filLV.getItems().addAll(booking.getFileList());

        if (booking.getÅbenSkoleForløb().getÅbenSkoleForløb() != null) {
            åbenSkoleCheckBox.setSelected(true);
            forløbCB.setValue(booking.getÅbenSkoleForløb());
            transportCB.setValue(booking.getÅbenSkoleForløb().getTransportType());
            afgangTF.setText(booking.getÅbenSkoleForløb().getTransportDeparture());
            ankomstTF.setText(booking.getÅbenSkoleForløb().getTransportArrival());
        }

        organisationCB.setValue(booking.getOrganization());

        if (booking.getActivity().getActivity() != null) {
            aktivitetCB.setValue(booking.getActivity());
            forplejningCB.setValue(booking.getCatering());
            deltagereTF.setText(String.valueOf(booking.getOrganization().getParticipants()));
            udstyrLV.getItems().addAll(booking.getEquipmentList());
        }

        if (booking.getOrganization().getAssistance() == null || booking.getOrganization().getAssistance().equals("Ingen")) {
            ingenRB.setSelected(true);
        } else if (booking.getOrganization().getAssistance().equals("Anne-Sofie Dideriksen")){
            annesofieRB.setSelected(true);
        } else if (booking.getOrganization().getAssistance().equals("Læring konsulent")) {
            læringsRB.setSelected(true);
        }

    }

    private void importToBooking() {
        
        booking.getCustomer().setFirstName(fornavnTF.getText());
        booking.getCustomer().setLastName(efternavnTF.getText());
        booking.getCustomer().setPhone(telefonTF.getText());
        booking.getCustomer().setDepartment(afdelingTF.getText());
        booking.getCustomer().setPosition(stillingTF.getText());

        booking.setStartTime(fraCB.getValue());
        booking.setEndTime(tilCB.getValue());
        booking.setStartDate(datoCB.getValue());
        booking.setNoShow(noShow.isSelected());
        booking.setWholeDay(booking.getStartTime().isBefore(LocalTime.of(12, 0)) && booking.getEndTime().isAfter(LocalTime.of(12, 0)));
        System.out.println(booking.isWholeDay());
        booking.setPersonalNote(personligTA.getText());
        booking.setMessageToAS(beskedTA.getText());
        booking.setFileList(filLV.getItems());

        if (bookingType.equals("Skole") && åbenSkoleCheckBox.isSelected()) {
            booking.setÅbenSkoleForløb(forløbCB.getValue());
            booking.getÅbenSkoleForløb().setTransportDeparture(afgangTF.getText());
            booking.getÅbenSkoleForløb().setTransportArrival(ankomstTF.getText());
            booking.getÅbenSkoleForløb().setTransportType(transportCB.getValue());

        } else if (bookingType.equals("Virksomhed")) {
            booking.setOrganization(organisationCB.getValue());
            booking.getOrganization().setParticipants(Integer.parseInt(deltagereTF.getText()));
            booking.setActivity(aktivitetCB.getValue());
            booking.setCatering(forplejningCB.getValue());
            booking.setEquipmentList(udstyrLV.getItems());

            if (læringsRB.isSelected()) {
                booking.getOrganization().setAssistance("Læring konsulent");
            } else if (annesofieRB.isSelected()) {
                booking.getOrganization().setAssistance("Anne-Sofie Dideriksen");
            } else if (ingenRB.isSelected()) {
                booking.getOrganization().setAssistance("Ingen");
            }
        }

    }

    @FXML
    void handleTilføjFil() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        filLV.getItems().addAll(files);
    }

    @FXML
    void handleSletFil() {
        List<File> filesList = filLV.getSelectionModel().getSelectedValues();
        for (File file : filesList) {
            filLV.getItems().remove(file);
        }
    }

    @FXML
    void handleSave() {
        if (isInputValid()) {
            progressSpinner.setVisible(true);
            gemBtn.setDisable(true);
            alertLabel.setVisible(false);
            importToBooking();

            EditBookingTask editBookingTask = new EditBookingTask(booking);
            editBookingTask.setOnSucceeded(e -> {
                if (!editBookingTask.getValue()) {
                    System.out.println("Booking failed to edit");

                    // If the booking fails to save, the user is notified
                    alertLabel.setVisible(true);

                    // Clear the ListView and ComboBoxes
                    datoCB.clear();
                    fraCB.clear();
                    tilCB.clear();
                    datoCB.getSelectionModel().clearSelection();
                    fraCB.getSelectionModel().clearSelection();
                    tilCB.getSelectionModel().clearSelection();
                    startTimeList.clear();
                    endTimeList.clear();
                }
                progressSpinner.setVisible(false);
                gemBtn.setDisable(false);

                Alert alert = new Alert(Alert.AlertType.NONE, "Du har lige lavet en ændring i denne booking, du bedes derfor sende kunden en e-mail med opdateret information.", ButtonType.OK);
                alert.setTitle("E-mail påmindelse");
                alert.initOwner(gemBtn.getScene().getWindow()); //Retrieves the title bar icon from the main window by setting the alerts owner to that window.
                alert.showAndWait();
            });
            Thread thread = new Thread(editBookingTask);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    void handleSlet() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Er du sikker på at du vil slette denne booking?", ButtonType.NO, ButtonType.YES);
        alert.setTitle("Slet booking");
        alert.initOwner(gemBtn.getScene().getWindow()); //Retrieves the title bar icon from the main window by setting the alerts owner to that window.
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
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

                Stage stage = (Stage) sletBtn.getScene().getWindow();
                stage.close();
            });
            Thread thread = new Thread(deleteBookingTask);
            thread.setDaemon(true);
            thread.start();
        }
    }

    @FXML
    void handleSletEquip() {
        List<String> equipmentList = udstyrLV.getSelectionModel().getSelectedValues();
        for (String equipment : equipmentList) {
            udstyrLV.getItems().remove(equipment);
        }
    }

    @FXML
    void handleTilføjEquip() {
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

        if (bookingType.equals("Skole") && åbenSkoleCheckBox.isSelected()) {
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
        } else if (bookingType.equals("Virksomhed")) {
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

        return success;
    }

    private void loadBookingTypeOptions(String organisationName) {
        if (organisationName.equals("Tønder Gymnasium") || organisationName.equals("Det Blå Gymnasium") || organisationName.equals("EUC Syd") || organisationName.equals("Tønder Kommune - skole")) {
            bookingType = "Skole";
            skoleVBox.setVisible(true);
            virksomhedVBox.setVisible(false);

            skoleVBox.setMinHeight(USE_COMPUTED_SIZE);
            skoleVBox.setPrefHeight(USE_COMPUTED_SIZE);

            virksomhedVBox.setMinHeight(0);
            virksomhedVBox.setPrefHeight(0);
        } else {
            bookingType = "Virksomhed";
            skoleVBox.setVisible(false);
            virksomhedVBox.setVisible(true);

            virksomhedVBox.setMinHeight(USE_COMPUTED_SIZE);
            virksomhedVBox.setPrefHeight(USE_COMPUTED_SIZE);

            skoleVBox.setMinHeight(0);
            skoleVBox.setPrefHeight(0);
        }
    }
}