package bgu.spl.net.api;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

public class BidiMsgProtocolImp implements BidiMessagingProtocol<Message> {
    private ConnectionsImp<Message> connectionsImp;
    private boolean shouldTerminate=false;
    private Client client;
    private int connectionId;

    public BidiMsgProtocolImp(Client c){
        this.client=c;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void process(Message message) {
        Message msg=message.process(client);
        //how and to whom may i 'return' the answer?
        // TODO: 31-Dec-18
    }

    @Override
    public void start(int connectionId, Connections<Message> connections) {
        this.connectionId=connectionId;
        //connectionsImp=connections;
        //should i save connections?
        //should i register client
    }
}
