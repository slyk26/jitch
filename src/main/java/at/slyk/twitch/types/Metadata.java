package at.slyk.twitch.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    private String id;
    private String author;
    private String category;
    private String title;
}
