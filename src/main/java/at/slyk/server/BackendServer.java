package at.slyk.server;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BackendServer {

    private Undertow server;

    public BackendServer() {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(BackendServer.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("backendServer")
                .addServlets(
                        LoginServlet.getUndertowInfo()
                );
        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        PathHandler path = null;
        try {
            // redirect url from twitch to parse fragment
            path = Handlers.path(Handlers.redirect("/")).addPrefixPath("/", manager.start());
            // site that handles
            path.addPrefixPath("/data", new BlockingHandler(new DataHandler()));
            path.addPrefixPath("/done", new DoneHandler("Login successful! - You can close this window now!"));
            path.addPrefixPath("/error", new DoneHandler("Login failed! - Either you declined or other another Error occurred!"));
        } catch (ServletException e) {
            log.error(e.getMessage());
            this.server = null;
        }

        this.server = Undertow.builder()
                .addHttpListener(9002, "localhost")
                .setHandler(path)
                .build();
    }

    public void start() {
        server.start();
    }

}
