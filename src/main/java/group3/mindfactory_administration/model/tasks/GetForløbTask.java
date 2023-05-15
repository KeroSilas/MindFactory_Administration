package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.ForløbDao;
import group3.mindfactory_administration.dao.ForløbDaoImpl;
import group3.mindfactory_administration.model.Forløb;
import javafx.concurrent.Task;

import java.util.List;

public class GetForløbTask extends Task<List<Forløb>> {

    private final ForløbDao forløbDao;

    public GetForløbTask() {
        forløbDao = new ForløbDaoImpl();
    }

    @Override
    public List<Forløb> call() {
        return forløbDao.getForløb();
    }
}
