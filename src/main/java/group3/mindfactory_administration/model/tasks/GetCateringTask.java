package group3.mindfactory_administration.model.tasks;

import group3.mindfactory_administration.dao.CateringDao;
import group3.mindfactory_administration.dao.CateringDaoImpl;
import group3.mindfactory_administration.model.Catering;
import javafx.concurrent.Task;

import java.util.List;

public class GetCateringTask extends Task<List<Catering>> {

    private final CateringDao cateringDao;

    public GetCateringTask() {
        cateringDao = new CateringDaoImpl();
    }

    @Override
    public List<Catering> call() {
        return cateringDao.getCatering();
    }
}
