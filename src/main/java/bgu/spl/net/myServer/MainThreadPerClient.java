package bgu.spl.net.myServer;

import bgu.spl.net.api.BidiMsgProtocolImp;
import bgu.spl.net.api.MsgEncodeDecode;
import bgu.spl.net.srv.Server;

import java.io.IOException;

public class MainThreadPerClient {
    public static void main(String[] args) throws IOException {
        Server.threadPerClient(
                7777, //port
                BidiMsgProtocolImp::new, //protocol factory
                MsgEncodeDecode::new //message encoder decoder factory
        ).serve();
    }
}
