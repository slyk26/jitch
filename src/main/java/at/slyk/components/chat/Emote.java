package at.slyk.components.chat;

import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Log4j2
public class Emote extends JPanel {
    private Image i;

    public Emote(URL url) {
        super();
        try {
            i = ImageIO.read(url);
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(i, 0,0, getWidth() * 4, getHeight() * 4, this);
    }
}
