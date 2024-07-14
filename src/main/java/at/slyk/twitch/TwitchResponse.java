package at.slyk.twitch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(value = { "pagination"}) // searchChannels
@Data
public class TwitchResponse<T> {
    private List<T> data;
}
