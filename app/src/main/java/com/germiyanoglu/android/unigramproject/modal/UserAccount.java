package com.germiyanoglu.android.unigramproject.modal;

import android.os.Parcel;
import android.os.Parcelable;

// TODO : 188 ) Creating UserAccount class including userId,description,followers,
// following,posts,profile_photo,userfullname,username,website
public class UserAccount implements Parcelable {

    private String userId;
    private String description;
    private int followers;
    private int following;
    private int posts;
    private String profile_photo;
    private String userfullname;
    private String username;
    private String website;

    public UserAccount(){

    }

    public UserAccount(String userId, String description, int followers, int following,
                       int posts, String profile_photo, String userfullname,
                       String username, String website) {
        this.userId = userId;
        this.description = description;
        this.followers = followers;
        this.following = following;
        this.posts = posts;
        this.profile_photo = profile_photo;
        this.userfullname = userfullname;
        this.username = username;
        this.website = website;
    }


    protected UserAccount(Parcel in) {
        userId = in.readString();
        description = in.readString();
        followers = in.readInt();
        following = in.readInt();
        posts = in.readInt();
        profile_photo = in.readString();
        userfullname = in.readString();
        username = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(description);
        dest.writeInt(followers);
        dest.writeInt(following);
        dest.writeInt(posts);
        dest.writeString(profile_photo);
        dest.writeString(userfullname);
        dest.writeString(username);
        dest.writeString(website);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserAccount> CREATOR = new Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", posts=" + posts +
                ", profile_photo='" + profile_photo + '\'' +
                ", userfullname='" + userfullname + '\'' +
                ", username='" + username + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
