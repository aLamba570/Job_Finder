package com.geralt.linkedin_clone.Adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.geralt.linkedin_clone.Model.UserModel;

import java.util.List;

public class NetworkAdapter extends RecyclerView.Adapter {

    private final Context aCtx;
    private final List<UserModel> connectionList;
    public NetworkAdapter(FragmentActivity activity, List<UserModel> connectionList, Context aCtx, List<UserModel> connectionList1) {
        this.aCtx = aCtx;
        this.connectionList = connectionList1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void notifyDataSetChanged() {
    }
}
