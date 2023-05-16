package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.dao.singleton.DatabaseConnector;
import group3.mindfactory_administration.model.Catering;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CateringDaoImpl implements CateringDao {

    private final DatabaseConnector databaseConnector;

    public CateringDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Catering> getCatering() {
        List<Catering> caterings = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Catering;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Catering catering = new Catering(
                        rs.getInt(1),
                        rs.getString(2)
                );
                caterings.add(catering);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return caterings;
    }
}
