package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
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

public class ProfileActivity extends AppCompatActivity {

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

        rvFollow = findViewById(R.id.rvFollow);
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