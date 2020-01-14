package data;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Connect {

    public static Connection connect() {
        Connection connect = null;
        try{
            String url = "jdbc:sqlite:/Users/ines/Desktop/ESS/database.db";
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection(url);
        } catch(SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        return connect;
    }

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {}
    }

    public static <T> T executeQuery(Connection connection, String query, DAOFunction<ResultSet, T> function) throws SQLException{
        PreparedStatement ppstt = connection.prepareStatement(query);
        return executeQuery(connection, ppstt, function);
    }

    public static <T> T executeQuery(Connection connection, PreparedStatement ppstt, DAOFunction<ResultSet, T> function) throws SQLException{
        try {
            ResultSet rs = ppstt.executeQuery();
            return function.apply(rs);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw e;
        }
        finally {
            close(connection);
        }
    }

}
