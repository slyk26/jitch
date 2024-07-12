package at.slyk.components.chat;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

@Getter
public class Message extends JPanel {

    @SneakyThrows
    public Message(String user, String msg) {
        super();
        this.setBackground(Color.RED);
        this.setMinimumSize(new Dimension(ChatPanel.CHAT_WIDTH, this.getHeight()));
        var emotes = new Emotes();
        this.add(new JLabel(user + ": "));

        for (String word : msg.split(" ")) {
            var e = emotes.getEmote(word);

            if (e != null) {
                this.add(new Emote(e));
            } else {
                this.add(new JLabel(word));
            }
        }

        this.setVisible(true);
    }
}