package com.example.loginact;

public class User_Profile {

    public String whatdoi;
    public  String who;
    public String whatcani;
    public String flag = "0";
    public  String category;
    public String role = "0";
    public String city;
    public String state;


    public  User_Profile()
    {
        this.whatdoi = "";
        this.who = "";
        this.whatcani = "";
        this.flag = "0";
        this.category = "";
        this.role = "0";
        this.city = "";
        this.state = "";
    }
    public User_Profile(String whatdoi, String who, String whatcani, String flag, String category,String role,String city,String state) {
        this.whatdoi = whatdoi;
        this.who = who;
        this.whatcani = whatcani;
        this.flag = flag;
        this.category = category;
        this.role = role;
        this.city = city;
        this.state = state;
    }
}
