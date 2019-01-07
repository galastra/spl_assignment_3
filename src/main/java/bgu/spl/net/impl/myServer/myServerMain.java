package bgu.spl.net.impl.myServer;

import bgu.spl.net.api.bidi.BidiMsgProtocolImp;
import bgu.spl.net.api.MsgEncodeDecode;
import bgu.spl.net.srv.Server;

public class myServerMain {
    public static void main(String[] args){

    boolean blocking = false;
        if (blocking) {

        System.out.println("SERVER STARTED - Thread Per Client");
        Server.threadPerClient(
                7777, //port
                BidiMsgProtocolImp::new, //protocol factory
                MsgEncodeDecode::new //message encoder decoder factory
        ).serve();
    }
        else {
        System.out.println("SERVER STARTED - Reactor");
        Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                BidiMsgProtocolImp::new, //protocol factory
                MsgEncodeDecode::new //message encoder decoder factory
        ).serve();
    }
}
}
