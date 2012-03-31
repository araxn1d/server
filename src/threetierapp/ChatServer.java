/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp;

/**
 *
 * @author Жека
 */
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

public class ChatServer extends Server {

    WebSocket websocket;
    SelectChannelConnector connector;
    WebSocketHandler wsHandler;
    ResourceHandler rsHandler;
    ArrayList<ChatSocket> ChatClients;

    public ChatServer(int port) {
        connector = new SelectChannelConnector();
        connector.setPort(port);
        this.addConnector(connector);
        wsHandler = new MyWebSocketHandler();
        this.setHandler(wsHandler);
        rsHandler = new ResourceHandler();
        rsHandler.setDirectoriesListed(true);
        rsHandler.setResourceBase("src/test/webapp");
        wsHandler.setHandler(rsHandler);
        ChatClients = new ArrayList<ChatSocket>();
       
    }

    public ArrayList<ChatSocket> getChatClients() {
        return ChatClients;
    }
}

class MyWebSocketHandler extends WebSocketHandler {

    public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
        return new ChatSocket((ChatServer) this.getServer());
    }
}
