package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.NOTIFICATION;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PM extends Message {
    public final short Opcode=6;
    private String UserName;
    private String Content;
    
    public PM(){
        UserName="";
        Content="";
        bytes=new LinkedList<>();
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    public String getContent() {
        return Content;
    }

    public String getUserName() {
        return UserName;
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        Byte temp=nextByte;
        if(UserName.equals("") & temp.shortValue()==0) {
            UserName = GetStringFromBytes();
            return false;
        }
        if(Content.equals("") & temp.shortValue()==0){
            Content=GetStringFromBytes();
            return true;
        }
        bytes.add(nextByte);
        return false;
    }

    @Override
    public String toString() {
        return "PM "+UserName+" "+Content;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(!c.getIsConncted() | Content.contains("@"))
            return new ERROR(Opcode);
            if (!clients.containsKey(UserName))
                return new ERROR(Opcode);
            else {
                AllMessages.add(this);
                if (clients.get(UserName).getIsConncted())
                    connection.send(clients.get(UserName).getConnId(), new NOTIFICATION(true, c.getName(), Content));
                else
                    clients.get(UserName).addMsg(new NOTIFICATION(true,c.getName(),Content));
            }
            //if the recipient isn't registered than an error msg should be sent ,how to check it? done
            //all pm msg should be stored in the server with the post msg
        return new ACK(Opcode);

    }
}
