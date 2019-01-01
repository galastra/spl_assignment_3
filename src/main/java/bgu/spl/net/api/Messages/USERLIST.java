package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class USERLIST extends Message {
    public final short Opcode=7;

    @Override
    public void decode(List<Byte> list) {
        // TODO: 30-Dec-18
    }

    @Override
    public int getOpCode() {
        return Opcode;
    }

    @Override
    public ServerMsg process(Client c) {
        return super.process(c);
        // TODO: 31-Dec-18
    }
}
