package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Booking;

import java.security.Timestamp;
import java.sql.SQLException;

public interface BookingDao {

    void editBooking (Booking booking, String Catering, String Activity, String Organization, String ÅbenSkoleForløb, String FirstName, String LastName, String Position, String Afdeling, String Phone, String Email, String Assistance, String TransportType, String TransportArrival, String TransportDeparture, int Participants, Timestamp BookingDateTime, String MessageToAS, String PersonalNote, String BookingType) throws SQLException;

    void deleteBooking (int bookingID);

}
