package bgu.spl.net.api.Messages.ServerToClient;

import java.io.Serializable;

public class ACK extends ServerMsg {
    public final short Opcode=10;
    protected short MessageOpcode;


    public ACK(short MsgOpCode){
        MessageOpcode=MsgOpCode;
    }

    public short getOpcode() {
        return Opcode;
    }

    public short getMessageOpcode() {
        return MessageOpcode;
    }

}
