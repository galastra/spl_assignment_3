package bgu.spl.net.api;

import bgu.spl.net.api.Messages.Message;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {
    private String Name;
    private String Password;
    private int NumPosts;
    private ConcurrentLinkedQueue<String> Followers;//maybe a list
    private ConcurrentLinkedQueue<String> Following;//maybe a list
    private boolean IsConnected;
    private int connId;
    private ConcurrentLinkedQueue<Message> appending_msgs;

    public Client(){
        connId = 0;
        Name="";
        Password="";
        Followers=new ConcurrentLinkedQueue<>();
        Following=new ConcurrentLinkedQueue<>();
        NumPosts=0;
        IsConnected=false;
        appending_msgs = new ConcurrentLinkedQueue<>();
    }

    public int getNumPosts() {
        return NumPosts;
    }

    public ConcurrentLinkedQueue<String> getFollowing() {
        return Following;
    }

    public boolean AddFollowing(String Name){
        if(Following.contains(Name))
            return false;
        Following.add(Name);
        return true;
    }

    public boolean removeFollowing(String name){
        if(Following.contains(name))
            Following.remove(name);
        else
            return false;
        return true;
    }

    public ConcurrentLinkedQueue<String> getFollowers() {
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

    public void Register(String Name,String Password)
    {
        this.Name=Name;
        this.Password=Password;
    }

    public void Login(){
        IsConnected=true;
    }

    public void LogOut(){
        IsConnected=false;
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

    public int getConnId() {
        return connId;
    }

    public void setConnId(int connId) {
        this.connId = connId;
    }

    public void addMsg(Message msg){
        appending_msgs.add(msg);
    }
    public ConcurrentLinkedQueue<Message> getAppending_msgs(){
        return appending_msgs;
    }

    public void setClient(Client client)
    {
        this.Name=client.getName();
        this.Password=client.Password;
        this.Following=client.Following;
        this.Followers=client.Followers;
        this.NumPosts=client.NumPosts;
        this.appending_msgs=client.appending_msgs;
    }

    public void clearPendingMsgs(){
        appending_msgs.clear();
    }

    public void AddToNumPost(){
        NumPosts++;
    }
}
