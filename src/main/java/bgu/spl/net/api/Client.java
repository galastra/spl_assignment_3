package bgu.spl.net.api;

import java.util.LinkedList;

public class Client {
    private String Name;
    private String Password;
    private int NumPosts;
    private LinkedList<String> Followers;//maybe a list
    private int NumFollowing;//maybe a list
    private boolean IsConnected;

    public Client(){
        Name="";
        Password="";
        Followers=new LinkedList<>();
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

    public LinkedList<String> getFollowers() {
        return Followers;
    }

    public boolean AddFollower(String Name)
    {
        if(Followers.contains(Name))
            return false;
        Followers.add(Name);
        return true;
    }

    public boolean RemoveFollower(String Name){
        if(Followers.contains(Name))
        {
            Followers.remove(Name);
            return true;
        }
        return false;
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
