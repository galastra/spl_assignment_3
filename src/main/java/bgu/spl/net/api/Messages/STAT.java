package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class STAT extends Message {
    public final short Opcode=8;
    private String UserName;

    public STAT(){}

    //@Override
    public void decode(List<Byte> list) {
        // TODO: 30-Dec-18  
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    @Override
    public ServerMsg process(Client c) {
        return super.process(c);
        // TODO: 31-Dec-18
    }

}
