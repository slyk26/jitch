package at.slyk.gui.chat;

import at.slyk.gui.chat.emotes.Emote;
import at.slyk.gui.chat.emotes.Emotes;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Message extends JPanel {

    private static boolean toggle = false;

    private final String rawMessage;

    public Message(String user, String msg, boolean system) {
        super(new FlowLayout(FlowLayout.LEFT));
        var emotes = new Emotes();
        this.rawMessage = msg;
        if(toggle) {
            this.setBackground(Color.LIGHT_GRAY);
        } else {
            this.setBackground(Color.GRAY);
        }

        if(system){
            setBackground(Color.YELLOW);
        } else {
            Message.setToggle(!toggle);
        }
        this.add(new JLabel(user + (!system ? ":" : "")));

        for (String word : msg.split(" ")) {
            var e = emotes.getEmote(word);

            if (e != null) {
                this.add(new Emote(e));
            } else {
                for(JLabel l: wordWrappedJLabel(user, word)) {
                    log.trace("adding size: {}", l.getPreferredSize());
                    this.add(l);
                }
            }
        }

        var rows = this.getPreferredSize().width / ChatPanel.MESSAGE_PADDING + 1;
        this.setPreferredSize(new Dimension(ChatPanel.MESSAGE_PADDING, this.getPreferredSize().height*rows));
        log.trace("rows: {} size: {}", rows, this.getPreferredSize());
        this.setVisible(true);
    }

    private static void setToggle(boolean t) {
        toggle = t;
    }

    private static java.util.List<JLabel> wordWrappedJLabel(String user, String word) {
        if(word.length() < 30) return List.of(new JLabel(word));

        log.trace("{}", word);
        int userW = new JLabel(user + ":").getPreferredSize().width;
        java.util.List<JLabel> ret = new ArrayList<>();
        JLabel toBeAdded = new JLabel();
        for(char c : word.toCharArray()) {
            toBeAdded.setText(toBeAdded.getText() + c);
            log.trace("current width: {} - label: {}", toBeAdded.getPreferredSize().width, toBeAdded.getText());
            if(toBeAdded.getPreferredSize().width + (ret.isEmpty() ? userW + 20 : 0) >= ChatPanel.MESSAGE_PADDING ) {
                log.trace("max: {} adding {} size: {}", ChatPanel.MESSAGE_PADDING, toBeAdded.getText() + userW, toBeAdded.getPreferredSize().width);
                ret.add(toBeAdded);
                toBeAdded = new JLabel();
            }
        }
        ret.add(toBeAdded);
        return ret;
    }
}