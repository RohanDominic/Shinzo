package com.example.shinzo;



public class UserProfile {
    public String userAge;
    public String userEmail;
    public String userName;
    public String userWeight;
    public String userHeight;
    public String userec1;
    public String userec2;
    public String userec3;

    public UserProfile(){
    }

    public UserProfile(String userAge, String userEmail, String userName, String userWeight, String userHeight, String userec1, String userec2, String userec3) {
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.userec1 = userec1;
        this.userec2 = userec2;
        this.userec3 = userec3;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }
    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }
    public String getUserec1() {
        return userec1;
    }

    public void setUserec1(String userec1) {
        this.userec1 = userec1;
    }
    public String getUserec2() {
        return userec2;
    }

    public void setUserec2(String userec2) {
        this.userec2 = userec2;
    }
    public String getUserec3() {
        return userec3;
    }

    public void setUserec3(String userec2) {
        this.userec3 = userec3;
    }

}

