package bgu.spl.net.api;

public class Client {
    private String Name;
    private String Password;
    private int NumPosts;
    private int NumFollowers;//maybe a list
    private int NumFollowing;//maybe a list
    private boolean IsConnected;

    public Client(){
        Name="";
        Password="";
        NumFollowers=0;
        NumFollowing=0;
        NumPosts=0;
        IsConnected=false;
    }

    public int getNumPosts() {
        return NumPosts;
    }

    public int getNumFollowing() {
        return NumFollowing;
    }

    public int getNumFollowers() {
        return NumFollowers;
    }

    public boolean getIsConncted(){
        return IsConnected;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
