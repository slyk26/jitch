package at.slyk.twitch.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelEmote extends Emote {
    private String tier;
    @JsonProperty("emote_type")
    private String emoteType;
    @JsonProperty("emote_set_id")
    private String emoteSetId;

}
