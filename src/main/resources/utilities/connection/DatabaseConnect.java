package main.resources.utilities.connection;

import org.h2.jdbcx.JdbcConnectionPool;
import main.resources.utilities.notification.Alerts;

import java.sql.*;

public class DatabaseConnect implements ConnectionValues {
    public static ResultSet res = null;
    public static Connection con = null;
    public static Statement stmt = null;
    public static PreparedStatement preparedStatement = null;

    public static Connection connect() {
        try {
            Class.forName(H2_DATABASE_DRIVER);
            JdbcConnectionPool cp = JdbcConnectionPool.create(
                    H2DB_URL, DB_USERNAME, DB_PASSWORD);
            con = cp.getConnection();
            stmt = con.createStatement();
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            Alerts.errorAlert("Connection Error", e.getLocalizedMessage());
        }
        return null;
    }

    public static void close(){
        try {
            preparedStatement.close();
            con.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
