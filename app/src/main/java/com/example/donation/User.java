package com.example.donation;

public class User {
    private String name;
    private String weight;
    private String location;
    private String group;


    // Empty constructor for Firestore
    public User() {}

    public User(String name, String weight, String location, String group) {
        this.name = name;
        this.weight = weight;
        this.location = location;
        this.group = group;

    }

    public String getName() { return name; }
    public String  getWeight() { return weight; }
    public String getLocation() { return location; }
    public String getGroup() { return group; }

}
