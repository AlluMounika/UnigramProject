package com.germiyanoglu.android.unigramproject.modal;

import android.os.Parcel;
import android.os.Parcelable;

// TODO : 179 ) Creating User class including id,username,phone number and email
public class User implements Parcelable {

    private String userId;
    private String username;
    private String userEmail;
    private String userPhoneNumber;

    public User(){

    }


    public User(String userId, String username, String userEmail, String userPhoneNumber) {
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
    }

    protected User(Parcel in) {
        userId = in.readString();
        username = in.readString();
        userEmail = in.readString();
        userPhoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(username);
        dest.writeString(userEmail);
        dest.writeString(userPhoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                '}';
    }
}
