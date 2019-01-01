package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;

public class Message implements Serializable {

    protected boolean IsReady=false;
    protected List<Byte> bytes;

    public boolean getIsReady(){
        return IsReady;
    }

    public boolean decodeNextByte(byte nextByte){
        return false;
    }

    protected byte[] ListToArray(){
        byte[] arr=new byte[bytes.size()];
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=bytes.get(i);
        }
        bytes=new LinkedList<>();
        return arr;
    }

    protected String GetStringFromBytes(){
        ByteBuffer buffer=ByteBuffer.wrap(ListToArray());
        try {
            return new String(buffer.array(), "US-ASCII");
        }
        catch (Exception e)
        {

        }
        return "";
    }

    protected byte[] encodeShort(short val)
    {
        ByteBuffer bb=ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(val);
        return bb.array();
    }

    public int getExpectedZeroBytes(){return -1;}

    public short getOpCode(){
        return 0;
    }

    public ServerMsg process(Client c){
        return null;
    }
}
