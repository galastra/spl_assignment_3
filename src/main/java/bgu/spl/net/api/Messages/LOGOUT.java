package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class LOGOUT extends Message {
    public final short Opcode=3;

    public LOGOUT(){}

    @Override
    public boolean decodeNextByte(byte nextByte) {
        return true;
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public ServerMsg process(Client c) {
        if(c.getIsConncted())
            return new ACK(Opcode);
        return new ERROR(Opcode);
    }
}
