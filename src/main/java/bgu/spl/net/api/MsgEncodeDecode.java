package bgu.spl.net.api;

import bgu.spl.net.api.Messages.ClientToServer.*;
import bgu.spl.net.api.Messages.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class MsgEncodeDecode implements MessageEncoderDecoder<Message> {
    private List<Byte> arr;
    private short OpCode;
    private Message CurrentMsg;
    public MsgEncodeDecode()
    {
        CurrentMsg=new Message();
        arr = new ArrayList<>();
    }

    @Override
    public Message decodeNextByte(byte nextByte) {
        if(OpCode==0)
        {
            if(arr.size()==1)
            {
                /*
                ByteBuffer bb=ByteBuffer.allocate(2);
                bb.order(ByteOrder.LITTLE_ENDIAN);
                bb.put(arr.remove(0));
                bb.put(nextByte);
                bb.flip();
                OpCode=bb.getShort();
                */
                byte[] temp=new byte[2];
                temp[0]=arr.remove(0);
                temp[1]=nextByte;
                OpCode=ByteBuffer.wrap(temp).order(ByteOrder.BIG_ENDIAN).getShort();
                getMassage(OpCode);
                if(CurrentMsg instanceof LOGOUT | CurrentMsg instanceof USERLIST) {// breaks oop a bit but never mind
                    Message To_Return=CurrentMsg;
                    CurrentMsg=new Message();
                    OpCode=0;
                    return To_Return;
                }
                    return null;
            }
            else
                arr.add(nextByte);
        }
           if(CurrentMsg.decodeNextByte(nextByte))//should also deal with msg like USERLIST with only Opcode being sent
           {
               Message to_return = CurrentMsg;
               CurrentMsg = new Message();
               OpCode = 0;
               return to_return;
           }

        return null;
    }

    @Override
    public byte[] encode(Message message) {
        // 02-Jan-19 those msg will be only a server to client msg
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
