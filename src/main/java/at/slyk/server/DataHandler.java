package at.slyk.server;

import at.slyk.Properties;
import at.slyk.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataHandler implements HttpHandler {
    @Override
    public void handleRequest(HttpServerExchange e) {
        if (!"POST".equals(e.getRequestMethod().toString())) {
            e.setStatusCode(405);
            return;
        }

        var body = Utils.inputStreamToString(e.getInputStream());

        if(body.isBlank() || body.isEmpty()) {
            log.info("User denied access on Twitch prompt");
            e.setStatusCode(400);
            return;
        }

        AuthorizationResponse res = null;
        try {
            res = new ObjectMapper().readValue(body, AuthorizationResponse.class);
            Properties.set(Properties.Property.TWITCH_AUTHORIZATION, res.getAccessToken());
        }catch (JsonProcessingException ex){
            log.error(ex);
        }
        log.debug("OK! - {}", res);
    }
}
