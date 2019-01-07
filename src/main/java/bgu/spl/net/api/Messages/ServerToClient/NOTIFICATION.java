package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.Message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class NOTIFICATION extends Message {
    public final short Opcode=9;
    private boolean IsPM;//false=Public
    private String PostingUser;
    private String Content;

    public short getOpcode() {
        return Opcode;
    }

    @Override
    public byte[] encode() {
        bytes=new LinkedList<>();
        ByteBuffer buffer;
        //Opcode
        byte[] A=encodeShort(Opcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //IsPM
        buffer=ByteBuffer.allocate(1);
        if(IsPM)
            bytes.add((byte)'\0');
        else
            bytes.add((byte)1);

        //Posting User
        buffer= StandardCharsets.UTF_8.encode(PostingUser);
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }

        // \0
        bytes.add((byte)'\0');
        /*
        buffer=ByteBuffer.allocate(1);
        buffer.putChar('\0');
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        */
        //Content
        buffer=StandardCharsets.UTF_8.encode(Content);
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        // \0
        /*
        buffer=ByteBuffer.allocate(1);
        buffer.putChar('\0');
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        */
        bytes.add((byte)'\0');
        return ListToArray();
    }

    public NOTIFICATION(boolean IsPM,String Name,String Content){
        PostingUser=Name;
        this.IsPM=IsPM;
        this.Content=Content;
        bytes=new LinkedList<>();
    }

}
