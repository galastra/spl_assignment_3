package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;

import java.nio.ByteBuffer;

public class REGISTER extends Message {
    public final short Opcode=1;
    private String UserName;
    private String PassWord;

    public REGISTER(String UserName,String PassWord){
        this.PassWord=PassWord;
        this.UserName=UserName;
    }

    public REGISTER(){
        UserName="";
        PassWord="";
    }

    public String getPassWord() {
        return PassWord;
    }

    public String getUserName() {
        return UserName;
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        ByteBuffer buffer=ByteBuffer.allocate(1);
        buffer.put(nextByte);
        if(UserName.equals("") & buffer.getChar()=='\0')
            UserName=GetStringFromBytes();
        if(PassWord.equals("") & buffer.getChar()=='\0')
        {
            PassWord=GetStringFromBytes();
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
        if(!c.getName().equals(""))
            return new ERROR(Opcode);
        else
        {
            c.Register(UserName,PassWord);
            return new ACK(Opcode);
        }
    }

}
