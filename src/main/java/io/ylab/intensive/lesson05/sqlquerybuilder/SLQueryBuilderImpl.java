package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SLQueryBuilderImpl implements SQLQueryBuilder{

    private final DataSource dataSource;

    public SLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {

        List<String> columnsNames = new ArrayList<>();

        try(java.sql.Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet rs = databaseMetaData.getColumns(null, null, tableName, null);
            while (rs.next()) {
                columnsNames.add(rs.getString("COLUMN_NAME"));
            }
        }
        if (columnsNames.size() > 0) {
            return "SELECT " + String.join(", ", columnsNames) + " FROM " + tableName +";";
        } else {
            return null;
        }

    }

    @Override
    public List<String> getTables() throws SQLException {

        List<String> tablesNames = new ArrayList<>();

        try(java.sql.Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet rs = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE","SYSTEM TABLE"});
            while (rs.next()) {
                tablesNames.add(rs.getString(3));
            }
        }
        return tablesNames;
    }
}
