package bgu.spl.net.api.Messages;

import bgu.spl.net.api.Client;
import bgu.spl.net.api.Messages.ServerToClient.ServerMsg;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {
    public void decode(List<Byte> list){

    }
    public int getOpCode(){
        return 0;
    }

    public ServerMsg process(Client c){
        return null;
    }
}
