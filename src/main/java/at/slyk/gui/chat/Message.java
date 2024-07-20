package at.slyk.gui.chat;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

@Slf4j
@Getter
public class Message extends JTextPane {

    private final boolean system;
    private final String user;
    private final Color userColor;
    private final String rawMessage;

    public Message(ChannelMessageEvent e, boolean system) {
        super();
        this.rawMessage = e.getMessage();
        this.system = system;
        this.user = e.getUser().getName();
        this.userColor = Color.decode(e.getMessageEvent().getUserChatColor().orElse("#000"));
        setMessage();
    }

    public Message(String user, String msg, boolean system) {
        super();
        this.rawMessage = msg;
        this.user = user;
        this.system = system;
        this.userColor = Color.WHITE;
        setMessage();
    }

    private void setMessage() {
        append(user, userColor, true);
        append(": ", Color.WHITE, true);
        append(rawMessage, Color.WHITE, false);
        setBackground(ChatPanel.BACKGROUND);
        resize();
        setEditable(false);
    }

    private void append(String msg, Color c, boolean bold) {
        MutableAttributeSet aset = new SimpleAttributeSet(SimpleAttributeSet.EMPTY);
        aset.addAttribute(StyleConstants.Foreground, c);
        aset.addAttribute(StyleConstants.Alignment, StyleConstants.LeftIndent);
        StyleConstants.setBold(aset, bold);

        int len = this.getDocument().getLength();
        this.setCaretPosition(len);
        this.setCharacterAttributes(aset, false);
        this.replaceSelection(msg);
    }

    private void resize() {
        this.setMargin(new Insets(0, 0, 0, 0));
        var totalWidth = this.getFontMetrics(this.getFont()).stringWidth(this.getText());
        var lines = totalWidth / ChatPanel.MESSAGE_CONTAINER + 1;
        var newHeight = getPreferredSize().height * lines + 4;
        setPreferredSize(new Dimension(ChatPanel.MESSAGE_CONTAINER, newHeight));
        this.setMargin(new Insets(4, 4, 4, 4));
    }

}