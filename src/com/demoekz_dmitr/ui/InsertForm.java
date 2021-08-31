package com.demoekz_dmitr.ui;

import com.demoekz_dmitr.DBHandler;
import com.demoekz_dmitr.Main;
import com.demoekz_dmitr.TableManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class InsertForm extends JFrame{
    private JTextField titleField;
    private JTextField costField;
    private JTextField descField;
    private JComboBox sellerBox;
    private JTextField imgFiled;
    private JCheckBox activeCheckBox;
    private JButton backBtn;
    private JButton saveBtn;
    private JPanel mainPanel;
    private JLabel idLabel;
    private  Boolean isOk = true;
    private  String title;
    private Double cost = null;
    private String desc = null;
    private String img;
    private int active = 0;


    public InsertForm(JTable table, String name){
        setContentPane(mainPanel);
        setTitle("Салон красоты Бровушка - добавление товара");
        setMinimumSize(new Dimension(400,500));
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("beauty_logo.png"))).getImage());

        ResultSet resultSet;

        DBHandler.openConnection();
        resultSet=DBHandler.execQuery("Select name from manufacturer");

            try {
                while (resultSet.next()){
                    sellerBox.addItem(resultSet.getString(1));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        DBHandler.closeConnection();

            backBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            if (!name.equals("")){
                DBHandler.openConnection();
                String sellerName="";
                ResultSet resultSet1 = DBHandler.execQuery("Select * from product where Title='"+name+"'");
                try {
                    while (resultSet1.next()){
                        idLabel.setText("ID товара: "+resultSet1.getString(1));
                        titleField.setText(resultSet1.getString(2));
                        costField.setText(resultSet1.getString(3));
                        descField.setText(resultSet1.getString(4));
                        imgFiled.setText(resultSet1.getString(5));
                        if (Integer.parseInt(resultSet1.getString(6))==1){
                            activeCheckBox.setSelected(true);
                        }
                        sellerName = resultSet1.getString(7);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                resultSet=DBHandler.execQuery("Select name from manufacturer where ID = "+Integer.parseInt(sellerName)+"");
                try {
                    while (resultSet.next()){
                        sellerBox.setSelectedItem(resultSet.getString(1));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                DBHandler.closeConnection();
            }

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seller = 0;
                validAll();

                DBHandler.openConnection();
                if (sellerBox.getSelectedIndex()!=0){
                    ResultSet resultSet1 = DBHandler.execQuery("Select ID from manufacturer where Name= '"+sellerBox.getSelectedItem().toString()+"'");

                    try {
                        while (resultSet1.next()){
                           seller = Integer.parseInt(resultSet1.getString(1));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                if (isOk){
                    System.out.println(title+" "+cost+" "+desc+" "+img+" "+active+" "+seller);
                    DBHandler.execQuery("Insert into product(Title,Cost,Description,MainImagePath,IsActive,ManufacturerID) values ('"
                            +title+"', "
                            +cost+", '"
                            +desc+"', '"
                            +img+"', "
                            +active+", "
                            +seller+")"
                    );
                    JOptionPane.showMessageDialog(null,"Товар успешно добавлен","Успех",JOptionPane.INFORMATION_MESSAGE);
                    TableManager.refreshTable(table,"");
                    dispose();
                }
                    DBHandler.closeConnection();
            }
        });

    }



    private void validAll(){
        isOk=true;
        title = titleField.getText();
        if (title.length()>100 || title.length()==0){
            JOptionPane.showMessageDialog(null,"Название не может быть длинее 100 символов или нулевым","Внимание",JOptionPane.WARNING_MESSAGE);
            isOk=false;
        }

        try {
            cost = Double.parseDouble(costField.getText());
        } catch (NumberFormatException exception){
            JOptionPane.showMessageDialog(null,"Цена не может включать буквенные символы или отсутвовать","Внимание",JOptionPane.WARNING_MESSAGE);
            isOk=false;
        }

        img = imgFiled.getText();
        if (img.length()>1000 ){
            JOptionPane.showMessageDialog(null,"Путь к фотографии не может быть длинее 1000 символов","Внимание",JOptionPane.WARNING_MESSAGE);
            isOk=false;
        }

        if (activeCheckBox.isSelected()){
            active=1;
        } else {
            active=0;
        }
    }
}
