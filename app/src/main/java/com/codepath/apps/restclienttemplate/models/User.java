package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String profileImageURL;
    public String profileBannerURL;
    public int followersCount;
    public int followingCount;
    public String description;
    //TODO: public boolean verified;

    public User() {}

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.profileImageURL = jsonObject.getString("profile_image_url_https");
        if(jsonObject.has("profile_banner_url")) {
            user.profileBannerURL = jsonObject.getString("profile_banner_url");
        } else {
            user.profileBannerURL = null;
        }
        user.followersCount = jsonObject.getInt("followers_count");
        user.followingCount = jsonObject.getInt("friends_count");
        user.description = jsonObject.getString("description");

        //TODO: user.verified = jsonObject.getBoolean("verified");
        return user;
    }

    // iterate and create a tweet for each object
    public static ArrayList<User> fromJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<User> users = new ArrayList<>();
        // For each JSONObject in the jsonArray, create a User object from the data and add it to a list of Tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            User user = fromJson(jsonArray.getJSONObject(i));
            users.add(user);
        }
        return users;
    }
}
