package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;
import at.slyk.gui.player.StreamPlayer;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class MainPanel extends JPanel {
    public static final AtomicReference<StreamPlayer> player = new AtomicReference<>();

    public MainPanel() {
        super(new BorderLayout());
        player.set(new StreamPlayer(this));
        this.setBackground(Color.BLACK);
        this.add(new ChatPanel(), BorderLayout.EAST);
        this.add(new MainMenuBar(), BorderLayout.NORTH);
    }
}
