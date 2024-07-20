package at.slyk.gui.player;

import at.slyk.twitch.TwitchApi;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;


@Slf4j
public class StreamPlayer extends EmbeddedMediaPlayerComponent {
    private final EmbeddedMediaPlayerComponent internal;
    private static final TwitchApi api = new TwitchApi();

    public StreamPlayer() {
        super();
        this.internal = new EmbeddedMediaListPlayerComponent();
    }


    public void play(String channel) {
        SwingUtilities.invokeLater(() -> {
            var res = api.getStreams(channel);
            try {
                var mrl = res.getStreams().getSevenTwentyPSixtyFrames().getUrl();
                log.debug(mrl);
                internal.mediaPlayer().media().play(mrl);
            } catch (Exception e) {
                log.error("play failed because invalid parsing of link");
            }
        });
    }

}
