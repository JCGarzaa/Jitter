package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    Context context;
     List<Tweet> tweets;

    // Pass in context and list of tweets
    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // for each row, inflate a layout for a tweet
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data at position
        Tweet tweet = tweets.get(position);
        // Bind tweet with viewholder
        holder.bind(tweet); 
    }

    @Override
    public int getItemCount() {
        return tweets.size() ;
    }


    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        TextView tvName;
        ImageView ivEmbedImage;

        ImageButton btnReply;
        TextView tvComments;

        ImageButton btnRetweet;
        TextView tvRetweetCount;

        ImageButton btnFavorite;
        TextView tvFavouritesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            tvName = itemView.findViewById(R.id.tvName);
            ivEmbedImage = itemView.findViewById(R.id.ivEmbedImage);
            ivEmbedImage.setVisibility(View.GONE);     // intially set up as invisible

            tvComments = itemView.findViewById(R.id.tvCommentCount);
            btnReply = itemView.findViewById(R.id.btnComment);

            tvRetweetCount = itemView.findViewById(R.id.tvRetweetCount);
            btnRetweet = itemView.findViewById(R.id.btnRetweet);

            tvFavouritesCount = itemView.findViewById(R.id.tvFavoriteCount);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }

        public void bind(Tweet tweet) {
            RequestOptions requestOptionsIMG = new RequestOptions();
            RequestOptions requestOptionsPFP = new RequestOptions();
            requestOptionsPFP = requestOptionsPFP.transform(new CenterCrop(), new RoundedCorners(65));
            requestOptionsIMG = requestOptionsIMG.transform(new CenterCrop(), new RoundedCorners(45));

            tvBody.setText(tweet.body);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvName.setText(tweet.user.name);

            tvRetweetCount.setText(String.valueOf(tweet.retweetCount));
            tvFavouritesCount.setText(String.valueOf(tweet.favouritesCount));
            //tvComments.setText(String.valueOf(tweet.commentCount));


            Glide.with(context).load(tweet.user.profileImageURL).apply(requestOptionsPFP).into(ivProfileImage);

            if (!tweet.imageURL.equals("")) {
                Glide.with(context).load(tweet.imageURL).apply(requestOptionsIMG).into(ivEmbedImage);
                ivEmbedImage.setVisibility(View.VISIBLE);
            }

            // set timestamp
            tvTimestamp.setText(tweet.timestamp);


            // Set up Reply feature
            btnReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tweet.replyFlag = true;
                    Intent i = new Intent(context, ComposeActivity.class);
                    i.putExtra("tweet", Parcels.wrap(tweet));
                    context.startActivity(i);
                }
            });


            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(context).load(R.drawable.ic_vector_heart).into(btnFavorite);
                    tvFavouritesCount.setText(String.valueOf(tweet.favouritesCount + 1));     // increment like count
                }
            });

            // set up retweet button
            btnRetweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(context).load(R.drawable.ic_vector_retweet).into(btnRetweet);
                    tvRetweetCount.setText(String.valueOf(tweet.retweetCount + 1));       // increment retweet count
                }
            });

            ivProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ProfileActivity.class);
                    i.putExtra("user", Parcels.wrap(tweet.user));
                    context.startActivity(i);
                }
            });

        }
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }
}
