package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;
import at.slyk.gui.chat.SearchBar;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainPanel extends JPanel {
    public MainPanel() {
        super(new BorderLayout());
        this.setBackground(Color.BLACK);

        var chatPanel = new ChatPanel();
        var sp = new SearchBar(chatPanel);
        chatPanel.add(sp, BorderLayout.NORTH);
        this.add(chatPanel, BorderLayout.EAST);
        this.add(new MainMenuBar(), BorderLayout.NORTH);
    }
}
