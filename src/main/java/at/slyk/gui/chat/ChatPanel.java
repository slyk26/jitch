package at.slyk.gui.chat;

import at.slyk.twitch.TwitchChat;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static at.slyk.gui.MainPanel.player;

@Slf4j
public class ChatPanel extends JPanel {

    public static final int CHAT_WIDTH = 300;
    public static final int MESSAGE_PADDING = CHAT_WIDTH - 25;
    public static final int MESSAGE_CONTAINER = CHAT_WIDTH - 20;

    private boolean paused = false;
    private final JPanel view;
    private final JScrollPane pane;
    private static final TwitchChat twitchChat = new TwitchChat();

    public ChatPanel() {
        super(new BorderLayout());
        var wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.view = new JPanel();
        this.view.setMaximumSize(new Dimension(CHAT_WIDTH, this.view.getPreferredSize().height));
        this.view.setLayout(new BoxLayout(this.view, BoxLayout.Y_AXIS));
        wrapper.add(this.view);
        this.pane = new JScrollPane(wrapper);
        this.pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setPreferredSize(new Dimension(CHAT_WIDTH, this.getPreferredSize().height));

        twitchChat.onMessage(m -> this.addMessage(new Message(m.getUser().getName(), m.getMessage(), false)));

        var search = new JPanel(new GridLayout(0, 1));
        search.add(new SearchBar(this));
        this.add(search, BorderLayout.NORTH);
        this.add(this.pane, BorderLayout.CENTER);
        this.add(new InputBox(this), BorderLayout.SOUTH);
        this.initChatKeybinds();
    }

    public void addMessage(Message msg) {
        var p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(msg);
        p.setPreferredSize(new Dimension(MESSAGE_CONTAINER, msg.getPreferredSize().height));
        this.view.add(p);
        this.validate();
        if (!paused) {
            this.pushDownScrollbar();
        }
    }

    public void joinChat(String channel) {
        if (twitchChat.joinChannel(channel)) {
            this.view.removeAll();
            this.addMessage(new Message("Joining ", channel, true));
            player.get().play(channel);
        }
    }

    public void sendMessage(String message) {
        twitchChat.send(message);
    }

    public void pushDownScrollbar() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = this.pane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    private void initChatKeybinds() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> {
                    if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_ALT) {
                        paused = true;
                    }

                    if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ALT) {
                        paused = false;
                    }

                    return false;
                });
    }
}
