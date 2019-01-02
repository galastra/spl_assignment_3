package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class NOTIFICATION extends Message {
    public final short Opcode=9;
    private boolean IsPM;//false=Public
    private String PostringUser;
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
            buffer.putChar('0');
        else
            buffer.putChar('1');
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        //Posting User
        buffer= StandardCharsets.UTF_8.encode(PostringUser);
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        buffer.reset();
        // \0
        buffer=ByteBuffer.allocate(1);
        buffer.putChar('\0');
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        buffer.reset();
        //Content
        buffer=StandardCharsets.UTF_8.encode(Content);
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        buffer.reset();
        // \0
        buffer=ByteBuffer.allocate(1);
        buffer.putChar('\0');
        A=buffer.array();
        for (byte temp:A) {
            bytes.add(temp);
        }
        return ListToArray();
    }

    public NOTIFICATION(){
        PostringUser="";
        Content="";
    }

}
