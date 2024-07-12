package at.slyk.components.chat;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    public static final int CHAT_WIDTH = 300;

    public ChatPanel() {
        super(new BorderLayout());
        var chatLog = new ChatLog();

        this.setPreferredSize(new Dimension(CHAT_WIDTH, this.getPreferredSize().height));
        this.add(chatLog, BorderLayout.CENTER);
        chatLog.pushDownScrollbar();
        this.add(new InputBox(chatLog), BorderLayout.SOUTH);
    }

}
