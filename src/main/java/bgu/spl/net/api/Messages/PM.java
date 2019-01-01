package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class PM extends Message {
    public final short Opcode=6;
    private String UseName;
    private String Content;
    
    public PM(){}

    @Override
    public int getOpCode() {
        return Opcode;
    }

    public String getContent() {
        return Content;
    }

    public String getUseName() {
        return UseName;
    }

    @Override
    public void decode(List<Byte> list) {
        // TODO: 30-Dec-18  
        
    }

    @Override
    public ServerMsg process(Client c) {
        return super.process(c);
        // TODO: 31-Dec-18  
    }
}
