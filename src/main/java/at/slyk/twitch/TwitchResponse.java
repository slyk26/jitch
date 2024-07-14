package at.slyk.twitch;

import lombok.Data;

import java.util.List;

@Data
public class TwitchResponse<T> {
    private List<T> data;
}
