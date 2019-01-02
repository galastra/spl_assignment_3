package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;

import java.nio.ByteBuffer;

public class STAT extends Message {
    public final short Opcode=8;
    private String UserName;

    public STAT(){
        UserName="";
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        ByteBuffer buffer=ByteBuffer.allocate(1);
        buffer.put(nextByte);
        if(UserName.equals("") & buffer.getChar()=='\0')
        {
            UserName=GetStringFromBytes();
            return true;
        }
        bytes.add(nextByte);
        return false;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public Message process(Client c) {
        if(!c.getIsConncted())
            return new ERROR(Opcode);
        // TODO: 02-Jan-19   if UserName is not registered an error msg should be returned
        // should get access to the given user name to infer STATACK needed values
        //return new STATACK(Opcode)
        return null;//temporary
    }

}
