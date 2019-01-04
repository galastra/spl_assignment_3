package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.USERLISTACK;
import bgu.spl.net.api.bidi.Connections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class USERLIST extends Message {
    public final short Opcode=7;

    @Override
    public boolean decodeNextByte(byte nextByte) {
        return true;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(!c.getIsConncted())
            return new ERROR(Opcode);
        //should have access to NumOfUsers UserNameList to send a proper ACK msg, access granted
        ArrayList<String> client_Names=getUsersName(clients);
        return new USERLISTACK(Opcode,client_Names.size(),client_Names);
    }

    private ArrayList<String> getUsersName(ConcurrentHashMap<String,Client> clients){
        ArrayList<String> client_names =new ArrayList<String>(clients.keySet());
        return client_names;
    }
}
