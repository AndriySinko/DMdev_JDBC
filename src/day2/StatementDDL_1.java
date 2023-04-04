package day2;

import day1.util.ConnectionManager;

import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
    При создании нашего Statement у него есть методы которые принимаю строки (все команды sql представлены как строки):
    -execute() - тип boolean который возвращает true если мы используем какую-то select операцию, в остальных false,
        но общепринято что с помощью него мы используем DDL команды (CREATE, DROP, ALTER)

        Statement тоэе имплементирует Autoclosable и его тже обязятельно нужно закрывать. Все стейтменты нужно закрывать
        чтобы не было утечки памяти
     */
public class StatementDDL_1 {
    public static void main(String[] args) {

        String sql = """
              --DROP DATABASE flight_repository;
              
              --CREATE SCHEMA cars;
              --CREATE TABLE cars.company (
              --id SERIAL PRIMARY KEY,
              --company_name VARCHAR(128) NOT NULL UNIQUE,
              --company_date DATE NOT NULL,
              --company_capitalization VARCHAR(128)
              --);
              
              --CREATE TABLE cars.models (
              --id SERIAL PRIMARY KEY,
              --company_id INT REFERENCES cars.company ON DELETE CASCADE ON UPDATE CASCADE,
              --model_name VARCHAR(64) NOT NULL,
              --model_years INT NOT NULL,
              --UNIQUE(model_name, model_years)
              --);
                """;

        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {

            var executeResult = statement.execute(sql);
            System.out.println(executeResult);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
