package group3.mindfactory_administration.model;

public class Catering {

    private int cateringID;
    private String catering;

    public Catering(int cateringID, String catering) {
        this.cateringID = cateringID;
        this.catering = catering;
    }

    public int getCateringID() {
        return cateringID;
    }

    public void setCateringID(int cateringID) {
        this.cateringID = cateringID;
    }

    public String getCatering() {
        return catering;
    }

    public void setCatering(String catering) {
        this.catering = catering;
    }

    @Override
    public String toString() {
        return catering;
    }
}
