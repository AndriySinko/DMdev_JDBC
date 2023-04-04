package day2;

import day1.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLInjection_5 {
    /**
     * SQL Injection - это то случай когда с помощью уязвимости программы можно сделать очень много проблем,
     * удалить базу данных, получить не те данные и т.д
     *
     * Это обычной случается в том случае где мы даем пользователю доступ до обычного получения через
     * форматирование через стейтмент, который ничего не проверяет, и приводит к уязвимости программы
     */
    public static void main(String[] args) {
        String company_id = "2";
        var result = getCarsById(company_id);
        System.out.println(result);

        /*
    На первый взгляд все нормально, но нет, в строке company_id, пользователь может продолжать делать какие-то действия
    и тем самым наделать много проблем
 */
        String company_id1 = "2 OR 1 = 1 ";
        var result1 = getCarsById(company_id1);
        System.out.println(result1);

        /*
        Тут мы уже получили все данные, которые мы не должны были получить
         */

        String company_id2 = "2; INSERT INTO cars.models (company_id, model_name, model_years) " +
                             "VALUES (3,'M5 Competition', 2020)";
        var result2 = getCarsById(company_id2);
        System.out.println(result);

        /*
        Хоть тут и выбросилась ошибка, но запрос на SQL сработал, и так каждый кто знает язык SQL
        может наделать больших проблем, но это все решается с помощью PreparedStatement;
 */
    }
    private static List<Integer> getCarsById(String company_id){
        String sql = """
                SELECT id
                FROM cars.models
                WHERE company_id = %s
                """.formatted(company_id);
        List<Integer> result = new ArrayList<>();
        try (var connection = ConnectionManager.openConnection();
            var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
//                result.add(resultSet.getInt("id"));  Этим мы рискуем что в таблице может быть элемент null,
//                а в лист нельзя добавлять примитивные типы null

                result.add(resultSet.getObject("id",Integer.class)); //Null safe
                // Этим мы убереглись что сложный тип переделывает полученное нами значение в то, что мы
                // указали после запятой
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
