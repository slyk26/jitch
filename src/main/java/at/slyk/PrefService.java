package at.slyk;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

@Slf4j
public class PrefService {

    @Getter
    private static final String CLIENTID = "l4friai92t6hqfq618foapccq8irwt";

    public enum Pref {
        USERNAME("username"),
        TOKEN("token");

        private final String key;

        Pref(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }
    }

    private static String get(Pref pref) {
        return Preferences.userRoot().node(PrefService.class.getName()).get(pref.toString(), null);
    }

    private static void set(Pref pref, String value) {
        Preferences.userRoot().node(PrefService.class.getName()).put(pref.toString(), value);
    }

    private static void clear() {
        try {
            Preferences.userRoot().node(PrefService.class.getName()).clear();
        } catch (BackingStoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String getUsername() {
        return get(Pref.USERNAME);
    }

    public static String getToken() {
        return get(Pref.TOKEN);
    }

    public static void setUsername(String username) {
        set(Pref.USERNAME, username);
    }

    public static void setToken(String token) {
        set(Pref.TOKEN, token);
    }

    public static void invalidate() {
        clear();
    }

}
