package at.slyk.components;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        super();

        this.setContentPane(new MainPanel());
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setTitle("Jitch demo");
        this.setSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
