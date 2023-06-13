package com.geralt.linkedin_clone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geralt.linkedin_clone.Model.UserModel;
import com.geralt.linkedin_clone.R;
import com.geralt.linkedin_clone.UserActivity;

import java.util.List;

public class NetworkAdapter extends RecyclerView.Adapter<NetworkAdapter.myViewHolder> {

    private final Context aCtx;
    private final List<UserModel> connectionList;
    public NetworkAdapter(FragmentActivity activity, List<UserModel> connectionList, Context aCtx, List<UserModel> connectionList1) {
        this.aCtx = aCtx;
        this.connectionList = connectionList1;
    }


    @NonNull
    @Override
    public NetworkAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(aCtx).inflate(R.layout.card_network, parent, false);
        return new NetworkAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkAdapter.myViewHolder holder, int position) {
        holder.name.setText(connectionList.get(position).getUsername());
        Glide.with(aCtx).load(connectionList.get(position).getImageUrl()).into(holder.userImage);
        holder.headline.setText(connectionList.get(position).getHeadline());
        holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(aCtx, UserActivity.class);
            intent.putExtra("user_data", connectionList.get(position));
            aCtx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, headline;
        ImageView userImage;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            userImage = itemView.findViewById(R.id.profileImg);
            headline = itemView.findViewById(R.id.text_headline);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

}
