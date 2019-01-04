package bgu.spl.net.api;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BidiMsgProtocolImp implements BidiMessagingProtocol<Message> {
    private static ConcurrentHashMap<String,Client> clients;
    private static ConcurrentLinkedQueue<Message> AllMessage;
    private Connections<Message> connectionsImp;
    private boolean shouldTerminate;
    private Client client;
    private int connectionId;

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void process(Message message) {
        Message msg=message.process(client,connectionsImp,clients,AllMessage);
        connectionsImp.send(client.getConnId(),msg);
        //check for messages while the client was AFK
        if (!client.getAppending_msgs().isEmpty()) //might be true allegedly only after LOGIN
            for (Message appending_msg : client.getAppending_msgs())
                connectionsImp.send(client.getConnId(),appending_msg);
    }

    public boolean exist(String Name){
        return clients.containsKey(Name);
    }

    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connectionId=connectionId;
        connectionsImp = connections;
        this.client = new Client();
        this.client.setConnId(connectionId);
        shouldTerminate = false;
    }
}
