package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDaoImpl implements ActivityDao{

    private final DatabaseConnector databaseConnector;

    public ActivityDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Activity> getActivities() {
        List<Activity> activities = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Activity;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Activity activity = new Activity(
                        rs.getInt(1),
                        rs.getString(2)
                );
                activities.add(activity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }
}
