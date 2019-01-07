package bgu.spl.net.api.Messages.ClientToServer;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.Message;
import bgu.spl.net.api.Messages.ServerToClient.ERROR;
import bgu.spl.net.api.Messages.ServerToClient.FollowACK;
import bgu.spl.net.api.bidi.Connections;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
            Byte temp=nextByte;
            if(temp.shortValue()==0)
                Follow=true;
            FirstByte=false;
            return false;
        }

        if(NumOfUsers==0 & bytes.size()==2)//check if i have the info to determine NumOfUsers
        {
            ByteBuffer bb=ByteBuffer.allocate(2);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.put(bytes.remove(0));
            bb.put(bytes.remove(0));
            bb.flip();
            NumOfUsers=bb.getShort();
            NumOfUsers++;//so we wont reach to NumofUsers==0 and bytes.size==2 and do again this operation
        }

        if(NumOfUsers==0)//insufficient info to determine NumOfUsers
            bytes.add(nextByte);
        else//here we infer the User List
        {

            /*
            ByteBuffer bb=ByteBuffer.allocate(1);
            bb.order(ByteOrder.BIG_ENDIAN);
            bb.put(nextByte);
            bb.flip();
            */
            Byte temp=nextByte;
            if(temp.shortValue()==0)
            {
                NumOfUsers--;
                UserNameList.add(GetStringFromBytes());
                if(NumOfUsers==1) {
                    Integer A=UserNameList.size();
                    NumOfUsers=A.shortValue();
                    return true;
                }
            }
            else
                bytes.add(nextByte);
        }
        return false;
    }

    @Override
    public Message process(Client c, Connections<Message> connection, ConcurrentHashMap<String,Client> clients, ConcurrentLinkedQueue<Message> AllMessages) {
        if(NumOfUsers==0 || !c.getIsConncted())
            return new ERROR(Opcode);
        else
        {
            if(Follow)
            {
                LinkedList<String> succesfullFollow=new LinkedList<>();

                for (String Name:UserNameList) {
                    if(clients.containsKey(Name) && c.AddFollowing(Name)) {
                        succesfullFollow.add(Name);
                        clients.get(Name).AddFollower(c.getName());
                    }
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
                    if(clients.containsKey(Name) && c.removeFollowing(Name)) {
                        succesfullUnFollow.add(Name);
                        clients.get(Name).RemoveFollower(Name);
                    }
                }
                if(succesfullUnFollow.size()==0)
                    return new ERROR(Opcode);
                else
                    return new FollowACK(Opcode,succesfullUnFollow.size(),succesfullUnFollow);
            }
        }
    }

    @Override
    public byte[] encode() {
        char ifFollow;
        if (Follow)
            ifFollow = '0';
        else
            ifFollow = '1';
        String str = getOpCode() + ifFollow + " " + NumOfUsers + " " + Arrays.toString(UserNameList.toArray());
        return str.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        char ifFollow;
        if (Follow)
            ifFollow = '0';
        else
            ifFollow = '1';
        return "FOLLOW "+ifFollow+" "+NumOfUsers+" "+UserNameList.toString();
    }
}
