package com.demoekz_dmitr;

import com.demoekz_dmitr.ui.MainForm;

import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        FontChange.allFontChange(new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT,14));
        MainForm form = new MainForm();
        form.setVisible(true);
        form.pack();
    }
}
