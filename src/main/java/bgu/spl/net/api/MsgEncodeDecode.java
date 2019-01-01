package bgu.spl.net.api;

import bgu.spl.net.api.Messages.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class MsgEncodeDecode implements MessageEncoderDecoder<Message> {
    private List<Byte> arr;
    private short OpCode;
    private int ExpectedBytes;
    public MsgEncodeDecode(){

    }

    @Override
    public Message decodeNextByte(byte nextByte) {
        if(arr.size()<2)
            arr.add(nextByte);
        else if(OpCode==0)
        {
            ByteBuffer bb=ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            bb.put(arr.remove(0));
            bb.put(arr.remove(0));
            OpCode=bb.getShort();

        }
        else if(ExpectedBytes>0)
        {
            arr.add(nextByte);
            ExpectedBytes--;
        }
        else
        {
            Message msg=getMassage(OpCode);
            msg.decode(arr);
            return msg;
        }
        return null;
    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }

    private Message getMassage(short OpCode)
    {
        Message ToReturn=null;
        switch (OpCode){
            case 1:
                ExpectedBytes=5;//HOW MANY BYTES I should expect from this message
                ToReturn=new REGISTER();
                break;
            case 2:
                ToReturn=new LOGIN();
                break;
            case 3:
                ToReturn=new LOGOUT();
                break;
            case 4:
                ToReturn=new FOLLOW();
                break;
            case 5:
                ToReturn=new POST();
                break;
            case 6:
                ToReturn=new PM();
                break;
            case 7:
                ToReturn=new USERLIST();
                break;
            case 8:
                ToReturn=new STAT();
                break;
            //no need to include server to client messages

        }
        return ToReturn;
    }
}
