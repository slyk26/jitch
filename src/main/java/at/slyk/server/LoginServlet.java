package at.slyk.server;

import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.ServletInfo;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginServlet extends HttpServlet {

    // parse fragment to object and forward it with POST and redirect
    private static final String JS = """
            <script>
                var div = document.getElementById("info");
                let frag = document.location.hash

                if(frag == null)
                    frag = "";
     
                frag = decodeURIComponent(frag.substring(1));

                div.innerHTML = frag;
                const body = {};

                for(let param of frag.split("&")){
                    const p = param.split("=");
                    body[p[0]] = p[1];
                }

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "http://localhost:9002/data", true);
                xhr.setRequestHeader("Content-Type", "application/json")
                xhr.send(JSON.stringify(body));

                xhr.onreadystatechange = function() {
                    if(this.readyState != 4) return;

                    if(this.status == 200) {
                        window.location.replace("http://localhost:9002/done");
                    } else {
                        window.location.replace("http://localhost:9002/error");
                    }
                };

            </script>
            """;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (PrintWriter pw = resp.getWriter()) {
            pw.println("<html><body><div id='info'></div>" + JS + "</body></html>");
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    public static ServletInfo getUndertowInfo() {
        return Servlets.servlet("login", LoginServlet.class).addMapping("/login");
    }
}
