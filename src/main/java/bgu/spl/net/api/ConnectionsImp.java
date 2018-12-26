package bgu.spl.net.api;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.bidi.ConnectionHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class ConnectionsImp<T> implements Connections<T> {
    private ConcurrentHashMap<Integer,ConnectionHandler<T>> connectionHandlers;

    public ConnectionsImp(ConcurrentHashMap<Integer,ConnectionHandler<T>> _connectionHandlers){
        connectionHandlers = new ConcurrentHashMap<>();
        connectionHandlers.putAll(_connectionHandlers);
    }

    @Override
    public void send(int connId, T msg) {
        connectionHandlers.get(connId).send(msg);
    }

    @Override
    public void broadcast(T msg) {
        for(ConnectionHandler<T> connectionHandler : connectionHandlers.values()){
            connectionHandler.send(msg);
        }
    }

    @Override
    public void disconnect(int connId) {
        connectionHandlers.remove(connId);
    }
}
