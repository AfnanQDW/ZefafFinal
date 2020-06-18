package com.zefaf.zefaffinal.Model;

public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private String profilePic;

    public User() {
    }

    public User(String name, String email, String phoneNumber, String profilePic) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
