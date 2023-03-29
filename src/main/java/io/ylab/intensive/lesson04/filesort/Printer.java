package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Printer {
    public void printInFile(DataSource dataSource, String fileName) throws FileNotFoundException, SQLException {
        String sortQuery = "select * from numbers order by val desc";
        PrintWriter printWriter = new PrintWriter(fileName);
        String cleanQuery = "delete from numbers";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sortQuery);
            PreparedStatement deleteStatement = connection.prepareStatement(cleanQuery)) {

            ResultSet rs = preparedStatement.executeQuery();

            try {
                while (rs.next()) {
                    printWriter.println(rs.getString(1));
                }
            } finally {
                printWriter.close();
            }

            deleteStatement.executeUpdate();
        }
    }
}
