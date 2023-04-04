package day2;

import day1.util.ConnectionManager;

import java.sql.SQLException;

public class StatementDML_2 {
    /**
     * Для DML комманд мы используем executeUpdate, впринципе ничем не отличается,
     * но возвращает int(обновленное, добавленное, удаленное) кол-во строк
     */

    public static void main(String[] args) {
        String sql = """
        --INSERT INTO cars.company (company_name, company_date, company_capitalization)
        --VALUES ('AG Audi','16-8-1909','€29.7 млрд (2018)'),
                --('Mercedes-Benz','01-10-1883','€26,289 млрд (2018 год)'),
                --('BMW','16-8-1909',' €57,83 млрд (2018)');
                
        --UPDATE cars.company
        --SET company_capitalization = '€48,9 млрд (2022)'
        --WHERE company_name ILIKE 'B%';
        
        --DELETE FROM cars.company 
        --WHERE company_date > '01-01-2000';
        """;


        try (var connection = ConnectionManager.openConnection();
            var statement = connection.createStatement()) {

            var resultExecute = statement.executeUpdate(sql);
            System.out.println(resultExecute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
