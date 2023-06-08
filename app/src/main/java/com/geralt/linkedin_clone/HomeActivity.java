package com.geralt.linkedin_clone;


import static com.geralt.linkedin_clone.R.anim;
import static com.geralt.linkedin_clone.R.id;
import static com.geralt.linkedin_clone.R.id.nav_home;
import static com.geralt.linkedin_clone.R.id.nav_jobs;
import static com.geralt.linkedin_clone.R.id.nav_network;
import static com.geralt.linkedin_clone.R.id.nav_notification;
import static com.geralt.linkedin_clone.R.id.nav_uplod;
import static com.geralt.linkedin_clone.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.geralt.linkedin_clone.Fragments.HomeFragment;
import com.geralt.linkedin_clone.Fragments.JobsFragment;
import com.geralt.linkedin_clone.Fragments.NetworkFragment;
import com.geralt.linkedin_clone.Fragments.NotificationFragment;
import com.geralt.linkedin_clone.Message.MessageActivity;
import com.geralt.linkedin_clone.Model.UserModel;
import com.geralt.linkedin_clone.Profile.ProfileActivity;
import com.geralt.linkedin_clone.utils.AppSharedPreferences;
import com.geralt.linkedin_clone.utils.UniversalImageLoderClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;


public class HomeActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    FirebaseUser user;

    ImageView profileImg, messageBtn, nav_img, nav_close_img;
    NavigationView navigationView;
    TextView tt, nav_name;


    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    DatabaseReference userRef;
    AppSharedPreferences appSharedPreferences;

    UserModel model;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);

        appSharedPreferences = new AppSharedPreferences(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.USER_CONSTANT).child(user.getUid());
        drawerLayout = findViewById(id.drawerLayout);
        profileImg = findViewById(id.img);
        messageBtn = findViewById(id.messageBtn);
        navigationView = findViewById(id.nav_view);

        UniversalImageLoderClass universalImageLoderClass = new UniversalImageLoderClass(this);
        ImageLoader.getInstance().init(universalImageLoderClass.getConfig());

        View header = navigationView.getHeaderView(0);
        nav_name = header.findViewById(id.user_name);
        nav_img = header.findViewById(id.img);
        nav_close_img = header.findViewById(id.close_img);
        tt = header.findViewById(id.tt);

        tt.setOnClickListener(v -> {
            Intent k = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(k);
            finish();
        });

        Glide.with(this).load(appSharedPreferences.getImgUrl()).into(profileImg);
        Glide.with(this).load(appSharedPreferences.getImgUrl()).into(nav_img);
        nav_name.setText(appSharedPreferences.getUserName());


        //navbar close button
        nav_close_img.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Open extract layout

        profileImg.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.openDrawer(Gravity.START);
            else drawerLayout.closeDrawer(Gravity.END);
        });


        // Open Message Activity
        messageBtn.setOnClickListener(v -> {
            Intent i = new Intent(HomeActivity.this, MessageActivity.class);
            startActivity(i);
        });

        bottomNavigationView = findViewById(id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle navigation item selection here
            switch (item.getItemId()) {
                case nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case nav_network:
                    selectedFragment = new NetworkFragment();
                    startActivity(new Intent(HomeActivity.this, SharepostActivity.class));
                    overridePendingTransition(anim.slide_up, anim.slide_down);
                    break;
                case nav_uplod:
                    selectedFragment = null;
                    break;
                case nav_notification:
                    selectedFragment = new NotificationFragment();
                    break;
                case nav_jobs:
                    selectedFragment = new JobsFragment();
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(id.frame_layout, selectedFragment).commit();
            }

            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(id.frame_layout, new HomeFragment()).commit();

        userRef.child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(UserModel.class);
                assert model != null;
                appSharedPreferences.setUsername(model.getUsername());
                appSharedPreferences.setImgUrl(model.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    @Override
    public void onBackPressed() {
        BottomNavigationView mBottomNavigationView = findViewById(id.bottomNavigationView);
        if (mBottomNavigationView.getSelectedItemId() == nav_home) {
            super.onBackPressed();
            finish();
        } else {
            mBottomNavigationView.setSelectedItemId(nav_home);
        }
    }
}