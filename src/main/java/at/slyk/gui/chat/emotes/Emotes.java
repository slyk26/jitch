package at.slyk.gui.chat.emotes;

import lombok.extern.log4j.Log4j2;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Emotes {

    private final Map<String, URL> test;

    public Emotes() {
        this.test = new HashMap<>();

        try {
            this.test.put("ULLE", URI.create("https://cdn.7tv.app/emote/629b803d0e9a57f274bef680/1x.png").toURL());
            this.test.put("NODDERS", URI.create("https://cdn.7tv.app/emote/60ae4bb30e35477634610fda/1x.gif").toURL());
        } catch (MalformedURLException e) {
            log.error(e);
        }
    }

    public URL getEmote(String name){
        return this.test.getOrDefault(name, null);
    }
}
