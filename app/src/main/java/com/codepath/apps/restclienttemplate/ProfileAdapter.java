package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.User;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    Context context;
    List<User> usersList;

    public ProfileAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    // for each row, inflate a layout for a profile
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(view);
    }

    // bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get data at position
        User user = usersList.get(position);
        // Bind tweet with viewholder
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return usersList.size() ;
    }

    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView ivProfilePic;
        public TextView tvProfileName;
        public TextView tvUsername;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // to access the context from any ViewHolder instance.
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvProfileName = itemView.findViewById(R.id.tvProfileName);
            tvUsername = itemView.findViewById(R.id.tvUsername);
        }

        public void bind(User user) {
            tvProfileName.setText(user.name);
            tvUsername.setText(user.screenName);
            if(user.profileImageURL != null) {
                Glide.with(context).load(user.profileImageURL).into(ivProfilePic);
            }
        }
    }

}
