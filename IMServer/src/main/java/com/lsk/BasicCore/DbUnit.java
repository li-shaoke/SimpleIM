package com.lsk.BasicCore;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class DbUnit {
    private static String driver="";
    private static String userName="";
    private static String password="";
    private static String url="";

    //
    static {
        try{
            Properties properties=new Properties();
            InputStream inputStream=DbUnit.class
                    .getClassLoader()
                    .getResourceAsStream("DbConnectionInfo.properties");
            properties.load(inputStream);

            driver=properties.getProperty("driver");
            userName=properties.getProperty("username");
            password=properties.getProperty("password");
            url=properties.getProperty("url");

            Class.forName(driver);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static void closeResultSet(ResultSet resultSet){
        if (resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection(){
        Connection connection=null;
        try{
            connection= DriverManager.getConnection(url,userName,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet){
        closeResultSet(resultSet);

        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        closeConnection(connection);
    }

    public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet){
        closeResultSet(resultSet);
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnection(connection);
    }
}
