package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.bidi.Connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LOGOUT extends Message {
    public final short Opcode=3;

    public LOGOUT(){}

    @Override
    public boolean decodeNextByte(byte nextByte) {
        return true;
    }

    @Override
    public String toString() {
        return "LOGOUT";
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(c.getIsConncted()) {
            c.LogOut();
            clients.get(c.getName()).LogOut();
            return new ACK(Opcode);
        }
        return new ERROR(Opcode);
    }
}
