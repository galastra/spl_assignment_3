package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.nio.ByteBuffer;
import java.util.List;

public class PM extends Message {
    public final short Opcode=6;
    private String UserName;
    private String Content;
    
    public PM(){}

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
        ByteBuffer buffer=ByteBuffer.allocate(1);
        buffer.put(nextByte);
        if(UserName.equals("") & buffer.getChar()=='0')
            UserName=GetStringFromBytes();
        if(Content.equals("") & buffer.getChar()=='0'){
            Content=GetStringFromBytes();
            return true;
        }

        bytes.add(nextByte);
        return false;
    }

    @Override
    public ServerMsg process(Client c) {
        if(!c.getIsConncted() | Content.contains("@"))
            return new ERROR(Opcode);


            // TODO: 01-Jan-19
            //if the reciepient isnt registed than a error msg should be sent ,how to check it?
            //all pm msg should be stored in the server with the post msg
        return null;

    }
}
