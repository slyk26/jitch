package at.slyk.gui.player;

import at.slyk.gui.MainPanel;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;


@Slf4j
public class StreamPlayer extends EmbeddedMediaPlayerComponent {
    private final EmbeddedMediaPlayerComponent internal;

    public StreamPlayer(MainPanel ref) {
        super();
        this.internal = new EmbeddedMediaListPlayerComponent();
        ref.add(internal, BorderLayout.CENTER);
        ref.setVisible(true);

    }


    public void play(String mrl) {
        SwingUtilities.invokeLater(() -> {
            log.debug(mrl);
            internal.mediaPlayer().media().play(mrl);
        });
    }

}
