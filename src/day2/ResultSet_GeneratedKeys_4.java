package day2;

import day1.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;

public class ResultSet_GeneratedKeys_4 {
    /**
     * Чтобы получить автогенирируемый ключ не прибегая к Select, мы можем это сдлать с помощью
     * executeUpdate, и потом метода getGeneratedKeys, но в параметры кроме как самой базыЮ нужно
     * задать и тип возвращаемое или нет(по дефолту стоит нет)
     */
    public static void main(String[] args) {
        String sql = """
        --INSERT INTO cars.models (company_id, model_name, model_years)
        --VALUES (1, 'Q5',2012),
           
        --INSERT INTO cars.models (company_id, model_name, model_years)
        --VALUES  (1, 'RS Q8',2022);
        
        --INSERT INTO cars.models (company_id, model_name, model_years)
        --VALUES  (2, 'GLE', 2018);
        
        --INSERT INTO cars.models (company_id, model_name, model_years)
        --VALUES  (2, 'AMG GT',2020);
        
        --INSERT INTO cars.models (company_id, model_name, model_years)
        --VALUES  (3, 'X5 Competition',2021);
        
        INSERT INTO cars.models (company_id, model_name, model_years)
        VALUES  (3, 'M3', 2019);
""";
        try (var connection = ConnectionManager.openConnection();
            var statement = connection.createStatement()) {

            var executeResult = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                var generatedID = generatedKeys.getInt("id");
                System.out.println(generatedID);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
