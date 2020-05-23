package com.example.instaclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserProfile extends AppCompatActivity {

    ImageView imageUserProfile;
    TextView textLoadImage, textProfileName, textProfileBio, textProfileProfession, textProfileHobbies, textProfileSports;
    FloatingActionButton floatingActionButton;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imageUserProfile = findViewById(R.id.imageUserProfile);
        textLoadImage = findViewById(R.id.textLoadImage);
        textProfileName = findViewById(R.id.textProfileName);
        textProfileBio = findViewById(R.id.textProfileBio);
        textProfileProfession = findViewById(R.id.textProfileProfession);
        textProfileHobbies = findViewById(R.id.textProfileHobbies);
        textProfileSports = findViewById(R.id.textProfileSports);
        floatingActionButton = findViewById(R.id.floatingButtonEdit);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String bio = intent.getStringExtra("Bio");
        String profession = intent.getStringExtra("Profession");
        String hobbies = intent.getStringExtra("Hobbies");
        String sports = intent.getStringExtra("Sports");

        textProfileName.setText(name);
        textProfileBio.setText(bio);
        textProfileProfession.setText(profession);
        textProfileHobbies.setText(hobbies);
        textProfileSports.setText(sports);

        textLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, ProfileTab.class);
                startActivity(intent);
            }
        });

    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    FancyToast.makeText(this, "Permission denied", Toast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUserProfile.setImageURI(data.getData());
        }
    }
}