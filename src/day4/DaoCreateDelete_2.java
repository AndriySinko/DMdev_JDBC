package day4;

import dao.ModelsDao;
import entity.Models;

public class DaoCreateDelete_2 {
    /**
     * С Dao обычно используется принцип CRUD - (CREATE, READ, UPDATE, DELETE - где CREATE это
     * INSERT, а READ это SELECT);
     *
     * Осуществляется это с помощью инициализации определенных методов в dao
     */
    public static void main(String[] args) {
        var modelDao = ModelsDao.getInstance();
        var model = new Models();
        model.setCompanyId(5);
        model.setModelName("Model X");
        model.setModelYears(2021);
        model.setEngineId(3);
        model.setTransmissionId(2);

//        var result = modelDao.save(model);
//        System.out.println(result);

        var resultDelete = modelDao.delete(8);
        System.out.println(resultDelete);

    }
}
