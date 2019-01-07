package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.ConnectionsImp;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LOGIN extends Message {
public final short Opcode=2;
private String UserName;
private String PassWord;

public LOGIN(){
    UserName="";
    PassWord="";
    bytes=new LinkedList<>();
}

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        Byte temp=nextByte;
        if(UserName.equals("") & temp.shortValue()==0)
        {
            UserName=GetStringFromBytes();
            return false;
        }
        if(PassWord.equals("") & temp.shortValue()==0)
        {
            PassWord=GetStringFromBytes();
            return true;
        }
        bytes.add(nextByte);
        return false;

    }

    @Override
    public String toString() {
        return "LOGIN "+UserName+" "+ PassWord;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        //if name is "" then the person is not registered or Maybe he is already connected
        if (clients.containsKey(UserName) && clients.get(UserName).getPassword().equals(PassWord) && !clients.get(UserName).getIsConncted()) {
            int connId=c.getConnId();
            c.setClient(clients.get(UserName));
            clients.get(UserName).Login();
            clients.get(UserName).setConnId(connId);
            c.setConnId(connId);
            c.Login();
            return new ACK(Opcode);
        }
        return new ERROR(Opcode);
    }
}
