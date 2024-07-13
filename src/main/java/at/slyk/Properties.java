package at.slyk;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class Properties {

    public enum Property {

        TWITCH_CLIENT_ID("twitch.client.id"),
        TWITCH_AUTHORIZATION("twitch.authorization");

        private final String key;

        Property(final String key){
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }

    }

    private static final Map<String, String> p = new HashMap<>();

    private Properties() {}

    public static void init(){
        Dotenv dotenv = Dotenv.load();
        set(Property.TWITCH_CLIENT_ID, dotenv.get("TWITCH_CLIENT_ID"));
    }

    public static void set(Property key, String value) {
       log.debug("Writing: {} -> {}", key, value);
        p.put(key.toString(), value);
    }

    public static String get(Property key) {
        log.debug("Reading: {} -> {}", key, p.get(key.toString()));
        return p.getOrDefault(key.toString(), null);
    }
}
