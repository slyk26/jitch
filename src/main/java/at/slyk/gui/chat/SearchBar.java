package at.slyk.gui.chat;

import at.slyk.twitch.TwitchApi;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.stream.Collectors;

@Slf4j
public class SearchBar extends JTextField {

    private static final TwitchApi api = new TwitchApi();

    public SearchBar(ChatPanel cp) {
        super();

        this.addActionListener(e -> {
            if (e.getActionCommand().isBlank() || e.getActionCommand().isEmpty()) {
                return;
            }

            var channels = api.searchChannelsByName(e.getActionCommand()).stream().sorted().collect(Collectors.toCollection(java.util.ArrayList::new));

            var goodMatch = channels.stream().filter(c -> c.getBroadcasterLogin().toLowerCase().startsWith(e.getActionCommand().toLowerCase())).findFirst();

            if (goodMatch.isPresent()) {
                channels.remove(goodMatch.get());
                channels.addFirst(goodMatch.get());
            }

            log.debug(channels.toString());
            cp.joinChat(channels.getFirst().getBroadcasterLogin());
        });
    }
}
