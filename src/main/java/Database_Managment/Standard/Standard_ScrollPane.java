package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;
import java.awt.*;

public class Standard_ScrollPane extends JScrollPane {

    public Standard_ScrollPane(Component view) {
        super(view);
        init();
    }

    private void init(){
        getVerticalScrollBar().setUI(Global.BUTTON_SCROLLBAR_UI);
        setBackground(Global.BACKGROUND_1);
        setForeground(Global.FOREGROUND);
        setBorder(BorderFactory.createLineBorder(Global.LINES,2));
        setPreferredSize(new Dimension(800,400));
    }

}
