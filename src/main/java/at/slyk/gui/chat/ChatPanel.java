package at.slyk.gui.chat;

import at.slyk.Main;
import at.slyk.gui.player.StreamPlayer;
import at.slyk.twitch.TwitchChat;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ChatPanel extends JPanel {
    public static final AtomicInteger CHAT_WIDTH = new AtomicInteger(370);
    public static final int MESSAGE_CONTAINER = CHAT_WIDTH.get() - 20;
    public static final Color BACKGROUND = Color.decode("#282b30");

    private final JPanel view;
    private final JScrollPane pane;

    private boolean paused = false;
    private transient TwitchChat twitchChat;
    private final StreamPlayer playerRef;

    public ChatPanel(StreamPlayer player) {
        super(new BorderLayout());
        var wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.view = new JPanel();
        this.playerRef = player;
        this.view.setMaximumSize(new Dimension(CHAT_WIDTH.get(), this.view.getPreferredSize().height));
        this.view.setLayout(new BoxLayout(this.view, BoxLayout.Y_AXIS));
        wrapper.add(this.view);
        wrapper.setBackground(BACKGROUND);
        this.pane = new JScrollPane(wrapper);
        this.pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.setPreferredSize(new Dimension(CHAT_WIDTH.get(), this.getPreferredSize().height));

        var search = new JPanel(new GridLayout(0, 1));
        search.add(new SearchBar(this));
        this.add(search, BorderLayout.NORTH);
        this.add(this.pane, BorderLayout.CENTER);
        this.add(new InputBox(this), BorderLayout.SOUTH);
        this.initChatKeybinds();

        Main.user.subscribe(u -> {
            if (u == null) {
                this.view.removeAll();
                if (twitchChat != null)
                    twitchChat.leaveChannel();
            } else {
                SwingUtilities.invokeLater(() -> {
                    twitchChat = new TwitchChat();
                    twitchChat.onMessage(m -> this.addMessage(new Message(m, false)));
                });
            }
        });
    }

    public void addMessage(Message msg) {
        this.view.add(new JSeparator());
        this.view.add(msg);
        this.validate();
        if (!paused) {
            this.pushDownScrollbar();
        }
        log.trace("{} {} {}", msg.getText(), msg.getPreferredSize(), msg);
    }

    public void joinChat(String channel) {
        if (twitchChat.joinChannel(channel)) {
            this.view.removeAll();
            this.addMessage(new Message("Joining ", channel, true));

            if (playerRef != null) {
                playerRef.play(channel);
            }
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
