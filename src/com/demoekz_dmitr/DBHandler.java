package com.demoekz_dmitr;

import java.sql.*;

public class DBHandler {

    private static Connection connection;
    public static void openConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pre_dem5","root","1234");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ResultSet execQuery(String query){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            if (query.contains("Select")){
                resultSet = preparedStatement.executeQuery();
            } else{
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
}
