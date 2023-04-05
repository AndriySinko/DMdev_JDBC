package day3;

import day1.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transactions_4 {
    /*
    Мы уже знаем что при любом действии(запросе или команде) вызывается транзакция которая
    это действие выполняет. Но что если нам нужно управлять транзакциями вручную, так как автоматическое
    выполнение может произвести некорректное выполнения запроса.

    Например нам нужно удалить данные из таблицы, на которую ссылается через внешний ключ
    другие данные из таблицы. При попытке удаления выбросится ошибка, так как есть файлы которые
    зависят от удаляемых файлов. Поэтому нужно сделать удаление сначала тех файлов которые
    ссылаются на те данные, которые мы хотим удалить. Но в этом случаем может произойти такое
    что мы сначала удалим первые данные, а вторые останутся, хотя мы хотели удалить именно вторые
     */
    public static void main(String[] args) throws SQLException {
        var deletedId = 6;
        var deleteFromTest1 = "DELETE FROM test1 WHERE id = ?";
        var deleteFromTest2 = "DELETE FROM test2 WHERE id = ?";
//        try (var connection = ConnectionManager.openConnection();
//             var deletePreparedStatement1 = connection.prepareStatement(deleteFromTest1);
//             var deletePreparedStatement2 = connection.prepareStatement(deleteFromTest2)) {
//
//            deletePreparedStatement1.setInt(1,deletedId);
////            deletePreparedStatement.executeUpdate(); - выбросилась ошибка
//
//            deletePreparedStatement2.setInt(1, deletedId);
//
///*
//            deletePreparedStatement2.executeUpdate();
//              if(true){
//              throw new RunTimeException("Oooops")
//              }
//            deletePreparedStatement1.executeUpdate();
//
//            Тут данные из 2 таблицы удалились а с 1 нет
//*/
//        } catch (SQLException e) {
//            throw new RunTimeException(e);
//        }

        Connection connection = null;
        PreparedStatement deletePreparedStatement1 = null;
        PreparedStatement deletePreparedStatement2 = null;
        try {
            connection = ConnectionManager.openConnection();
            deletePreparedStatement1 = connection.prepareStatement(deleteFromTest1);
            deletePreparedStatement2 = connection.prepareStatement(deleteFromTest2);

            connection.setAutoCommit(false);
            deletePreparedStatement1.setInt(1, deletedId);
            deletePreparedStatement2.setInt(1, deletedId);

            deletePreparedStatement2.executeUpdate();
            if (true) {
                throw new RuntimeException("Ooops");
            }
            deletePreparedStatement1.executeUpdate();
            connection.commit();
        }catch (Exception e){
            if(connection != null){
                connection.rollback();
            }
            throw e;
        } finally {
            if(connection != null){
                connection.close();
            }
            if(deletePreparedStatement1 != null){
                deletePreparedStatement1.close();
            }
            if(deletePreparedStatement2 != null){
                deletePreparedStatement2.close();

            }
        }
    }
}
