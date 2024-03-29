package com.geralt.linkedin_clone;

import static com.geralt.linkedin_clone.Constants.REQUEST;
import static com.geralt.linkedin_clone.Constants.USER_CONSTANT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geralt.linkedin_clone.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {


    CircleImageView profileImg;
    TextView txt_name, txt_title, txt_location, item_search_input, user_email, profile_link;
    CardView connectBtn;
    DatabaseReference database;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        UserModel userModel = intent.getParcelableExtra("user_data");


        txt_name = findViewById(R.id.txt_name);
        txt_title = findViewById(R.id.txt_headline);
        txt_location = findViewById(R.id.txt_location);
        profileImg = findViewById(R.id.profileImg);
        item_search_input = findViewById(R.id.item_search_input);
        connectBtn = findViewById(R.id.connectBtn);
        user_email = findViewById(R.id.user_email);
        backBtn = findViewById(R.id.btn_back);
        profile_link = findViewById(R.id.profile_link);

        backBtn.setOnClickListener(v -> finish());

        item_search_input.setText(userModel.getUsername());
        txt_name.setText(userModel.getUsername());
        txt_location.setText(userModel.getLocation());
        user_email.setText(userModel.getEmailAddress());
        txt_title.setText(userModel.getHeadline());
        profile_link.setText(String.format("%s%s%s", "https://www.linkedin.com/in/", userModel.getUsername(), "-a785i1b7/"));
        Glide.with(this).load(userModel.getImageUrl()).into(profileImg);


        connectBtn.setOnClickListener(v -> {
            database.child(USER_CONSTANT).child(userModel.getKey()).child(REQUEST).child(user.getUid()).setValue(true);
            connectBtn.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray));
            connectBtn.setEnabled(false);
        });

    }
}