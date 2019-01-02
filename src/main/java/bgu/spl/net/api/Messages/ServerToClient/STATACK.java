package bgu.spl.net.api.Messages.ServerToClient;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class STATACK extends ACK {
    private int NumPosts;
    private int NumFollowers;
    private int NumFollowing;
    public STATACK(short Opcode,int NumOfPosts,int NumFollowers,int NumFollowing)
    {
        super(Opcode);
        this.NumFollowers=NumFollowers;
        this.NumFollowing=NumFollowing;
        this.NumPosts=NumOfPosts;
    }

    @Override
    public byte[] encode() {
       bytes=new LinkedList<>();
       byte[] A;
        //OpCode
        A=encodeShort(Opcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //MsgOpCode
        A=encodeShort(MessageOpcode);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //NumPosts
        Integer NUMPOST=NumPosts;
        short _NumPosts=NUMPOST.shortValue();
        A=encodeShort(_NumPosts);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //NumFollowers
        Integer NUMFOLLOWERS=NumFollowers;
        short _NumFollowers=NUMFOLLOWERS.shortValue();
        A=encodeShort(_NumFollowers);
        for (byte temp:A) {
            bytes.add(temp);
        }
        //NumFollowing
        Integer NUMFOLLOWING=NumFollowing;
        short _NumFollowing=NUMFOLLOWING.shortValue();
        A=encodeShort(_NumFollowing);
        for (byte temp:A) {
            bytes.add(temp);
        }
        return ListToArray();
    }


}
