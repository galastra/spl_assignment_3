package bgu.spl.net.api;

import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;
import bgu.spl.net.api.bidi.BidiMessagingProtocol;
import bgu.spl.net.api.bidi.Connections;

public class BidiMsgProtocolImp implements BidiMessagingProtocol<Message> {

    private boolean shouldTerminate=false;
    private Client client;

    public BidiMsgProtocolImp(){}

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    @Override
    public void process(Message message) {
        ServerMsg serverMsg=message.process(client);
        //how and to whom may i return the answer?
        // TODO: 31-Dec-18
    }

    @Override
    public void start(int connectionId, Connections<Message> connections) {

        //should i save connections?
        //should i register client
    }
}
