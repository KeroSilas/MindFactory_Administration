package group3.mindfactory_administration.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private boolean isEmailSent;
    private String messageToAS;
    private String personalNote;
    private String bookingType;
    private List<String> equipmentList;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isWholeDay;
    private boolean isHalfDayEarly;
    private boolean isNoShow;

    private static Booking instance = null;

    private Booking() {
        clearBooking();
    }

    public static Booking getInstance() {
        if (instance == null) {
            instance = new Booking();
        }
        return instance;
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

    public boolean isEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(boolean isEmailSent) {
        this.isEmailSent = isEmailSent;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isWholeDay() {
        if (startTime.isBefore(LocalTime.of(12, 0)) && endTime.isAfter(LocalTime.of(12, 0))) {
            isWholeDay = true;
        }
        return isWholeDay;
    }

    public boolean isHalfDayEarly() {
        if (endTime.isBefore(LocalTime.of(12, 0))) {
            isHalfDayEarly = true;
        }
        return isHalfDayEarly;
    }

    public boolean isNoShow() {
        return isNoShow;
    }

    public void clearBooking() {
        this.catering = "Ingen";
        this.activity = "Ingen";
        this.organization = "";
        this.åbenSkoleForløb = "Ingen";
        this.firstName = "";
        this.lastName = "";
        this.position = "";
        this.department = "";
        this.phone = "";
        this.email = "";
        this.assistance = "Ingen";
        this.transportType = "Ikke valgt";
        this.transportArrival = "Ikke valgt";
        this.transportDeparture = "Ikke valgt";
        this.participants = 0;
        this.bookingDateTime = null;
        this.startDate = null;
        this.startTime = null;
        this.endTime = null;
        this.isWholeDay = false;
        this.isHalfDayEarly = false;
        this.isNoShow = false;
        this.isEmailSent = false;
        this.messageToAS = "";
        this.personalNote = "";
        this.bookingType = null;
        this.equipmentList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return startDate + " " + startTime + " - " + endTime;
    }

}