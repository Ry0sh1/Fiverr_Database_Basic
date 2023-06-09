package Database_Managment.Listener;

import Database_Managment.GUI.Dashboard.Frame_Dashboard;
import Database_Managment.Listener.Dialogs.Options_AddWindow.AddWindow;
import Database_Managment.Listener.Dialogs.Options_RemoveWindow.RemoveWindow;
import Database_Managment.Listener.Dialogs.Options_SwitchTable.SwitchTableWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
                new RemoveWindow(Frame_Dashboard.FRAME);
            }
            case "change" ->{
                //TODO:CHANGE
            }
            case "switchTable" ->{
                try {
                    new SwitchTableWindow(Frame_Dashboard.FRAME);
                }catch (SQLException sqlException){
                    sqlException.printStackTrace();
                }
            }

        }

    }

}
