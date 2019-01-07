package bgu.spl.net.api.Messages.ServerToClient;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class FollowACK extends ACK {
    private int NumOfUsers;
    private LinkedList<String> UserNameList;

    public FollowACK(short Opcode, int NumOfUsers, LinkedList<String> UserNameList)
    {
        super(Opcode);
        this.NumOfUsers=NumOfUsers;
        this.UserNameList=UserNameList;
        bytes = new LinkedList<>();
    }

    @Override
    public byte[] encode() {
        //OpCode
        byte[] A=encodeShort(Opcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //MessageOpCode
        A=encodeShort(MessageOpcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //NumOfUsers
        Integer NUMOFUSERS=NumOfUsers;
        short _NUMOFUSERS=NUMOFUSERS.shortValue();
        A=encodeShort(_NUMOFUSERS);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //UserNameList
        for (String Name:UserNameList) {
            ByteBuffer buffer= StandardCharsets.UTF_8.encode(Name);
            A=buffer.array();
            for (byte temp:A) {
                bytes.add(temp);
            }
            // \0 between each name
            /*
            buffer=ByteBuffer.allocate(1);
            buffer.putChar('\0');
            A=buffer.array();
            for (byte temp:A) {
                bytes.add(temp);
            }
            */
            bytes.add((byte)'\0');
        }
        return ListToArray();
    }
}
