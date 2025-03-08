package com.example.donation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter_1 extends RecyclerView.Adapter<UserAdapter_1.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter_1(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_item_donate, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewName.setText(user.getName());
        holder.textViewweight.setText("weight: " + user.getWeight());
        holder.textViewlocation.setText("location: " + user.getLocation());
        holder.textViewgroup.setText("group: " + user.getGroup());

        // Load profile image using Glide

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewweight,textViewlocation,textViewgroup;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewweight = itemView.findViewById(R.id.textViewweight);
            textViewlocation = itemView.findViewById(R.id.textViewlocation);
            textViewgroup = itemView.findViewById(R.id.textViewgroup);
        }
    }
}
