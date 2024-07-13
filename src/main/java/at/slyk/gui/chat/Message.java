package at.slyk.gui.chat;

import at.slyk.gui.chat.emotes.Emote;
import at.slyk.gui.chat.emotes.Emotes;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class Message extends JPanel {

    public Message(String user, String msg) {
        super();
        var emotes = new Emotes();
        this.setBackground(Color.RED);
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