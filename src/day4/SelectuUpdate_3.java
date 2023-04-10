package day4;

import dao.ModelsDao;
import entity.Models;

public class SelectuUpdate_3 {
    /**
     * Select всегда должен возвращать оптионал, потому что резуотат может быть null
     */
    public static void main(String[] args) {
        var modelDao = ModelsDao.getInstance();
        var result = modelDao.findById(9);
        System.out.println(result);

        result.ifPresent(models ->
                {
                    models.setModelName("Passat B5");
                    models.setModelYears(2020);
                    models.setCompanyId(4);

                    modelDao.update(models);
                }
        );
        var result1 = modelDao.findAll();
        for (Models results : result1) {
            System.out.println(results);
        }
    }
}
