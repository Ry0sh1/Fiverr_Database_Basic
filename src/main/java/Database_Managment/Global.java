package Database_Managment;

import javax.swing.*;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class Global {

    public static Color BACKGROUND_1 = new Color(40,40,40);
    public static Color BACKGROUND_2 = new Color(60,60,60);
    public static Color FOREGROUND = new Color(255,255,255);
    public static Color LINES = new Color(180,20,40);
    public static String selected = "Human";
    public static ScrollBarUI BUTTON_SCROLLBAR_UI = new BasicScrollBarUI() {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = Global.BACKGROUND_2;
            this.trackColor = Global.BACKGROUND_1;
        }
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton jbutton = new JButton();
            jbutton.setPreferredSize(new Dimension(0, 0));
            jbutton.setMinimumSize(new Dimension(0, 0));
            jbutton.setMaximumSize(new Dimension(0, 0));
            return jbutton;
        }
    };

}
