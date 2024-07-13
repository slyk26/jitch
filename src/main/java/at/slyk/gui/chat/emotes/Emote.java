package at.slyk.gui.chat.emotes;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

@Log4j2
public class Emote extends JPanel {
    public Emote(URL url) {
        super();
        this.setLayout(new BorderLayout());
        var i = new ImageIcon(url);
        this.add(new JLabel(i), BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(i.getIconWidth(), i.getIconHeight()));
    }
}
