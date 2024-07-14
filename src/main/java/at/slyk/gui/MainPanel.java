package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainPanel extends JPanel {
    public MainPanel() {
        super(new BorderLayout());
        this.setBackground(Color.BLACK);

        this.add(new ChatPanel(), BorderLayout.EAST);
        this.add(new MainMenuBar(), BorderLayout.NORTH);
    }
}
