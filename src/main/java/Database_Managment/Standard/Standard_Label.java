package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;

public class Standard_Label extends JLabel {

    public Standard_Label(String text) {
        super(text);
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setForeground(Global.FOREGROUND);
    }

}
