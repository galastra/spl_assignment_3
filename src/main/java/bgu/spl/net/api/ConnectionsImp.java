package bgu.spl.net.api;
import bgu.spl.net.api.bidi.Connections;
import bgu.spl.net.srv.ConnectionHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class ConnectionsImp<T> implements Connections<T> {
    private ConcurrentHashMap<Integer,ConnectionHandler<T>> connectionHandlers;
    private AtomicInteger connId;

    public ConnectionsImp(){
        connectionHandlers = new ConcurrentHashMap<>();
        connId = new AtomicInteger(0);
    }

    public ConnectionsImp(ConcurrentHashMap<Integer,ConnectionHandler<T>> _connectionHandlers){
        connectionHandlers = new ConcurrentHashMap<>();
        connectionHandlers.putAll(_connectionHandlers);
    }

    @Override
    public void send(int connId, T msg) {
        //TODO: what todo when there is no connection with this connId, should not be possible
        //if the user is logged in then display the message
        //else keep message until user connect again and display on connect
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
        try {
            connectionHandlers.get(connId).close();
            connectionHandlers.remove(connId);
        }
        catch (Exception e){
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public int register(ConnectionHandler<T> connectionHandler){
        connectionHandlers.put(connId.get(),connectionHandler);
        return connId.getAndIncrement();
    }

    //should we enter a register function?
}

