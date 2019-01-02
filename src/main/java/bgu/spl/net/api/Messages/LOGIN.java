package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class LOGIN extends Message{
public final short Opcode=2;
private String UserName;
private String PassWord;

public LOGIN(String UserName,String PassWord){
    this.PassWord=PassWord;
    this.UserName=UserName;
    bytes=new LinkedList<>();
}

public LOGIN(){}

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
        ByteBuffer buffer=ByteBuffer.allocate(1);
        buffer.put(nextByte);
        if(UserName.equals("") & buffer.getChar()=='\0')
        {
            UserName=GetStringFromBytes();
        }
        if(PassWord.equals("") & buffer.getChar()=='\0')
        {
            PassWord=GetStringFromBytes();
            return true;
        }
        bytes.add(nextByte);
        return false;

    }



    @Override
    public Message process(Client c) {
        //if name is "" then the person is not registered or Maybe he is already connected
        if(c.getName()=="" | c.getIsConncted() )
            return new ERROR(Opcode);
        else {
            c.Login();
            return new ACK(Opcode);
        }
    }
}
