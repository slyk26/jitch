package at.slyk.gui;

import at.slyk.gui.chat.ChatPanel;
import at.slyk.gui.player.StreamPlayer;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class MainPanel extends JPanel {
    public MainPanel(boolean withPlayer) {
        super(new BorderLayout());

        StreamPlayer player = null;

        if (withPlayer) {
            player = new StreamPlayer();
        }

        var chatPanel = new ChatPanel(player);
        var bar = new MainMenuBar();

        this.add(bar, BorderLayout.NORTH);

        if (withPlayer) {
            this.add(chatPanel, BorderLayout.EAST);
            this.add(player, BorderLayout.CENTER);
        } else {
            this.add(chatPanel, BorderLayout.CENTER);
        }
    }
}
