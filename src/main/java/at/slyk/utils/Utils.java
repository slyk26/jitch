package at.slyk.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class Utils {

    private Utils() {
    }

    public static boolean isValidUrl(String url) {
        try {
            URL u = URI.create(url).toURL();
            u.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static URL toURL(String url){
        URL a = null;
        if(isValidUrl(url)){
            try {
                a = URI.create(url).toURL();
            } catch (MalformedURLException e) {
                log.debug(e.getMessage());
            }
        }
        assert a != null;
        return a;
    }

    public static void openWebpage(String urlString) {
        try {
            if (!isValidUrl(urlString)) return;
            Desktop.getDesktop().browse(URI.create(urlString));
        } catch (Exception e) {
            Utils.openWebpageLinux(urlString);
        }
    }

    public static void openWebpageLinux(String url) {
        try {
            Runtime.getRuntime().exec(new String[]{"xdg-open", url});
        } catch (IOException e) {
            log.error("Cannot open webpage");
        }
    }

    public static String inputStreamToString(InputStream inputStream) {
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
