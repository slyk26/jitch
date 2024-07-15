package at.slyk.twitch.types;

import lombok.Data;

import java.util.List;

@Data
public class TwitchResponse<T> {
    private List<T> data;
}
