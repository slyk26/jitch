package at.slyk.twitch.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Emote {
    private String id;
    private String name;
    private Map<String, String> images;
    private List<String> format;
    private List<String> scale;
    @JsonProperty("theme_mode")
    private List<String> theme;
}
