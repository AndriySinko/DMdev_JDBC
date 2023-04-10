package dao;

import day4.util.ConnectionPool;
import dto.ModelsFilter;
import entity.Models;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelsDao {
    private static final ModelsDao INSTANCE = new ModelsDao();
    private ModelsDao(){
    }
    private static final String DELETE_SQL = """
            DELETE FROM cars.models
            WHERE id = ?;
            """;

    private static final String CREATE_SQL = """
            INSERT INTO cars.models (company_id, model_name, model_years, engine_id, transmission_id)
            VALUES (?,?,?,?,?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE cars.models
            SET company_id = ?,
                model_name = ?,
                model_years = ?,
                engine_id = ?,
                transmission_id = ?
                WHERE id = ?;
            """;
    private static final String FIND_BY_ID = """
            SELECT  id,
                    company_id,
                    model_name,
                    model_years,
                    engine_id,
                    transmission_id
            FROM cars.models
            WHERE id = ?;
            """;

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM cars.models
            """;

    public List<Models> findAll(){
        try (var connection = ConnectionPool.get();
            var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<Models> models = new ArrayList<>();
            while (resultSet.next()){
                models.add(new Models(
                        resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        resultSet.getString("model_name"),
                        resultSet.getInt("model_years"),
                        resultSet.getInt("engine_id"),
                        resultSet.getInt("transmission_id")
                ));
            }
            return models;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Models> findAll(ModelsFilter filter){
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.modelName() != null){
            whereSql.add("model_name = ?");
            parameters.add(filter.modelName());
        }

        if (filter.companyId() != 0){
            whereSql.add("company_id = ?");
            parameters.add(filter.companyId());
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream()
                .collect(Collectors.joining(" AND ", " WHERE ", " LIMIT ? OFFSET ? "));
        var SQL = FIND_ALL_SQL + where;
        try (var connection = ConnectionPool.get();
        var preparedStatement = connection.prepareStatement(SQL)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i+1, parameters.get(i));
            }
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            List<Models> models = new ArrayList<>();
            while (resultSet.next()){
                models.add(new Models(
                        resultSet.getInt("id"),
                        resultSet.getInt("company_id"),
                        resultSet.getString("model_name"),
                        resultSet.getInt("model_years"),
                        resultSet.getInt("engine_id"),
                        resultSet.getInt("transmission_id")
                ));
            }
            return models;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Models> findById (Integer id){
        try (var connection = ConnectionPool.get();
        var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);

            var resultSet = preparedStatement.executeQuery();
            Models models = null;
                if(resultSet.next()){
                    models = new Models(
                            resultSet.getInt("id"),
                            resultSet.getInt("company_id"),
                            resultSet.getString("model_name"),
                            resultSet.getInt("model_years"),
                            resultSet.getInt("engine_id"),
                            resultSet.getInt("transmission_id")
                    );
                }
            return Optional.ofNullable(models);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Models model){
        try(var connection = ConnectionPool.get();
        var preparedStatement = connection.prepareStatement(UPDATE_SQL)){

            preparedStatement.setInt(1, model.getCompanyId());
            preparedStatement.setString(2, model.getModelName());
            preparedStatement.setInt(3, model.getModelYears());
            preparedStatement.setInt(4, model.getEngineId());
            preparedStatement.setInt(5, model.getTransmissionId());
            preparedStatement.setInt(6,model.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Models save(Models m){
        try (var connection = ConnectionPool.get();
        var preparedStatement = connection.prepareStatement(
                CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, m.getCompanyId());
            preparedStatement.setString(2, m.getModelName());
            preparedStatement.setObject(3, m.getModelYears());
            preparedStatement.setObject(4, m.getEngineId());
            preparedStatement.setObject(5, m.getTransmissionId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                m.setId(generatedKeys.getInt("id"));
            }
            return m;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean delete(Integer id){
        try (var connection = ConnectionPool.get();
        var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, Integer.valueOf(id));
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ModelsDao getInstance(){
        return INSTANCE;
    }
}
