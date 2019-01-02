package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.USERLIST_ACK;

public class USERLIST extends Message {
    public final short Opcode=7;

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
        if(!c.getIsConncted())
            return new ERROR(Opcode);
        //should have access to NumOfUsers UserNameList to send a proper ACK msg
        // TODO: 31-Dec-18

        //return new USERLIST_ACK(Opcode)
        return null;
    }
}
