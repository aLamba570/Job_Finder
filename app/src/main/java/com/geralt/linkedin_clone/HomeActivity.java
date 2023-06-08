package com.geralt.linkedin_clone;



import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.geralt.linkedin_clone.Fragments.HomeFragment;
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
import com.geralt.linkedin_clone.Constants;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;


public class HomeActivity extends BaseActivity {

    DrawerLayout drawerLayout;
    FirebaseUser user;
    UserModel userModel;

    ImageView profileImg, messageBtn, nav_img, nav_close_img;
    NavigationView navigationView;
    TextView tt, nav_name;


    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    DatabaseReference userRef;
    AppSharedPreferences appSharedPreferences;

    UserModel model;
    public static final String USER_CONSTANT = "Users";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appSharedPreferences = new AppSharedPreferences(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child(Constants.USER_CONSTANT).child(user.getUid());
        drawerLayout = findViewById(R.id.drawerLayout);
        profileImg = findViewById(R.id.img);
        messageBtn = findViewById(R.id.messageBtn);
        navigationView = findViewById(R.id.nav_view);

        UniversalImageLoderClass universalImageLoderClass = new UniversalImageLoderClass(this);
        ImageLoader.getInstance().init(universalImageLoderClass.getConfig());

        View header = navigationView.getHeaderView(0);
        nav_name = header.findViewById(R.id.user_name);
        nav_img = header.findViewById(R.id.img);
        nav_close_img = header.findViewById(R.id.close_img);
        tt = header.findViewById(R.id.tt);

        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(k);
                finish();
            }
        });

        Glide.with(this).load(appSharedPreferences.getImgUrl()).into(profileImg);
        Glide.with(this).load(appSharedPreferences.getImgUrl()).into(nav_img);
        nav_name.setText(appSharedPreferences.getUserName());


        //navbar close button
        nav_close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        // Open extract layout

        profileImg.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.openDrawer(Gravity.START);
            else drawerLayout.closeDrawer(Gravity.END);
        });


        // Open Message Activity
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, MessageActivity.class);
                startActivity(i);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();

        userRef.child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model = snapshot.getValue(UserModel.class);
                appSharedPreferences.setUsername(model.getUsername());
                appSharedPreferences.setImgUrl(model.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }

    private final BottomNavigationView.OnNavigationItemReselectedListener navigationItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_network:
                        selectedFragment = null;
                        break;
                    case R.id.nav_uplod:
                        selectedFragment = null;
                        break;
                    case R.id.nav_notification:
                        selectedFragment = null;
                        break;
                    case R.id.nav_jobs:
                        selectedFragment = null;
                        break;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
                }
                return true;
            };

    @Override
    public void onBackPressed() {
        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        if (mBottomNavigationView.getSelectedItemId() == R.id.nav_home) {
            super.onBackPressed();
            finish();
        } else {
            mBottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}