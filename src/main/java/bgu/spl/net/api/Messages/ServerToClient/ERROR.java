package bgu.spl.net.api.Messages.ServerToClient;

import java.io.Serializable;

public class ERROR extends ServerMsg {
    public final short Opcode=11;
    private short MessageOpcode;

    public short getMessageOpcode() {
        return MessageOpcode;
    }

    public short getOpcode() {
        return Opcode;
    }

    public ERROR(short MsgOpCode){
        MessageOpcode=MsgOpCode;
    }
}
