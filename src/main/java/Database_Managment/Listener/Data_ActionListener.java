package Database_Managment.Listener;

import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.Listener.Dialogs.AddWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Data_ActionListener implements ActionListener {

    private final String buttonName;
    public Data_ActionListener(String buttonName){
        this.buttonName = buttonName;
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (buttonName){
            case "add" ->{
                new AddWindow(Frame_Dashboard.FRAME);
            }
            case "remove" ->{
                //TODO:REMOVE
            }
            case "change" ->{
                //TODO:CHANGE
            }
            case "switchTable" ->{
                //TODO:SWITCH_TABLE
            }

        }

    }

}
