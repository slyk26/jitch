package at.slyk.twitch.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stream {
    private String type;
    private String url;
    private String master;

    public String getUrl() {
        return url.replace("<a href='", "").replace("</a>","").replace(">", "").replace("' target='_blank' title='", "");
    }
}
