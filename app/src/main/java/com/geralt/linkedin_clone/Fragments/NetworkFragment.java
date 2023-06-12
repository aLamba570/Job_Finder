package com.geralt.linkedin_clone.Fragments;

import static com.geralt.linkedin_clone.Constants.INFO;
import static com.geralt.linkedin_clone.Constants.REQUEST;
import static com.geralt.linkedin_clone.Constants.USER_CONSTANT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geralt.linkedin_clone.Adapters.NetworkAdapter;
import com.geralt.linkedin_clone.Adapters.RequestAdapter;
import com.geralt.linkedin_clone.Model.RequestModel;
import com.geralt.linkedin_clone.Model.UserModel;
import com.geralt.linkedin_clone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkFragment extends Fragment {

    List<RequestModel> list;
    List<UserModel> connectionList;
    NetworkAdapter adapter;
    RequestAdapter requestAdapter;
    RecyclerView recyclerView, requestRecyclerView;
    DatabaseReference ref;
    FirebaseUser user;
    private List<String> requestList;

    public NetworkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        recyclerView = view.findViewById(R.id.recycler_network);
        requestRecyclerView = view.findViewById(R.id.request_recyclerView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<>();
        requestList = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Network RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setNestedScrollingEnabled(false);

        //Request RecyclerView
        requestRecyclerView.setHasFixedSize(true);
        requestRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        requestRecyclerView.setNestedScrollingEnabled(false);
        
        readUsers();
        GetAllUsersId();
    }

    private void GetAllUsersId() {
        requestList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(USER_CONSTANT).child(user.getUid()).child(REQUEST);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    requestList.add(dataSnapshot.getKey());
                }
                readRequest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                
            }

        });
    }

    private void readRequest() {
    }

    private void readUsers() {
        connectionList = new ArrayList<>();
        ref.child(USER_CONSTANT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                connectionList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel model = dataSnapshot.child(INFO).getValue(UserModel.class);
                    if (!model.getKey().equals(user.getUid())) {
                        connectionList.add(model);
                    }
                }
                Collections.reverse(list);
                adapter = new NetworkAdapter(getActivity(), connectionList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}