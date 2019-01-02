package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ACK;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;

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
    public Message process(Client c) {
        if(c.getIsConncted())
            return new ACK(Opcode);
        // TODO: 02-Jan-19 should remove client from active users list in connections class 
        return new ERROR(Opcode);
    }
}
