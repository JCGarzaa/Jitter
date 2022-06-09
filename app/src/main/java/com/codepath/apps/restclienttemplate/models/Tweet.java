package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageURL;
    public String timestamp;

    public int commentCount;
    public int retweetCount;
    public int favouritesCount;
    public boolean replyFlag;

    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.replyFlag = false;            // set Flag to false intially

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

            Log.i("TWEET", "HAS MEDIA!!");
        }
        else {
            tweet.imageURL = "";
            Log.i("TWEET", "NO MEDIA :(");
        }

        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.favouritesCount = jsonObject.getInt("favorite_count");

        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        tweet.timestamp = tweet.getRelativeTimeAgo(tweet.createdAt);
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

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + "m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + "h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + "d";
            }
        } catch (ParseException e) {
            Log.i("TWEET", "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
