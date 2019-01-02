package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Message implements Serializable {

    protected List<Byte> bytes;

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
            return new String(buffer.array(), StandardCharsets.UTF_8);
        }
        catch (Exception e)
        {
            System.out.println(e.toString()+" problem with decode byte to string");
        }
        return "";
    }

    public byte[] encode(){
        return null;
    }

    protected byte[] encodeShort(short val)
    {
        ByteBuffer bb=ByteBuffer.allocate(2);
        bb.putShort(val);
        return bb.array();
    }

    public short getOpCode(){
        return 0;
    }

    public Message process(Client c){
        return null;
    }
}
