package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.FollowACK;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FOLLOW extends Message {
    public final short Opcode=4;
    private boolean Follow;//true=Follow false=Unfollow
    private short NumOfUsers;
    private List<String> UserNameList;
    private boolean FirstByte=true;
    
    public FOLLOW(){
        Follow=false;
        NumOfUsers=0;
        UserNameList=new LinkedList<>();
        bytes=new LinkedList<>();
    }

    @Override
    public short getOpCode() {
        return Opcode;
    }

    public int getNumOfUsers() {
        return NumOfUsers;
    }

    public List<String> getUserNameList() {
        return UserNameList;
    }

    @Override
    public boolean decodeNextByte(byte nextByte) {
        if(FirstByte)//wil happen first
        {
            ByteBuffer bb=ByteBuffer.allocate(1);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(nextByte);
            char a=bb.getChar();
            if(a=='0')
                Follow=true;
            FirstByte=false;
        }

        if(NumOfUsers==0 & bytes.size()==2)//check if i have the info to determine NumOfUsers
        {
            ByteBuffer bb=ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(bytes.remove(0));
            bb.put(bytes.remove(0));
            NumOfUsers=bb.getShort();
            NumOfUsers++;//so we wont reach to NumofUsers==0 and bytes.size==2 and do again this operation
        }

        if(NumOfUsers==0)//insufficient info to determine NumOfUsers
            bytes.add(nextByte);
        else//here we infer the User List
        {
            if(NumOfUsers==1) {
                Integer A=UserNameList.size();
                NumOfUsers=A.shortValue();
                return true;
            }
            ByteBuffer bb=ByteBuffer.allocate(1);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(nextByte);
            if(bb.getChar()=='\0')
            {
                NumOfUsers--;
                UserNameList.add(GetStringFromBytes());
            }
            else
                bytes.add(nextByte);
        }
        return false;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(NumOfUsers==0)
            return new ERROR(Opcode);
        else
        {
            if(Follow)
            {
                LinkedList<String> succesfullFollow=new LinkedList<>();

                for (String Name:UserNameList) {
                    if(clients.containsKey(Name) && c.AddFollower(Name))
                        succesfullFollow.add(Name);
                }
                if(succesfullFollow.size()==0)
                    return new ERROR(Opcode);
                else
                    return new FollowACK(Opcode,succesfullFollow.size(),succesfullFollow);
            }
            else
            {
                LinkedList<String> succesfullUnFollow=new LinkedList<>();

                for (String Name:UserNameList) {
                    if(clients.containsKey(Name) && c.RemoveFollower(Name))
                        succesfullUnFollow.add(Name);
                }
                if(succesfullUnFollow.size()==0)
                    return new ERROR(Opcode);
                else
                    return new FollowACK(Opcode,succesfullUnFollow.size(),succesfullUnFollow);
            }
        }
    }
}
