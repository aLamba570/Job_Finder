package com.geralt.linkedin_clone;

import static com.google.android.gms.common.internal.Constants.*;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.geralt.linkedin_clone.Model.UserModel;
import com.geralt.linkedin_clone.utils.AppSharedPreferences;
import com.google.android.gms.common.internal.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    }
}