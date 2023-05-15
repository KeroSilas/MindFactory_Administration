package group3.mindfactory_administration.model;

public class Activity {

    private int activityID;
    private String activity;

    public Activity(int activityID, String activity) {
        this.activityID = activityID;
        this.activity = activity;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return activity;
    }
}
