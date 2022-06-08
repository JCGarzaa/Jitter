package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageURL;

    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        if (jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString("full_text");
        }
        else {
            tweet.body = jsonObject.getString("text");
        }

        JSONObject entities = jsonObject.getJSONObject("entities");

        if (entities.has("media")) {
            JSONArray mediaArray = entities.getJSONArray("media");
                tweet.imageURL = mediaArray.getJSONObject(0).getString("media_url_https");
                //tweet.imageURL += "?size=small";

            Log.i("TWEET", "HAS MEDIA!!");
        }
        else {
            tweet.imageURL = "";
            Log.i("TWEET", "NO MEDIA :(");
        }

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
