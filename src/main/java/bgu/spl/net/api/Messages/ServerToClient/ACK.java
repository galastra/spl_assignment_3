package bgu.spl.net.api.Messages.ServerToClient;

import java.io.Serializable;

public class ACK<T> extends ServerMsg {
    public final short Opcode=10;
    private short MessageOpcode;
    private T Optional;

    public ACK(short MsgOpCode){
        MessageOpcode=MsgOpCode;
    }

    public short getOpcode() {
        return Opcode;
    }

    public short getMessageOpcode() {
        return MessageOpcode;
    }

    public T getOptional() {
        return Optional;
    }

}
