package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
        bytes=new LinkedList<>();
    }

    public String getPassWord() {
        return PassWord;
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
        return "REGISTER " +UserName+" "+PassWord;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(!c.getName().equals(""))
            return new ERROR(Opcode);
        else
        {
            c.Register(UserName,PassWord);
            clients.put(c.getName(),c);
            return new ACK(Opcode);
        }
    }

}
