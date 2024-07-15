package at.slyk.twitch.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Streams{
    @JsonProperty("audio_only")
    private Stream audioOnly;
    @JsonProperty("160p")
    private Stream oneSixtyP;
    @JsonProperty("360p")
    private Stream threeSixtyP;
    @JsonProperty("480p")
    private Stream fourEightyP;
    @JsonProperty("720p60")
    private Stream sevenTwentyPSixtyFrames;
    @JsonProperty("1080p60")
    private Stream tenEightyPSixtyFrames;
    // ??
    private Stream worst;
    private Stream best;
}
