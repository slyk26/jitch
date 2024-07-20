package at.slyk.server;

import at.slyk.Main;
import at.slyk.PrefService;
import at.slyk.common.User;
import at.slyk.twitch.TwitchApi;
import at.slyk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange e) {
        if (!"POST".equals(e.getRequestMethod().toString())) {
            e.setStatusCode(405);
            return;
        }

        var body = Utils.inputStreamToString(e.getInputStream());

        if (body.isBlank() || body.isEmpty()) {
            log.info("User denied access on Twitch prompt");
            e.setStatusCode(400);
            return;
        }

        AuthorizationResponse res = null;
        try {
            res = new ObjectMapper().readValue(body, AuthorizationResponse.class);
            PrefService.setToken(res.getAccessToken());
            var name = new TwitchApi().getMe().getDisplayName();

            Main.user.onNext(new User(name, res.getAccessToken()));

        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
        log.debug("OK! - {}", res);
    }
}
