package com.germiyanoglu.android.unigramproject.modal;

import android.os.Parcel;
import android.os.Parcelable;

// TODO : 225 ) Creating UserInformation class for retieving all information of each user
public class UserInformation implements Parcelable{

    private User user;
    private UserAccount accountInformation;

    public UserInformation(){

    }

    public UserInformation(User user, UserAccount accountInformation) {
        this.user = user;
        this.accountInformation = accountInformation;
    }


    protected UserInformation(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        accountInformation = in.readParcelable(UserAccount.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeParcelable(accountInformation, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel in) {
            return new UserInformation(in);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccount getAccountInformation() {
        return accountInformation;
    }

    public void setAccountInformation(UserAccount accountInformation) {
        this.accountInformation = accountInformation;
    }
}
