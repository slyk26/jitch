package at.slyk.twitch.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreamLinkFormResponse {
    private int status;
    private List<String> output;
    private List<String> errors;

}
