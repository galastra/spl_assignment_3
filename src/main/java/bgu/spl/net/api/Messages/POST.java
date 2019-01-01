package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class POST extends Message {
    public final short Opcode=5;

    private String Content;
    private List<String> UsersToNotify;//the one who's mentioned by @UserName

    public POST(){}

    @Override
    public void decode(List<Byte> list) {
        // TODO: 30-Dec-18  
    }

    public String getContent() {
        return Content;
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

    public List<String> getUsersToNotify() {
        return UsersToNotify;
    }
}
