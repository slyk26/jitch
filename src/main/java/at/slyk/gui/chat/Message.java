package at.slyk.gui.chat;

import at.slyk.utils.Utils;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import raven.emoji.AutoWrapText;
import raven.emoji.EmojiIcon;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

import static at.slyk.gui.chat.ChatPanel.CHANNEL_EMOTES;
import static at.slyk.gui.chat.ChatPanel.GLOBAL_EMOTES;

@Slf4j
@Getter
public class Message extends JTextPane {
    private static final ConcurrentHashMap<String, ImageIcon> cache = new ConcurrentHashMap<>();
    private static final String TEMPLATE = "https://static-cdn.jtvnw.net/emoticons/v2/{{id}}/{{format}}/{{theme_mode}}/{{scale}}";

    private final boolean system;
    private final String user;
    private final Color userColor;
    private final String rawMessage;
    private int w = 0;
    private int cnt = 0;

    public Message(ChannelMessageEvent e, boolean system) {
        super();
        this.rawMessage = e.getMessage();
        this.system = system;
        this.user = e.getUser().getName();
        this.userColor = Color.decode(e.getMessageEvent().getUserChatColor().orElse("#ffffff"));
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
        this.setEditorKit(new AutoWrapText(this));
        EmojiIcon.getInstance().installTextPane(this);
        append(user, userColor, true);
        append(": ", Color.WHITE, true);

        for (String word : rawMessage.split(" ")) {
            if (GLOBAL_EMOTES.containsKey(word) || CHANNEL_EMOTES.containsKey(word)) {
                appendEmote(word);
            } else {
                append(word, Color.WHITE, false);
            }
            append(" ", Color.WHITE, false);
        }

        setBackground(ChatPanel.BACKGROUND);
        resize();
        setEditable(false);
    }

    @SneakyThrows
    private void append(String msg, Color c, boolean bold) {
        MutableAttributeSet aset = new SimpleAttributeSet(SimpleAttributeSet.EMPTY);
        aset.addAttribute(StyleConstants.Foreground, c);
        aset.addAttribute(StyleConstants.Alignment, StyleConstants.LeftIndent);
        StyleConstants.setBold(aset, bold);

        int len = this.getStyledDocument().getLength();
        this.setCaretPosition(len);
        this.setCharacterAttributes(aset, false);
        this.replaceSelection(msg);
    }

    private void appendEmote(String emote) {
        var icon = getEmote(emote);
        w += icon.getIconWidth();
        cnt += 1;
        int len = this.getStyledDocument().getLength();
        this.setCaretPosition(len);
        this.insertIcon(icon);
    }

    private ImageIcon getEmote(String emote) {
        ImageIcon ret;

        if (cache.containsKey(emote)) {
            ret = cache.get(emote);
        } else {
            var newEmote = GLOBAL_EMOTES.get(emote);
            if (newEmote == null)
                newEmote = CHANNEL_EMOTES.get(emote);
            var isAnimated = newEmote.getFormat().contains("animated");
            var emoteUrl = TEMPLATE
                    .replace("{{id}}", newEmote.getId())
                    .replace("{{format}}", isAnimated ? "animated" : "static")
                    .replace("{{theme_mode}}", "dark")
                    .replace("{{scale}}", "1.0");
            ret = new ImageIcon(Utils.toURL(emoteUrl));
            cache.put(emote, ret);
        }

        return ret;
    }

    private void resize() {
        var magicPadding = 35d;
        this.setMargin(new Insets(0, 0, 0, 0));
        double totalWidth = this.getFontMetrics(this.getFont()).stringWidth(this.getText()) + w - cnt - magicPadding;
        var lines = (int) Math.ceil(totalWidth / ChatPanel.MESSAGE_CONTAINER);
        var newHeight = getPreferredSize().height * lines + 2;
        log.debug("text: {} total: {} lines: {} newHeight:{} prefHeight: {}", this.getText(), totalWidth, lines, newHeight, getPreferredSize().height);
        setPreferredSize(new Dimension(ChatPanel.MESSAGE_CONTAINER, newHeight));
    }

}