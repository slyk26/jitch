package at.slyk.server;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoneHandler implements HttpHandler {

    private final String msg;

    public DoneHandler(String msg) {
        this.msg = msg;
    }

    @Override
    public void handleRequest(HttpServerExchange e) {
        e.getResponseSender().send(msg);
    }
}
