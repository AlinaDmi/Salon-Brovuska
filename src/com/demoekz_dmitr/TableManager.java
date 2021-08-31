package com.demoekz_dmitr;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableManager {


    public static void refreshTable(JTable table, String sort){

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Товар","Цена","Активность"});
        ResultSet resultSet;
        DBHandler.openConnection();
        if(sort.equals("Все активности") || sort.equals("")){
            resultSet = DBHandler.execQuery("Select * from product");
        } else{
            resultSet = DBHandler.execQuery("Select * from product where IsActive = "+Integer.parseInt(sort)+"");
        }

            try {
                while (resultSet.next()){
                    tableModel.addRow(new String[]{
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(6)
                    });
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        DBHandler.closeConnection();
        table.setModel(tableModel);
    }
}
