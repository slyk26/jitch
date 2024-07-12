package at.slyk.utils;

import lombok.extern.log4j.Log4j2;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Log4j2
public class Utils {
    public static boolean isValidUrl(String url) {
        try {
            URL u = URI.create(url).toURL();
            u.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(URI.create(urlString));
        } catch (Exception e) {
            log.warn("Can not open webpage, trying xdg-open");
            Utils.openWebpageLinux(urlString);
        }
    }

    public static void openWebpageLinux(String url) {
        try {
            Runtime.getRuntime().exec(new String[] {"xdg-open", url});
        } catch (IOException e) {
           log.error("Cannot open webpage, giving up");
        }
    }
}
