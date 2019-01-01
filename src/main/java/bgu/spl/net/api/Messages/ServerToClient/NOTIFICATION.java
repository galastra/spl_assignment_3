package bgu.spl.net.api.Messages.ServerToClient;

import java.io.Serializable;

public class NOTIFICATION implements Serializable {
    public final short Opcode=9;
    private boolean IsPM;//false=Public
    private String PostringUser;
    private String Content;

    public NOTIFICATION(){}

}
