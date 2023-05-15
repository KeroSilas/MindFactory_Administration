package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Forløb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ForløbDaoImpl implements ForløbDao{

    private final DatabaseConnector databaseConnector;

    public ForløbDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Forløb> getForløb() {
        List<Forløb> forløbList = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Forløb;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Forløb forløb = new Forløb(
                        rs.getInt(1),
                        rs.getString(2)
                );
                forløbList.add(forløb);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return forløbList;
    }
}
