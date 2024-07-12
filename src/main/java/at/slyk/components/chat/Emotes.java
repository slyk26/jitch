package at.slyk.components.chat;

import lombok.extern.log4j.Log4j2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Emotes {

    private final Map<String, URL> emotes;

    public Emotes() {
        this.emotes = new HashMap<>();

        try {
            this.emotes.put("ULLE", URI.create("https://cdn.7tv.app/emote/629b803d0e9a57f274bef680/2x.png").toURL());
        } catch (MalformedURLException e) {
            log.error(e);
        }
    }

    public URL getEmote(String name){
        return this.emotes.getOrDefault(name, null);
    }
}
