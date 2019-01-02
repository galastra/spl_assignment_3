package bgu.spl.net.api.Messages.ServerToClient;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class USERLIST_ACK extends ACK {
    private int NumOfUsers;
    private LinkedList<String> UserNameList;
    public USERLIST_ACK(short Opcode, int NumOfUsers, LinkedList<String> UserNameList)
    {
        super(Opcode);
        this.NumOfUsers=NumOfUsers;
        this.UserNameList=UserNameList;
    }

    @Override
    public byte[] encode() {
        byte[] A;
        bytes=new LinkedList<>();
        ByteBuffer buffer;
        //Opcode
        A=encodeShort(Opcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //MsgOPcode
        A=encodeShort(MessageOpcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //NumOfUsers
        Integer NUMOFUSERS=NumOfUsers;
        short _NumOfUsers=NUMOFUSERS.shortValue();
        A=encodeShort(_NumOfUsers);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //UserNameList
        for (String Name:UserNameList) {
            buffer= StandardCharsets.UTF_8.encode(Name);
            A=buffer.array();
            for (byte temp:A) {
                bytes.add(temp);
            }
            // \0
            buffer.reset();
            buffer.putChar('\0');
            A=buffer.array();
            for (byte temp:A) {
                bytes.add(temp);
            }
            buffer.reset();
        }
        return ListToArray();
    }
}
