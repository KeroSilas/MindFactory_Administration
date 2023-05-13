package group3.mindfactory_administration.controllers;

/*
 * This class controls the edit booking view.
 * This is where the administrator can edit and delete bookings.
 * The administrator can also see the booking details and add a note and files to the booking.
 */

/*public class EditBookingController {
    private Booking booking;

    void handleEdit() {

    }

    void handleSlet() {

        // Sets the statusLabel to empty, shows the progressSpinner and disables the afbrydBtn
        statusLabel.setText("");
        progressSpinner.setVisible(true);
        sletBtn.setDisable(true);
        sletBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        // Starts a new thread to delete the booking
        // If the booking is deleted, the statusLabel will be set to "Booking slettet" and the text color will be green
        // If the booking is not found, the statusLabel will be set to "Booking ikke fundet" and the text color will be red
        // Once the thread is done, the progressSpinner will be hidden and the afbrydBtn will be enabled again
        booking.getBookingID();
        if (deleteBookingTask.getValue()) {
            statusLabel.setText("Booking slettet");
            statusLabel.setStyle("-fx-text-fill: green");
            bookingNummerTF.clear();
        } else {
            statusLabel.setText("Booking ikke fundet");
            statusLabel.setStyle("-fx-text-fill: red");
        }
        progressSpinner.setVisible(false);
        sletBtn.setDisable(false);
        sletBtn.setStyle("-fx-background-color: #BD2323FF; -fx-text-fill: #ffffff;");


    ;
    Thread thread = new Thread(deleteBookingTask);
    thread.setDaemon(true);
    thread.start();
}


    private void setBooking(Booking booking) {
    this.booking = booking;
    }
}*/