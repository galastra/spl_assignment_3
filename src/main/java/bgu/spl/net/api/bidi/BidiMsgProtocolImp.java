package bgu.spl.net.api.bidi;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BidiMsgProtocolImp implements BidiMessagingProtocol<Message> {
    private static ConcurrentHashMap<String, Client> clients;
    private static ConcurrentLinkedQueue<Message> AllMessage;
    private Connections<Message> connectionsImp;
    private boolean shouldTerminate;
    private Client client;
    //private int connectionId;

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void process(Message message) {
        Message msg2send=message.process(client,connectionsImp,clients,AllMessage);
        connectionsImp.send(client.getConnId(),msg2send);
        //check for messages while the client was AFK
        if (!client.getAppending_msgs().isEmpty()) { //might be true allegedly only after LOGIN
            for (Message appending_msg : client.getAppending_msgs())
                connectionsImp.send(client.getConnId(), appending_msg);
            client.clearPendingMsgs();
            clients.get(client.getName()).clearPendingMsgs();
        }
        if (msg2send instanceof ACK && ((ACK)msg2send).getMessageOpcode()==3) {
            shouldTerminate = true;
            connectionsImp.disconnect(client.getConnId());
        }
    }

    @Override
    public void start(int connectionId, Connections<Message> connections) {
        System.out.println("protocol started");
        if (clients == null)
            clients = new ConcurrentHashMap<>();
        if (AllMessage == null)
            AllMessage = new ConcurrentLinkedQueue<>();
        //this.connectionId=connectionId;
        connectionsImp = connections;
        this.client = new Client();
        this.client.setConnId(connectionId);
        shouldTerminate = false;
    }
}
