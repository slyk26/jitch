package at.slyk.twitch;

import at.slyk.PrefService;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import lombok.extern.slf4j.Slf4j;
import rx.subjects.PublishSubject;

import java.util.function.Consumer;


@Slf4j
public class TwitchChat {
    private TwitchClient twitchClient;

    public static final PublishSubject<String> channel = PublishSubject.create();
    private String currentChannel = null;
    private static final String TWITCH = "twitch";

    public TwitchChat() {
        this.login();

        channel.subscribe(c -> this.currentChannel = c);
    }

    public void login() {
        log.debug("chat login");
        this.twitchClient = TwitchClientBuilder.builder()
                .withChatAccount(new OAuth2Credential(TWITCH, PrefService.getToken()))
                .withEnableChat(true)
                .build();
    }

    public void leaveChannel() {
        if (currentChannel != null) {
            log.info("leaving {}", currentChannel);
            this.twitchClient.getChat().leaveChannel(currentChannel);
        }
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
