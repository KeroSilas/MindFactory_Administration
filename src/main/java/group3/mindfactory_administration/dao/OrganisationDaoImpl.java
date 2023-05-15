package group3.mindfactory_administration.dao;

import group3.mindfactory_administration.model.Organization;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrganisationDaoImpl implements OrganisationDao{

    private final DatabaseConnector databaseConnector;

    public OrganisationDaoImpl() {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public List<Organization> getOrganisations() {
        List<Organization> organizations = new ArrayList<>();
        try (Connection con = databaseConnector.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Organisation;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Organization organization = new Organization(
                        rs.getInt(1),
                        rs.getString(2)
                );
                organizations.add(organization);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return organizations;
    }
}
