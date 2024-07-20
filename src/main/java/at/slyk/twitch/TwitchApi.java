package at.slyk.twitch;

import at.slyk.PrefService;
import at.slyk.twitch.types.*;
import at.slyk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TwitchApi {

    private static final String BASE = "https://api.twitch.tv/helix/";
    private final HttpClient client = HttpClient.newHttpClient();


    public void login() {
        var scopes = URLEncoder.encode("chat:read chat:edit", StandardCharsets.UTF_8);
        String s = "https://id.twitch.tv/oauth2/authorize?" +
                "client_id=" + PrefService.getCLIENTID() + "&" +
                "response_type=token&" +
                "redirect_uri=http://localhost:9002/login&" +
                "scope=" + scopes;
        Utils.openWebpage(s);
    }

    public TwitchUser getMe() {
        var res = this.get(Utils.toURL(BASE + "users?"));
        log.debug(res);

        TwitchResponse<TwitchUser> body;
        try {
            body = new ObjectMapper().readValue(res, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }

        return body.getData().getFirst();
    }

    public TwitchUser getUserByName(final String userName) {
        var res = this.get(Utils.toURL(BASE + "users?login=" + userName));
        log.debug(res);

        TwitchResponse<TwitchUser> body;
        try {
            body = new ObjectMapper().readValue(res, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }

        return body.getData().getFirst();
    }

    public List<SearchChannel> searchChannelsByName(String input) {
        input = URLEncoder.encode(input, StandardCharsets.UTF_8);
        var res = this.get(Utils.toURL(BASE + "search/channels?query=" + input + "&first=5"));
        TwitchResponsePaginated<SearchChannel> body;

        try {
            body = new ObjectMapper().readValue(res, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return List.of();
        }
        return body.getData();
    }

    public StreamLinks getStreams(String channel) {
        var body = "https://twitch.tv/" + channel;
        var res = this.post(Utils.toURL("https://onlinetool.app/run/streamlink"), body);
        log.debug(res);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            StreamLinkFormResponse form = mapper.readValue(res, StreamLinkFormResponse.class);
            log.debug("{}", form.getOutput());
            var formRes = mapper.readValue(form.getOutput().getFirst(), new TypeReference<StreamLinks>() {});
            log.debug("{}", formRes);
            return formRes;

        } catch (JsonProcessingException e) {
            log.debug("{}", res);
            log.error("{}", e.getMessage());
            return null;
        }
    }

    private String post(URL url, String body) {
        Map<String, String> formData = Map.of("streamlink_url", body);
        HttpRequest r;
        try {
            r = HttpRequest.newBuilder()
                    .uri(url.toURI())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
                    .POST(HttpRequest.BodyPublishers.ofString(encodeFormData(formData)))
                    .build();
            log.debug("{}", r);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            return "";
        }

        return doRequest(r);
    }

    private String get(URL url) {
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

        return doRequest(r);
    }

    private String doRequest(HttpRequest r) {
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

    private List<String> makeHeaders() {
        List<String> headers = new ArrayList<>();

        headers.add("Authorization");
        headers.add("Bearer " + PrefService.getToken());
        headers.add("Client-Id");
        headers.add(PrefService.getCLIENTID());

        return headers;
    }

    private static String encodeFormData(Map<String, String> formData) {
        return formData.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

}
