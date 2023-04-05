package day3;

import day1.util.ConnectionManager;

import java.sql.SQLException;

public class MetaData_3 {
    /*
    С помощью MetaData мы можем получить абсолютно любую информацию об наших бд
     */
    public static void main(String[] args) {
        try (var connection = ConnectionManager.openConnection()) {

            var metaData = connection.getMetaData();

            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString("TABLE_CAT");
                System.out.println(catalog);

                var schemas = metaData.getSchemas(catalog, "cars");
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    System.out.println(schema);

                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    while (tables.next()) {
                        var table = tables.getString("TABLE_NAME");
                        System.out.println(table);

                        var columns = metaData.getColumns(catalog,schema,table,"%");
                        while (columns.next()){
                            System.out.println(columns.getString("COLUMN_NAME"));
                        }
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
