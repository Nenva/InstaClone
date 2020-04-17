package com.example.instaclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLogIn extends AppCompatActivity {

    private EditText editUserNameSignUp, editPasswordSignUp, editUserNameLogIn, editPasswordLogIn;
    private Button buttonSignUp, buttonLogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_log_in);

        editUserNameSignUp = findViewById(R.id.editUserNameSignUp);
        editPasswordSignUp = findViewById(R.id.editPasswordSignUp);
        editUserNameLogIn = findViewById(R.id.editUserNameLogIn);
        editPasswordLogIn = findViewById(R.id.editPasswordLogIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonLogIn = findViewById(R.id.buttonLogIn);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(editUserNameSignUp.getText().toString());
                appUser.setPassword(editPasswordSignUp.getText().toString());
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(SignUpLogIn.this, appUser.get("username") + " is signed up successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        } else {
                            FancyToast.makeText(SignUpLogIn.this, e.getMessage(),
                                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                    }
                });
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(editUserNameLogIn.getText().toString(),
                        editPasswordLogIn.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null && e == null) {
                                    FancyToast.makeText(SignUpLogIn.this, user.get("username") + " is logged in successfully",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                } else {
                                    FancyToast.makeText(SignUpLogIn.this, e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                }
                            }
                        });
            }
        });
    }
}
