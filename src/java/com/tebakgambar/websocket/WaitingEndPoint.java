package com.tebakgambar.websocket;

import com.tebakgambar.model.Player;
import com.tebakgambar.model.User;
import com.tebakgambar.model.Room;
import com.tebakgambar.service.RfTema;
import com.tebakgambar.service.SoalService;
import com.tebakgambar.service.SoalService_Service;
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
 * Web Socket Waiting Room
 * 
 * @author Ade Fruandta
 */
@ServerEndpoint(value = "/waitingEndPoint", configurator = GetHttpSessionConfigurator.class)
public class WaitingEndPoint {

    private final SoalService_Service portSoal = new SoalService_Service();
    private final SoalService soalService = this.portSoal.getSoalServicePort();
    
    private static final Set<Session> peers = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        System.out.println("INVOKED Open Socket Waiting Room: Session: " + session.getId());

        //Get Session Attribute
        this.httpSession = (HttpSession) config.getUserProperties()
                .get(HttpSession.class.getName());
        User userTicket = (User) this.httpSession.getAttribute("userTicket");
        userTicket.setId(session.getId());
        RfTema rfTema = (RfTema) this.httpSession.getAttribute("rfTemaSession");
        Integer maxPlayer = (Integer) this.httpSession.getAttribute("maxPlayerSession");
        //Get Session Attribute

        //Setting room
        Player player = new Player(userTicket);
        Room room = new Room(session.getId(), rfTema, maxPlayer);
        room.addPlayer(player);
        Boolean roomReady = true;
        for (Session otherSession : peers) {
            Map mapOtherSession = otherSession.getUserProperties();
            Room otherRoom = (Room) mapOtherSession.get("room");

            //Jika room sudah ada dan kurang dari Max player, maka masuk ke room tersebut
            if (otherRoom.getMaxPlayer() == maxPlayer && otherRoom.getPlayers().size() < maxPlayer && otherRoom.getRfTema().getId() == room.getRfTema().getId()) {
                otherRoom.addPlayer(player);
                room = otherRoom;
                roomReady = false;
                System.out.println("INVOKED Open Socket Waiting Room: getRoom: " + room.toString());
                break;
            }
            //Jika room sudah ada dan kurang dari Max player, maka masuk ke room tersebut

        }
        if(roomReady){
            room.setSoals(this.soalService.getSoal(rfTema.getId()));
        }
        //Setting room
        
        //Setting User Properties Socket
        Map map = session.getUserProperties();
        map.put("userTicket", userTicket);
        map.put("room", room);
        map.put("player", player);
        //Setting User Properties Socket
        
        //Set session room
        this.httpSession.setAttribute("roomSession", room);
        this.httpSession.setAttribute("playerSession", player);
        //Set session room
        
        peers.add(session);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("INVOKED Close Socket Waiting Room");

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
                otherSession.getBasicRemote().sendText("{\"module\": \"outRoom\", \"user\": \"" + player.getUser().getUserName() + "\"}");
                break;
            }
            //Keluar dari room

        }
        //Update room yang sedang dikunjungi

        peers.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("INVOKED Message Socket Waiting Room: " + message);

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
