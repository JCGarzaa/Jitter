package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageURL;
    public String profileBannerURL;
    public int followersCount;
    public int followingCount;
    //TODO: public boolean verified;

    public User() {}

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");
        user.profileBannerURL = jsonObject.getString("profile_banner_url");
        user.followersCount = jsonObject.getInt("followers_count");
        user.followingCount = jsonObject.getInt("friends_count");

        //TODO: user.verified = jsonObject.getBoolean("verified");
        return user;
    }
}
