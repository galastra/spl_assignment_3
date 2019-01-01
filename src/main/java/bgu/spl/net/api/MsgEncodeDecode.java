package bgu.spl.net.api;

import bgu.spl.net.api.Messages.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class MsgEncodeDecode implements MessageEncoderDecoder<Message> {
    private List<Byte> arr;
    private short OpCode;
    private Message CurrentMsg;
    public MsgEncodeDecode(){

    }

    @Override
    public Message decodeNextByte(byte nextByte) {
        if(OpCode==0)
        {
            if(arr.size()==2)
            {
                ByteBuffer bb=ByteBuffer.allocate(2);
                bb.order(ByteOrder.LITTLE_ENDIAN);
                bb.put(arr.remove(0));
                bb.put(arr.remove(0));
                OpCode=bb.getShort();
            }
            else
                arr.add(nextByte);
        }
        else{
           if(CurrentMsg.decodeNextByte(nextByte))
               return CurrentMsg;
        }
        return null;
    }

    @Override
    public byte[] encode(Message message) {
        return message.encode();
    }

    private void getMassage(short OpCode)
    {

        switch (OpCode){
            case 1:
                CurrentMsg=new REGISTER();
                break;
            case 2:
                CurrentMsg=new LOGIN();
                break;
            case 3:
                CurrentMsg=new LOGOUT();
                break;
            case 4:
                CurrentMsg=new FOLLOW();
                break;
            case 5:
                CurrentMsg=new POST();
                break;
            case 6:
                CurrentMsg=new PM();
                break;
            case 7:
                CurrentMsg=new USERLIST();
                break;
            case 8:
                CurrentMsg=new STAT();
                break;
            //no need to include server to client messages

        }

    }
}
