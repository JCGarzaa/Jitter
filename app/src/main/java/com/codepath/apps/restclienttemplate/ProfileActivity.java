package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = "ProfileActivity";

    JitterClient client;
    List<User> userList;
    ProfileAdapter adapter;

    RecyclerView rvFollow;
    Button btnFollowers;
    Button btnFollowing;
    ImageView ivProfilePic;
    ImageView ivProfileBanner;
    RequestOptions requestOptionsPFP;

    TextView tvFollowingCount;
    TextView tvFollowersCount;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client =  JitterrApp.getRestClient(this);

        // Setup Recycler View
        rvFollow = findViewById(R.id.rvFollow);
        userList = new ArrayList<>();
        adapter = new ProfileAdapter(this, userList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvFollow.setLayoutManager(llm);
        rvFollow.setAdapter(adapter);

        btnFollowers = findViewById(R.id.btnFollowers);
        btnFollowing = findViewById(R.id.btnFollowing);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivProfileBanner = findViewById(R.id.ivProfileBanner);

        tvFollowersCount = findViewById(R.id.tvFollowerCount);
        tvFollowingCount = findViewById(R.id.tvFollowingCount);
        tvDescription = findViewById(R.id.tvDescription);


        requestOptionsPFP = new RequestOptions();
        requestOptionsPFP = requestOptionsPFP.transform(new CenterCrop(), new RoundedCorners(65));

        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));

        populateFollowers(user.screenName);

        tvFollowingCount.setText(String.valueOf(user.followingCount));
        tvFollowersCount.setText(String.valueOf(user.followersCount));
        tvDescription.setText(user.description);

        // Profile Picture
        Glide.with(this).load(user.profileImageURL).apply(requestOptionsPFP).into(ivProfilePic);

        // Profile Banner
        Glide.with(this).load(user.profileBannerURL).into(ivProfileBanner);

        //TODO: FIX THIS
        //TODO: ATTACH RECYCLER VIEW
        //TODO: CREATE RECYCLER VIEW ARRAY, POPULATE WITH FOLLOWERS AND FOLLOWING



    }
    private void populateFollowers(String screenName) {
        client.getFollowers(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "Recieved followers: " + json.toString());

                try {
                    JSONArray jsonArray = json.jsonObject.getJSONArray("users");
                    userList.addAll(User.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                }
                catch (JSONException e) {
                    Log.e(TAG, "JSON Exception!");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure " + response, throwable);
            }
        });

    }
}