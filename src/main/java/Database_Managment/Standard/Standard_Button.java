package Database_Managment.Standard;

import Database_Managment.Global;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Standard_Button extends JButton {

    public Standard_Button(String text) {
        super(text);
        init();
    }

    private void init(){
        setBackground(Global.BACKGROUND_1);
        setForeground(Global.FOREGROUND);
        setBorder(BorderFactory.createLineBorder(Global.LINES,2));
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton b = (JButton) e.getComponent();
                b.setBackground(Global.BACKGROUND_2);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                JButton b = (JButton) e.getComponent();
                b.setBackground(Global.BACKGROUND_1);
            }
        });
    }

}
