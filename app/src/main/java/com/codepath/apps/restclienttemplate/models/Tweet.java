package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        return tweet;
    }

    // iterate and create a tweet for each object
    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // For each JSONObject in the jsonArray, create a Tweet object from the data and add it to a list of Tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
