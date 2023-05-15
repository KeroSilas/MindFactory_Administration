package group3.mindfactory_administration.model;

public class Organization {

    private int organizationID;
    private String organization;
    private int participants;
    private String assistance;

    public Organization(int organizationID, String organization) {
        this.organizationID = organizationID;
        this.organization = organization;
    }

    public Organization(int organizationID, String organization, int participants, String assistance) {
        this.organizationID = organizationID;
        this.organization = organization;
        this.participants = participants;
        this.assistance = assistance;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getAssistance() {
        return assistance;
    }

    public void setAssistance(String assistance) {
        this.assistance = assistance;
    }

    @Override
    public String toString() {
        return organization;
    }
}
