package service;


import Interceptor.SimpleInterceptor;
import Repository.OrderRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private Logger logger = LoggerFactory.getLogger(WorkerSocket.class);
    @EJB
    OrderRepository repo;
    @OnOpen
    public void onOpen(Session session) throws IOException {
        logger.info(format("%s started working.", session.getId()));
        workers.add(session);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders",repo.findAll());
        session.getBasicRemote().sendText(jsonObject.toString(2));
    }

    @OnError
    public void onError(Throwable error) {
        logger.error(error.getMessage());
    }
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        logger.info(format("%s stopped working.", session.getId()));
        workers.remove(session);

    }
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        logger.info("#Worker order update: " + message);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orders",repo.findAll());
        for (Session worker : workers) {
            if (!worker.getId().equals(session.getId()))
                worker.getBasicRemote().sendObject(jsonObject.toString(2));

        }
    }

}