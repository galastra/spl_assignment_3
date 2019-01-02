package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class LOGIN extends Message{
public final short Opcode=2;
private String UserName;
private String PassWord;

public LOGIN(String UserName,String PassWord){
    this.PassWord=PassWord;
    this.UserName=UserName;
}

public LOGIN(){}

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    @Override
    public int getOpCode() {
        return Opcode;
    }

    @Override
    public void decode(List<Byte> list) {
        // TODO: 30-Dec-18  
    }

    @Override
    public ServerMsg process(Client c) {
        //if name is "" then the person is now registered or Maybe he is already connected
        if(c.getName().equals("") | c.getIsConncted() )
            return new ERROR(Opcode);
        else
            return new ACK<Object>()
    }
}
