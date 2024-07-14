package at.slyk.gui.chat.emotes;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

@Slf4j
public class Emote extends JPanel {
    public Emote(URL url) {
        super();
        this.setLayout(new BorderLayout());
        var i = new ImageIcon(url);
        this.add(new JLabel(i), BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(i.getIconWidth(), i.getIconHeight()));
    }
}
