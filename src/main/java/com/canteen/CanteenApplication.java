package com.canteen;

import com.canteen.gui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CanteenApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                new LoginFrame().setVisible(true);
            }
        });
    }
}