package service;


import Interceptor.SimpleInterceptor;
import Repository.OrderRepository;
import org.json.JSONObject;

import javax.ejb.EJB;
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
@ServerEndpoint(value = "/worker")
@Interceptors(SimpleInterceptor.class)
public class WorkerSocket {
    static Set<Session> workers = Collections.synchronizedSet(new HashSet<Session>());
    @EJB
    OrderRepository repo;
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println(format("%s started working.", session.getId()));
        workers.add(session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders",repo.findAll());
        session.getBasicRemote().sendText(jsonObject.toString(2));
    }

    @OnError
    public void onError(Throwable error) {
        /// put model back to pool, log erros
    }
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(format("%s stopped working.", session.getId()));
        workers.remove(session);

    }
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("#Worker order update: " + message);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders",repo.findAll());
        for (Session worker : workers) {
            if (!worker.getId().equals(session.getId()))
                worker.getBasicRemote().sendObject(jsonObject.toString(2));

        }
    }

}