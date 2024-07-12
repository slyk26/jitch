package at.slyk.components;

import at.slyk.components.chat.ChatPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        super(new BorderLayout());
        this.setBackground(Color.BLACK);

        this.add(new ChatPanel(), BorderLayout.EAST);
    }
}
