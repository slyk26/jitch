package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    public MainFrame(){
        super();

        int appHeight = 740;
        boolean withPlayer = false;

        this.setContentPane(new MainPanel(withPlayer));
        this.setResizable(false);
        this.setTitle("Jitch demo");
        if (withPlayer) {
            this.setSize(new Dimension(1550, appHeight));
        } else {
            this.setSize(new Dimension(ChatPanel.CHAT_WIDTH.get(), appHeight));
        }
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}
