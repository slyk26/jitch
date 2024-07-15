package at.slyk.twitch;

import at.slyk.Properties;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static at.slyk.gui.MainPanel.player;

@Slf4j
public class TwitchChat {
    private final TwitchClient twitchClient;
    private static final TwitchApi api = new TwitchApi();

    private String currentChannel = null;

    public TwitchChat() {
        this.twitchClient = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .withChatAccount(new OAuth2Credential("twitch", Properties.get(Properties.Property.TWITCH_AUTHORIZATION)))
                .build();
    }

    public boolean joinChannel(String channel){

        if(channel.equalsIgnoreCase(currentChannel)) return false;

        if(currentChannel != null) {
            log.info("leaving {}", currentChannel);
            this.twitchClient.getChat().leaveChannel(currentChannel);
        }

        log.info("joining {}", channel);
        this.twitchClient.getChat().joinChannel(channel);
        this.currentChannel = channel;
        player.get().play(api.getStreams(channel).getStreams().getSevenTwentyPSixtyFrames().getUrl());
        return true;
    }

    public void onMessage(Consumer<ChannelMessageEvent> consumer) {
        this.twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, consumer);
    }

    public void send(String msg){
        log.debug("sending message channel: {}, msg: {}", this.currentChannel, msg);
        if(! this.twitchClient.getChat().sendMessage(this.currentChannel, msg)) {
            log.error("Cannot add msg to queue");
        }
    }
}
