package day4;

import dao.ModelsDao;
import dto.ModelsFilter;
import entity.Models;

public class BatchSelectWihFiltration_4 {
    public static void main(String[] args) {
        var filter = new ModelsFilter(3,0,"RS Q8",1);
        var modelsDao = ModelsDao.getInstance();
        var results = modelsDao.findAll(filter);
        System.out.println(results);
    }
}
