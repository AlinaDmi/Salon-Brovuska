package com.demoekz_dmitr;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.util.Enumeration;

public class FontChange {
    public static void allFontChange(FontUIResource font){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource){
                UIManager.put(key,font);
            }
        }
    }
}
