package group3.mindfactory_administration.model;


import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private int bookingID;
    private String catering;
    private String activity;
    private String organization;
    private String åbenSkoleForløb;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private String phone;
    private String email;
    private String assistance;
    private String transportType;
    private String transportArrival;
    private String transportDeparture;
    private int participants;
    private LocalDateTime bookingDateTime;
    private String messageToAS;
    private String personalNote;
    private String bookingType;
    private List<String> equipmentList;
    private List<File> bookingFileList;

    public Booking(int bookingID, String catering, String activity, String organization, String åbenSkoleForløb, String firstName, String lastName, String position, String department, String phone, String email, String assistance, String transportType, String transportArrival, String transportDeparture, int participants, LocalDateTime bookingDateTime, String messageToAS, String personalNote, String bookingType) {
        this.bookingID = bookingID;
        this.catering = catering;
        this.activity = activity;
        this.organization = organization;
        this.åbenSkoleForløb = åbenSkoleForløb;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.department = department;
        this.phone = phone;
        this.email = email;
        this.assistance = assistance;
        this.transportType = transportType;
        this.transportArrival = transportArrival;
        this.transportDeparture = transportDeparture;
        this.participants = participants;
        this.bookingDateTime = bookingDateTime;
        this.messageToAS = messageToAS;
        this.personalNote = personalNote;
        this.bookingType = bookingType;
        this.equipmentList = new ArrayList<>();
        this.bookingFileList = new ArrayList<>();
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getCatering() {
        return catering;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getÅbenSkoleForløb() {
        return åbenSkoleForløb;
    }

    public void setÅbenSkoleForløb(String åbenSkoleForløb) {
        this.åbenSkoleForløb = åbenSkoleForløb;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getTransportArrival() {
        return transportArrival;
    }

    public void setTransportArrival(String transportArrival) {
        this.transportArrival = transportArrival;
    }

    public String getTransportDeparture() {
        return transportDeparture;
    }

    public void setTransportDeparture(String transportDeparture) {
        this.transportDeparture = transportDeparture;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getAfdeling() {
        return department;
    }

    public void setAfdeling(String afdeling) {
        this.department = afdeling;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }

    public String getMessageToAS() {
        return messageToAS;
    }

    public void setMessageToAS(String messageToAS) {
        this.messageToAS = messageToAS;
    }

    public String getPersonalNote() {
        return personalNote;
    }

    public void setPersonalNote(String personalNote) {
        this.personalNote = personalNote;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public List<String> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<String> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<File> getBookingFilesList() {return bookingFileList; }

    public void setBookingFilesList(List<File> bookingFileList) {
        this.bookingFileList = bookingFileList;
    }



}
