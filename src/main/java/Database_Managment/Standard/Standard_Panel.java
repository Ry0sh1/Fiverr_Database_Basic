package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;
import java.awt.*;

public class Standard_Panel extends JPanel {

    public Standard_Panel() {
        init();
    }

    public Standard_Panel(LayoutManager layout) {
        super(layout);
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setForeground(Global.FOREGROUND);
    }

}
