package at.slyk.twitch.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TwitchUser {
    private String id;
    private String login;
    @JsonProperty("display_name")
    private String displayName;
    private String type;
    @JsonProperty("broadcaster_type")
    private String broadcasterType;
    private String description;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("offline_image_url")
    private String offlineImageUrl;
    @JsonProperty("view_count")
    private int viewCount;
    @JsonProperty("created_at")
    private Date createdAt;
}
