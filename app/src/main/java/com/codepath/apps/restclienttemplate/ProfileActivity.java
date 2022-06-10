package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    JitterClient client;
    List<User> userList;
    ProfileAdapter adapter;

    RecyclerView rvFollow;
    Button btnFollowers;
    Button btnFollowing;
    ImageView ivProfilePic;
    ImageView ivProfileBanner;
    RequestOptions requestOptionsPFP;

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

        requestOptionsPFP = new RequestOptions();
        requestOptionsPFP = requestOptionsPFP.transform(new CenterCrop(), new RoundedCorners(65));

        User user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));


        // Profile Picture
        Glide.with(this).load(user.profileImageURL).apply(requestOptionsPFP).into(ivProfilePic);

        // Profile Banner
        Glide.with(this).load(user.profileBannerURL).into(ivProfileBanner);


        //TODO: ATTACH RECYCLER VIEW
        //TODO: CREATE RECYCLER VIEW ARRAY, POPULATE WITH FOLLOWERS AND FOLLOWING

    }
}