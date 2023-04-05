package day3;

import day1.util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;

public class Blob_Clob_6 {
    /*
    Blob - бинарный большой обьект - представление в байтах, в него засунуть можно все что угодно(фото видео)
    Clob - символьный большой обьект - представление символьных данных/текстов/

    В postgres нету таких сложных типов данных, но есть их аналоги
    Blob = Bytea
    Clob = Text
     */
    public static void main(String[] args) throws SQLException, IOException {
        //setImage();
        getImage();
    }
    private static void setImage() throws SQLException, IOException {
        var sql = """
                UPDATE cars.models
                SET image = ?
                WHERE id = 2;
                
                UPDATE cars.models
                SET image = ?
                WHERE id = 5;
                """;

        try (var connection = ConnectionManager.openConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBytes(1,
                    Files.readAllBytes(Path.of("resources","inputphoto","audirsq8.png")));
            preparedStatement.setBytes(2,
                    Files.readAllBytes(Path.of("resources","inputphoto","bmw-x5-m-competition-si.png")));
            preparedStatement.executeUpdate();
        }
    }

    private static void getImage() throws SQLException, IOException {
        var sql = """
                SELECT image
                FROM cars.models
                WHERE id = ?;
                """;

        try (var connection = ConnectionManager.openConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 5);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                var image = resultSet.getBytes("image");
                Files.write(Path.of("resources","outputphoto","car1.png"), image, StandardOpenOption.CREATE);
            }
        }
    }
}
