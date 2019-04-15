package service.chat;


import Interceptor.SimpleInterceptor;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.interceptor.Interceptors;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@ApplicationScoped
@ServerEndpoint(value = "/chat")
@Interceptors(SimpleInterceptor.class)
public class ChatServer {
    static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(format("%s joined the chat room.", session.getId()));
        peers.add(session);
        JSONObject message = new JSONObject();
        message.put("sender","Server");
        message.put("content",format("%s joined the chat room", (String) session.getId()));
        message.put("received",getDate());
        //notify peers about joining the chat room
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(message.toString());
        }
    }
    private String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date_unparsed = new Date();
        return dateFormat.format(date_unparsed);
    }
    @OnError
    public void onError(Throwable error) {
        /// put model back to pool, log erros
    }
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(format("%s left the chat room.", session.getId()));
        peers.remove(session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sender","Server");
        jsonObject.put("content",format("%s left the chat room", (String) session.getId()));
        jsonObject.put("received",getDate());
        //notify peers about leaving the chat room
        for (Session peer : peers) {
            peer.getBasicRemote().sendText(jsonObject.toString(2));
        }
    }
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println(message);
        JSONObject jsonObject = new JSONObject(message);
        jsonObject.put("received",getDate());
        for (Session peer : peers) {
                peer.getBasicRemote().sendObject(jsonObject.toString(2));

        }
    }

}