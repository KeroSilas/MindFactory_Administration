package group3.mindfactory_administration.model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
 * This class is used to create a booking object.
 * The booking object is used to store all the information about a booking.
 */

public class Booking implements Comparable<Booking> {

    private int bookingID;
    private Customer customer;
    private Catering catering;
    private Activity activity;
    private Organization organization;
    private Forløb åbenSkoleForløb;
    private LocalDateTime bookingDateTime;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isWholeDay;
    private boolean isNoShow;
    private boolean isEmailSent;
    private String messageToAS;
    private String personalNote;
    private List<String> equipmentList;
    private List<File> fileList;

    private String bookingType;

    public Booking(int bookingID, Customer customer, Catering catering, Activity activity, Organization organization, Forløb åbenSkoleForløb, LocalDateTime bookingDateTime, LocalDate startDate, LocalTime startTime, LocalTime endTime, boolean isWholeDay, boolean isNoShow, String messageToAS, String personalNote, List<String> equipmentList, List<File> fileList) {
        this.bookingID = bookingID;
        this.customer = customer;
        this.catering = catering;
        this.activity = activity;
        this.organization = organization;
        this.åbenSkoleForløb = åbenSkoleForløb;
        this.bookingDateTime = bookingDateTime;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isWholeDay = isWholeDay;
        this.isNoShow = isNoShow;
        this.messageToAS = messageToAS;
        this.personalNote = personalNote;
        this.equipmentList = equipmentList;
        this.fileList = fileList;
        equipmentList = new ArrayList<>();
        fileList = new ArrayList<>();
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
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

    public boolean isNoShow() {
        return isNoShow;
    }

    public void setNoShow(boolean isNoShow) {
        this.isNoShow = isNoShow;
    }

    public Catering getCatering() {
        return catering;
    }

    public void setCatering(Catering catering) {
        this.catering = catering;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Forløb getÅbenSkoleForløb() {
        return åbenSkoleForløb;
    }

    public void setÅbenSkoleForløb(Forløb åbenSkoleForløb) {
        this.åbenSkoleForløb = åbenSkoleForløb;
    }

    public Customer getCustomer() {
        if (customer == null) {
            customer = new Customer();
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getBookingType() {
        String organizationName = organization.getOrganization();
        if (organizationName.equals("Tønder Gymnasium") || organizationName.equals("Det Blå Gymnasium") || organizationName.equals("EUC Syd") || organizationName.equals("Tønder Kommune - skole")) {
            bookingType = "Skole";
        } else {
            bookingType = "Virksomhed";
        }
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPersonalNote() {
        return personalNote;
    }

    public void setPersonalNote (String personalNote) {
        this.personalNote = personalNote;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<File> getFileList() {
        return fileList;
    }

    @Override
    public String toString() {
        return startDate + " " + startTime + " - " + endTime;
    }

    @Override
    public int compareTo(Booking o) {
        return this.getStartTime().compareTo(o.getStartTime());
    }

}