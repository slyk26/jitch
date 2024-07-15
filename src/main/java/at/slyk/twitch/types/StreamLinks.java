package at.slyk.twitch.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamLinks {
    private Metadata metadata;
    private String plugin;
    private Streams streams;
}
