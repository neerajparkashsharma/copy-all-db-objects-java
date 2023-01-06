import java.sql.*;

public class CopyDatabaseObjects {
    public static void main(String[] args) {
        // Replace with your own database connection details
        String sourceDbUrl = "jdbc:mysql://localhost:3306/source_database";
        String sourceUsername = "source_user";
        String sourcePassword = "source_password";
        String targetDbUrl = "jdbc:mysql://localhost:3306/target_database";
        String targetUsername = "target_user";
        String targetPassword = "target_password";

        try (
                // Connect to the source database
                Connection sourceConnection = DriverManager.getConnection(sourceDbUrl, sourceUsername, sourcePassword);
                Statement sourceStatement = sourceConnection.createStatement();

                // Connect to the target database
                Connection targetConnection = DriverManager.getConnection(targetDbUrl, targetUsername, targetPassword);
                Statement targetStatement = targetConnection.createStatement();
        ) {
            // Get a list of all tables in the source database
            ResultSet sourceTablesResultSet = sourceStatement.executeQuery("SHOW TABLES");

            while (sourceTablesResultSet.next()) {
                String tableName = sourceTablesResultSet.getString(1);

                // Get the data for the current table
                ResultSet sourceDataResultSet = sourceStatement.executeQuery("SELECT * FROM " + tableName);

                // Create the table in the target database
                target Statement.executeUpdate("CREATE TABLE " + tableName + " LIKE " + tableName);

                // Insert the data into the target table
                while (sourceDataResultSet.next()) {
                    String columns = "";
                    String values = "";

                    // Build the column names and values strings for the INSERT statement
                    for (int i = 1; i <= sourceDataResultSet.getMetaData().getColumnCount(); i++) {
                        String columnName = sourceDataResultSet.getMetaData().getColumnName(i);
                        String columnValue = sourceDataResultSet.getString(i);
                        if (i > 1) {
                            columns += ", ";
                            values += ", ";
                        }
                        columns += columnName;
                        values += "'" + columnValue + "'";
                    }

                    // Insert the data into the target table
                    target Statement.executeUpdate("INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
