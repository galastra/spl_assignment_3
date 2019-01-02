package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.Message;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

public class ERROR extends Message {
    public final short Opcode=11;
    private short MessageOpcode;

    public short getMessageOpcode() {
        return MessageOpcode;
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
        //MessageOpcode
        buffer=ByteBuffer.allocate(2);
        buffer.putShort(MessageOpcode);
        for (byte temp:buffer.array()) {
            bytes.add(temp);
        }
        return ListToArray();
    }

    public short getOpcode() {
        return Opcode;
    }

    public ERROR(short MsgOpCode){
        MessageOpcode=MsgOpCode;
    }
}
