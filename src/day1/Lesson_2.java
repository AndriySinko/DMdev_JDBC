package day1;

import day1.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Lesson_2 {
    /*
    Чтобы подключится к базе данных, мы используем DriverManager, который возвращает тип Connection который
    имплементирует AutoClosable, следственно его нужно закрывать.

    DriverManager с помощью метода getConnection должен принимать название, пароль и урл базы данных

    у него есть методы
    .close() - закрывает, чтобы это не делать мы можем засунуть наше подключение в try-with-resources блок
    .commit - коммит наши данные в базу
    .rolback - возвращает действие назад

    Еще он имеет методы типа Statement и PreparedStatement
     .createStatement() - используется для DDL команд - создание удаление и т.д
     .prepareStatement() - используется для более сложны задач(запросы и т.д)

     На практике используется чаще .prepareStatement(), так как он наследует еще и Statement, и имеет его методы
     */
    public static void main(String[] args) throws SQLException {
//        Class<Driver> driver = Driver.class;
//        String name = "********";
//        String password = "********";
//        String url = "*****************************************";
//        try (var connection = DriverManager.getConnection(url, name, password)) {
//            System.out.println(connection.getTransactionIsolation());
//        }

    /*
    При создании подключения, нам постоянно нужно указывать url, user, password, чтобы этого не делать, мы можем
    создать утилитный класс с нашим подключение, там где url, user, password, будует константы, и будет только
    метод плодключения
     */
        try (var connection = ConnectionManager.openConnection()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
