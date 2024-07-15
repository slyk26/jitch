package at.slyk.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        super();

        this.setContentPane(new MainPanel());
        this.setResizable(false);
        this.setTitle("Jitch demo");
        this.setSize(new Dimension(1550, 740));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
