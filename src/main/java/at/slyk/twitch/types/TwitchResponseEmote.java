package at.slyk.twitch.types;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TwitchResponseEmote<T> extends TwitchResponse<T> {
    String template;
}
