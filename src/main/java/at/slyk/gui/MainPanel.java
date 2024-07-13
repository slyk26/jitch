package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;

@Log4j2
public class MainPanel extends JPanel {
    public MainPanel() {
        super(new BorderLayout());
        this.setBackground(Color.BLACK);

        this.add(new ChatPanel(), BorderLayout.EAST);
        this.add(new MainMenuBar(), BorderLayout.NORTH);
    }
}
