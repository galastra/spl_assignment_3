package bgu.spl.net.api.Messages.ServerToClient;

import bgu.spl.net.api.Messages.FOLLOW;

import java.util.LinkedList;

public class FollowACK extends ACK {
    private int NumOfUsers;
    private LinkedList<String> UserNameList;

    public FollowACK(short Opcode, int NumOfUsers, LinkedList<String> UserNameList)
    {
        super(Opcode);
        this.NumOfUsers=NumOfUsers;
        this.UserNameList=UserNameList;
    }

}
