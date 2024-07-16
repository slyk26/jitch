package at.slyk.twitch;

import at.slyk.PrefService;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class TwitchChat {
    private final TwitchClient twitchClient;

    private String currentChannel = null;
    private static final String TWITCH = "twitch";

    public TwitchChat() {
        var builder = TwitchClientBuilder.builder()
                .withEnableChat(true);
        try {
            log.info("with Chat Account: {}", PrefService.getToken());
            builder.withChatAccount(new OAuth2Credential(TWITCH, PrefService.getToken()));
        } catch (NullPointerException e) {
            log.info("fresh login");
        }
        this.twitchClient = builder.build();
    }

    public void lateLogin() {
        log.info("user late login");
        twitchClient.getChat().getCredentialManager().addCredential(TWITCH, new OAuth2Credential(TWITCH, PrefService.getToken()));
    }

    public void leaveChannel() {
        log.info("leaving {}", currentChannel);
        this.twitchClient.getChat().leaveChannel(currentChannel);
    }

    public boolean joinChannel(String channel){

        if(channel.equalsIgnoreCase(currentChannel)) return false;

        if(currentChannel != null) {
            this.leaveChannel();
        }

        log.info("joining {}", channel);
        this.twitchClient.getChat().joinChannel(channel);
        this.currentChannel = channel;
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
