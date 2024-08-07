package at.slyk.twitch.types;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TwitchResponsePaginated<T> extends TwitchResponse<T> {
    private Pagination pagination;
}
