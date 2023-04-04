package day2;

import day1.util.ConnectionManager;

import java.sql.SQLException;

public class ResultSet_SELECT_3 {
    /*
    ResultSet - интерфейс который реализует функция PgResultSet в бд. Он похож на итератор(курсор рбд).
    Наичастейше используется для чтение SELECT, хотя и может обновлять/удалять/добавлять, но это в очень редко

    У него есть метод next который перемаещт курсор между колонками. Он является типом boolean, и когда курсор стоит
    перед колонкой, он поверяет есть ли слудеящая, если да то true, и двигается дальше, только тогда когда он
    перешел к началу другой колонки мы можем воздествовать с данными той колонки которую сейчас считали

    ResultSet по дефолту Read-only только на чтение, и Forward-Only. Что означает что он только на чтение, и его
    курсор двигается нормально от самой верхней(1) колонки по послденюю по очереди.
    Но мы можем изменить ResultSetType и ResultSetConcurrent так что вставим вместо Read-only UPDATABLE и сможем
    обноваять таблицу, а вместо Forward-Only SENSITIVE/INSENSITIVE и сожем прыгать по таюлице получая разные значения

    ResultSet является AutoClosable но его необязательно закрывать так, как что у него есть условие что если
    его Statement закроется, то он тоже закроется.

    Чтобы получить данные типа ResultSet, мы должны на нашем Statement использовать метод executeQuery
     */
    public static void main(String[] args) {

        String sql = """
    SELECT *
    FROM cars.company
""";
        try (var connection = ConnectionManager.openConnection();
            var statement = connection.createStatement()) {

            var executeResult = statement.executeQuery(sql);
            while (executeResult.next()){
                System.out.println(executeResult.getInt("id"));
                System.out.println(executeResult.getString("company_name"));
                System.out.println(executeResult.getDate("company_date"));
                System.out.println(executeResult.getString("company_capitalization"));
                System.out.println("================================");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
