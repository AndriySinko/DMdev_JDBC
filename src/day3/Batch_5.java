package day3;

import day1.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Batch_5 {
    /*
    Если у нас есть какой-то сервер(допустим в Америке) и мы к нему обращаемся постоянно,
    то выполнение этих запросов действует следующим образом. На сервер посылается запрос,
    выполняется и возвращается результат. И так с каждым запросом.

    Хоть скорость выполнения и быстра, но если большие расстояния, то и на отправки запросов
    тоже тратится время. Поэтому мы через нашу транзакцию которой мы сами управляем
    делаем это сразу(сначала все посылаем, потом получаем)

    Это используется в работе с обычным Statement.

    Обычно это часто ипользуется с DDL и INSERT, DELETE, UPDATE
     */
    public static void main(String[] args) throws SQLException {
        var addNewTables = """
                CREATE TABLE cars.engine (
                id SERIAL PRIMARY KEY,
                horse_power INT NOT NULL,
                torque INT NOT NULL,
                fuel_consumption NUMERIC NOT NULL,
                engine_type VARCHAR(64) NOT NULL CHECK (engine_type = 'Дизель' OR engine_type = 'Бензин')
                );
                
                CREATE TABLE cars.transmission (
                id SERIAL PRIMARY KEY,
                transmission_type VARCHAR(32) NOT NULL 
                                CHECK (transmission_type = 'Механическая' 
                                OR transmission_type = 'Автоматическая'
                                OR transmission_type = 'Роботизированная'
                                OR transmission_type = 'Вариативная'),
                drive_unit VARCHAR(32) NOT NULL
                            CHECK ( drive_unit = 'Передний' 
                            OR drive_unit = 'Задний'
                            OR drive_unit = 'Полный' ),
                steps INT NOT NULL 
                );
                """;
        var alterModelsTable = """
                ALTER TABLE IF EXISTS cars.models
                ADD COLUMN engine_id INT REFERENCES cars.engine;

                ALTER TABLE IF EXISTS cars.models
                ADD COLUMN transmission_id INT REFERENCES cars.transmission;
                """;
        var insertIntoToNewTables1 = """
                INSERT INTO cars.engine (horse_power,torque,fuel_consumption,engine_type)
                VALUES (600,800,12.1,'Бензин'),
                        (320,550,7.9,'Дизель'),
                        (625,750,10.5,'Бензин');
                """;
        var insertIntoToNewTables2 = """
                INSERT INTO cars.transmission (transmission_type, drive_unit, steps)
                VALUES ('Автоматическая','Полный',8),
                        ('Роботизированная','Передний',6),
                        ('Механическая','Полный',7);
                """;

        var updateModels1 = """
                UPDATE cars.models
                SET engine_id = 1
                WHERE company_id = 1;
                """;
        var updateModels2 = """
                UPDATE cars.models
                SET engine_id = 3
                WHERE company_id = 2;
                """;
        var updateModels3 = """
                UPDATE cars.models
                SET engine_id = 2
                WHERE company_id = 3;
                """;
        var updateModels4 = """
                UPDATE cars.models
                SET transmission_id = 2
                WHERE company_id = 1;
                """;
        var updateModels5 = """
                UPDATE cars.models
                SET transmission_id = 1
                WHERE company_id = 2;
                """;
        var updateModels6 = """
                UPDATE cars.models
                SET transmission_id = 3
                WHERE company_id = 3;
                """;

        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.openConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

//            statement.execute(addNewTables); Это сделано отдельно

            statement = connection.createStatement();
//            statement.addBatch(insertIntoToNewTables1);
//            statement.addBatch(insertIntoToNewTables2);
////            statement.addBatch(alterModelsTable); Иксепшион(слишком много возвразенных обновлений)

//            statement.execute(alterModelsTable); Сделано отдельно из-за иксепшина

            statement.addBatch(updateModels1);
            statement.addBatch(updateModels2);
            statement.addBatch(updateModels3);
            statement.addBatch(updateModels4);
            statement.addBatch(updateModels5);
            statement.addBatch(updateModels6);

            var ints = statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            if(connection != null){
                connection.rollback();
            }
            throw new RuntimeException(e);
        }finally {
            if(connection != null){
                connection.close();
            }
            if(statement != null){
                statement.close();
            }
        }
    }
}
