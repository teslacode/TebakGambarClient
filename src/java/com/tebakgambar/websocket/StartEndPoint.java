package com.tebakgambar.websocket;

import com.tebakgambar.model.Player;
import com.tebakgambar.model.Room;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Web Socket Start Game
 *
 * @author Ade Fruandta
 */
@ServerEndpoint(value = "/startEndPoint", configurator = GetHttpSessionConfigurator.class)
public class StartEndPoint {

    private static final Set<Session> peers = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("INVOKED Open Socket Start Game");

        //Get Session Attribute
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        Room room = (Room) this.httpSession.getAttribute("roomSession");
        Player player = (Player) this.httpSession.getAttribute("playerSession");
        //Get Session Attribute

        //Setting User Properties Socket
        Map map = session.getUserProperties();
        map.put("room", room);
        map.put("player", player);
        //Setting User Properties Socket

        peers.add(session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("INVOKED Close Socket Start Game");
        
        //Update room yang sedang dikunjungi
        Map map = session.getUserProperties();
        Room room = (Room) map.get("room");
        Player player = (Player) map.get("player");
        for (Session otherSession : peers) {
            Map mapOtherSession = otherSession.getUserProperties();
            Room roomOtherSession = (Room) mapOtherSession.get("room");
            
            //Keluar dari room
            if (room.equals(roomOtherSession) && !session.equals(otherSession)) {
                roomOtherSession.removePlayer(player);
                otherSession.getBasicRemote().sendText("{\"module\": \"outGame\", \"user\": \"" + player.getUser().getUserName() + "\"}");
                break;
            }
            //Keluar dari room

        }
        //Update room yang sedang dikunjungi
        
        peers.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("INVOKED Message Socket Start Game: " + message);

        //Update room yang sedang dikunjungi
        Map map = session.getUserProperties();
        Room room = (Room) map.get("room");
        for (Session otherSession : peers) {
            Map mapOtherSession = otherSession.getUserProperties();
            Room roomOtherSession = (Room) mapOtherSession.get("room");

            //Masuk & Update room
            if (room.equals(roomOtherSession) && !session.equals(otherSession)) {
                otherSession.getBasicRemote().sendText(message);
            }
            //Masuk & Update room

        }
        //Update room yang sedang dikunjungi

    }

}
