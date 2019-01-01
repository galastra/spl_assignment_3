package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.util.List;

public class FOLLOW extends Message {
    public final short Opcode=4;
    private boolean Follow;//false is UNFOLLOW
    private int NumOfUsers;
    private List<String> UserNameList;
    
    public FOLLOW(){}

    @Override
    public int getOpCode() {
        return Opcode;
    }

    public int getNumOfUsers() {
        return NumOfUsers;
    }

    public List<String> getUserNameList() {
        return UserNameList;
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
