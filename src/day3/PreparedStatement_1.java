package day3;

import day1.util.ConnectionManager;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatement_1 {
    /*
    PreparedStatement - интерфейс который наследуется от интерфейса Statement, но отличие в том что он при своем
    создании принимает sql, и не принимет в execute/executeQuery/executeUpdate.

    Если нам нужно чтобы пользователь внешне задавал какие-то запросы, то мы можем обезопасится с помощью указания
    ? внесто форматирования с помощью %s, и потом с помощью огромного числа сетеров который присутствуют в нем
    задавать уже параметры, которые будту принимать тот тип который мы хотим установить.
    !!! ЭТО ОБЯЗАТЛЬНО ДЕЛАЕТСЯ ПЕРЕД ВЫЗОВОМ МЕТОДОВ execute.
     */
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(1900,1,1);
        LocalDate end = LocalDate.of(2000,1,1);

        var result = getCompaniesDates(start,end);
        int i = 1;
        for (String s : result) {
            System.out.println(i+": "+s);
            i++;
        }
    }
    private static List<String> getCompaniesDates(LocalDate start, LocalDate end){
        String sql = """
        SELECT company_name || ' ' || company_date
        FROM cars.company
        WHERE company_date BETWEEN ? AND ?
""";
        List<String> result = new ArrayList<>();
        try (var connection = ConnectionManager.openConnection();
             var prepareStatement = connection.prepareStatement(sql)) {
            System.out.println(prepareStatement+"\n===================================\n");
            prepareStatement.setDate(1, Date.valueOf(start));
            System.out.println(prepareStatement+"\n===================================\n");
            prepareStatement.setDate(2, Date.valueOf(end));
            System.out.println(prepareStatement+"\n===================================\n");
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()){
                result.add(resultSet.getObject(1, String.class));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
