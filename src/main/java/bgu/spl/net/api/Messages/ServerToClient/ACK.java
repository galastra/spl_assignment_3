package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.Message;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ACK extends Message {
    public final short Opcode=10;
    protected short MessageOpcode;


    public ACK(short MsgOpCode){
        MessageOpcode=MsgOpCode;
    }

    public short getOpcode() {
        return Opcode;
    }

    @Override
    public byte[] encode() {
        //Opcode
        bytes=new LinkedList<>();
        ByteBuffer buffer=ByteBuffer.allocate(2);
        buffer.putShort(Opcode);
        for (byte temp:buffer.array()) {
            bytes.add(temp);
        }
        //buffer.reset();
        //MessageOpcode
        buffer=ByteBuffer.allocate(2);
        buffer.putShort(MessageOpcode);
        for (byte temp:buffer.array()) {
            bytes.add(temp);
        }
        return ListToArray();
    }

    public short getMessageOpcode() {
        return MessageOpcode;
    }

}
