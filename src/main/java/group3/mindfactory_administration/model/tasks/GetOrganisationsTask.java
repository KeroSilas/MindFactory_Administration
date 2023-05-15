package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.OrganisationDao;
import group3.mindfactory_administration.dao.OrganisationDaoImpl;
import group3.mindfactory_administration.model.Organization;
import javafx.concurrent.Task;

import java.util.List;

public class GetOrganisationsTask extends Task<List<Organization>> {

    private final OrganisationDao organisationDao;

    public GetOrganisationsTask() {
        organisationDao = new OrganisationDaoImpl();
    }

    @Override
    public List<Organization> call() {
        return organisationDao.getOrganisations();
    }
}
