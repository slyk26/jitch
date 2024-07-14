package at.slyk.twitch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = { "tag_ids" }) // because deprecated
@Data
public class SearchChannel implements Comparable<SearchChannel> {
    @Override
    public int compareTo(@NotNull SearchChannel o) {
        return this.broadcasterLogin.compareTo(o.broadcasterLogin);
    }

    @JsonProperty("broadcaster_language")
    private String broadcasterLanguage;
    @JsonProperty("broadcaster_login")
    private String broadcasterLogin;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("game_id")
    private String gameId;
    @JsonProperty("game_name")
    private String gameName;
    private String id;
    @JsonProperty("is_live")
    private boolean isLive;
    private List<String> tags;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    private String title;
    @JsonProperty("started_at")
    private Date startedAt;
}