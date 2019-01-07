package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.NOTIFICATION;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class POST extends Message {
    public final short Opcode=5;
    private String Content;
    private List<String> UsersToNotify;//the one who's mentioned by @UserName

    public POST(){
        Content="";
        UsersToNotify=new LinkedList<>();
        bytes=new LinkedList<>();
    }


    public String getContent() {
        return Content;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        Byte temp=nextByte;
        if(Content.equals("") & temp.shortValue()==0)
        {
            Content=GetStringFromBytes();
            setUsersToNotify();
            return true;
        }
        bytes.add(nextByte);
        return false;
    }

    private void setUsersToNotify(){
        String[] arr=Content.split(" ");
        for (String temp:arr) {
            if (temp.startsWith("@"))
                UsersToNotify.add(temp.substring(1));
        }
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(!c.getIsConncted())
            return new ERROR(Opcode);
        for (String Name:UsersToNotify) {
            if(clients.containsKey(Name)) {
                AllMessages.add(this);
                if (clients.get(Name).getIsConncted())
                    connection.send(clients.get(Name).getConnId(), new NOTIFICATION(false, c.getName(), Content));
                else
                    clients.get(Name).addMsg(new NOTIFICATION(false,c.getName(),Content));
            }
        }
        for(String Name:c.getFollowers()){
            if(clients.containsKey(Name) & !UsersToNotify.contains(Name)) {
                AllMessages.add(this);
                if (clients.get(Name).getIsConncted())
                    connection.send(clients.get(Name).getConnId(), new NOTIFICATION(false, c.getName(), Content));
                else
                    clients.get(Name).addMsg(new NOTIFICATION(false,c.getName(),Content));
            }
        }
        c.AddToNumPost();
        clients.get(c.getName()).AddToNumPost();
        return new ACK(Opcode);
    }

    public List<String> getUsersToNotify() {
        return UsersToNotify;
    }

    @Override
    public String toString() {
        return "POST "+Content;
    }
}
