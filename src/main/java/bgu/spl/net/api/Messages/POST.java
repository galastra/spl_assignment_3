package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class POST extends Message {
    public final short Opcode=5;

    private String Content;
    private List<String> UsersToNotify;//the one who's mentioned by @UserName

    public POST(){
        Content="";
        UsersToNotify=new LinkedList<>();
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
        ByteBuffer buffer=ByteBuffer.allocate(1);
        buffer.put(nextByte);
        if(Content.equals("") & buffer.getChar()=='\0')
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
            if(temp.startsWith("@"))
                UsersToNotify.add(temp.substring(1));
        }
    }

    @Override
    public Message process(Client c) {
        if(!c.getIsConncted())
            return new ERROR(Opcode);
        // TODO: 02-Jan-19 should inform and initiate the sending of the message to anyone in UserToNotify
        return new ACK(Opcode);
    }

    public List<String> getUsersToNotify() {
        return UsersToNotify;
    }
}
