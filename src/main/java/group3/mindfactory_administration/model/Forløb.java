package group3.mindfactory_administration.model;

public class Forløb {

    private int forløbID;
    private String åbenSkoleForløb;
    private String transportType;
    private String transportArrival;
    private String transportDeparture;

    public Forløb(int forløbID, String åbenSkoleForløb) {
        this.forløbID = forløbID;
        this.åbenSkoleForløb = åbenSkoleForløb;
    }

    public Forløb(int forløbID, String åbenSkoleForløb, String transportType, String transportArrival, String transportDeparture) {
        this.forløbID = forløbID;
        this.åbenSkoleForløb = åbenSkoleForløb;
        this.transportType = transportType;
        this.transportArrival = transportArrival;
        this.transportDeparture = transportDeparture;
    }

    public int getForløbID() {
        return forløbID;
    }

    public void setForløbID(int forløbID) {
        this.forløbID = forløbID;
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

    @Override
    public String toString() {
        return åbenSkoleForløb;
    }
}
