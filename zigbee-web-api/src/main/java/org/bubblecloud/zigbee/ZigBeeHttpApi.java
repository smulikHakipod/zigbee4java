package org.bubblecloud.zigbee;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yaronshani on 4/4/15.
 */
public abstract class ZigBeeHttpApi {

    public class ZigBeeHttpHandler extends AbstractHandler
    {
        public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {

            String httpPath = httpServletRequest.getRequestURI();
            if (httpPath == "HomeAutomation/DoorLock/Open") {
                ZigBeeHttpApi.this.callApi(httpPath.replace('/', '.'), httpServletRequest.getQueryString());
            }
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.getWriter().println("<h1>Hello World</h1>");
        }
    }

    public ZigBeeHttpApi() {
        Server server = new Server(8080);
        try {
            server.setHandler(new ZigBeeHttpHandler());
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract void callApi(String apiName, Object params);

}


