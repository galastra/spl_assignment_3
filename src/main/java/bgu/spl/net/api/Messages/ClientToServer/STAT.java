package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.STATACK;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class STAT extends Message {
    public final short Opcode=8;
    private String UserName;

    public STAT(){
        UserName="";
        bytes=new LinkedList<>();
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        Byte temp=nextByte;
        if(UserName.equals("") & temp.shortValue()==0)
        {
            UserName=GetStringFromBytes();
            return true;
        }
        bytes.add(nextByte);
        return false;
    }

    @Override
    public String toString() {
        return "STAT "+UserName;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(!c.getIsConncted() | !clients.containsKey(UserName))
            return new ERROR(Opcode);
        return new STATACK(Opcode,clients.get(UserName).getNumPosts(),clients.get(UserName).getFollowers().size(),clients.get(UserName).getFollowing().size());
        // should get access to the given user name to infer STATACK needed values, access granted
        //return new STATACK(Opcode), returned
    }
}
