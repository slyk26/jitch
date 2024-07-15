package at.slyk.gui.chat;

import at.slyk.twitch.types.SearchChannel;
import at.slyk.twitch.TwitchApi;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class SearchBar extends JComboBox<SearchChannel> {

    private static final TwitchApi api = new TwitchApi();

    private String currentSearch = "";
    private transient List<SearchChannel> foundChannels = new ArrayList<>();

    public SearchBar(ChatPanel cp) {
        super();
        this.setEditable(true);


        this.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED && !this.foundChannels.isEmpty()) {
                cp.joinChat(this.foundChannels.getFirst().toString());
            }
        });


        this.addActionListener(e -> {
            String input = Objects.requireNonNull(((SearchBar) e.getSource()).getSelectedItem()).toString();

            if (e.getActionCommand().isBlank() || e.getActionCommand().isEmpty()) {
                return;
            }

            if (!this.currentSearch.equalsIgnoreCase(input)) {
                this.removeAllItems();
                this .foundChannels = getChannels(input);
                log.debug("fetching channels: {}", this.foundChannels);
                this.currentSearch = input;
                for (var channel : foundChannels) {
                    this.addItem(channel);
                }
            }
        });
    }

    private List<SearchChannel> getChannels(String input) {
        var channels = api.searchChannelsByName(input).stream().sorted().collect(Collectors.toCollection(java.util.ArrayList::new));

        var goodMatch = channels.stream().filter(c -> c.getBroadcasterLogin().toLowerCase().startsWith(input.toLowerCase())).findFirst();

        if (goodMatch.isPresent()) {
            channels.remove(goodMatch.get());
            channels.addFirst(goodMatch.get());
        }

        return channels;
    }
}
