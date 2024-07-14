package at.slyk.twitch;

import at.slyk.Properties;
import at.slyk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TwitchApi {

    private static final String BASE = "https://api.twitch.tv/helix/";
    private final HttpClient client = HttpClient.newHttpClient();


    public void login() {
        var scopes = URLEncoder.encode("chat:read chat:edit", StandardCharsets.UTF_8);
        String s = "https://id.twitch.tv/oauth2/authorize?" +
                "client_id=" + Properties.get(Properties.Property.TWITCH_CLIENT_ID) + "&" +
                "response_type=token&" +
                "redirect_uri=http://localhost:9002/login&" +
                "scope=" + scopes;
        Utils.openWebpage(s);
    }

    public TwitchUser getUserByName(final String userName) {
        var res = this.get(Utils.toURL(BASE + "users?login=" + userName));
        log.debug(res);

        TwitchResponse<TwitchUser> body;
        try {
            body = new ObjectMapper().readValue(res, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
           log.error(e.getMessage());
           return null;
        }

        return body.getData().getFirst();
    }

    public List<SearchChannel> searchChannelsByName(String input) {
        input = URLEncoder.encode(input, StandardCharsets.UTF_8);
        var res = this.get(Utils.toURL(BASE + "search/channels?query=" + input + "&first=5"));
        TwitchResponse<SearchChannel> body;

        try{
            body = new ObjectMapper().readValue(res, new TypeReference<>() {});
        }catch (JsonProcessingException e){
            log.error(e.getMessage());
            return List.of();
        }
        return body.getData();
    }

    private String get(URL url){
        HttpRequest r;
        try {
            r = HttpRequest.newBuilder()
                    .uri(url.toURI())
                    .headers(makeHeaders().toArray(String[]::new))
                    .build();
        } catch (URISyntaxException e) {
           log.error(e.getMessage());
           return "";
        }

        HttpResponse<String> res;
        try {
            res = client.send(r, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
            return "";
        }

        return res.body();
    }

    private List<String> makeHeaders(){
        List<String> headers = new ArrayList<>();

        headers.add("Authorization");
        headers.add("Bearer " + Properties.get(Properties.Property.TWITCH_AUTHORIZATION));
        headers.add("Client-Id");
        headers.add(Properties.get(Properties.Property.TWITCH_CLIENT_ID));

        return headers;
    }

}
