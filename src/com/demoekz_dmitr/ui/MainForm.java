package com.demoekz_dmitr.ui;

import com.demoekz_dmitr.DBHandler;
import com.demoekz_dmitr.Main;
import com.demoekz_dmitr.TableManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static javax.swing.JOptionPane.YES_OPTION;

public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JTable table;
    private JButton delBtn;
    private JButton updateBtn;
    private JButton addBtn;
    private JComboBox sortBox;


    public MainForm(){
        setTitle("Салон красоты Бровушка - просмотр товаров");
        setMinimumSize(new Dimension(800,600));
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(Objects.requireNonNull(Main.class.getClassLoader().getResource("beauty_logo.png"))).getImage());

        TableManager.refreshTable(table,"");
        sortBox.addItem("Все активности");
        sortBox.addItem(0);
        sortBox.addItem(1);

        sortBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableManager.refreshTable(table,sortBox.getSelectedItem().toString());
            }
        });

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow()>0){
                    if ( JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить выбранный товар?","Внимание!",JOptionPane.YES_NO_OPTION)
                    ==YES_OPTION){
                        DBHandler.openConnection();
                        DBHandler.execQuery("Delete from product where Title= '"+table.getValueAt(table.getSelectedRow(),0)+"'");
                        DBHandler.closeConnection();
                        JOptionPane.showMessageDialog(null,"Товар успешно удалён","Успех",JOptionPane.INFORMATION_MESSAGE);
                        TableManager.refreshTable(table,sortBox.getSelectedItem().toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Не выбрано ни одного товара","Внимание",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            InsertForm form = new InsertForm(table,"");
            form.setVisible(true);
            form.pack();
            sortBox.setSelectedItem("Все активности");
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertForm form = new InsertForm(table, table.getValueAt(table.getSelectedRow(),0).toString());
                form.setVisible(true);
                form.pack();
                sortBox.setSelectedItem("Все активности");
            }
        });
    }

}
