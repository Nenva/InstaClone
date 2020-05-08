package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editLoginEmail, editLoginPassword;
    private Button buttonLogInActivity, buttonSignUpLogInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");

        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPassword = findViewById(R.id.editLoginPassword);

        editLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(buttonLogInActivity);
                }
                return false;
            }
        });

        buttonLogInActivity = findViewById(R.id.buttonLogInActivity);
        buttonSignUpLogInActivity = findViewById(R.id.buttonSignUpLoginActivity);

        buttonLogInActivity.setOnClickListener(this);
        buttonSignUpLogInActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
           ParseUser.getCurrentUser().logOut();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonLogInActivity:

                if (editLoginEmail.getText().toString().equals("") ||
                        editLoginPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LoginActivity.this, "All fields are required!",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(editLoginEmail.getText().toString());
                    appUser.setUsername(editLoginPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + editLoginEmail.getText().toString());
                    progressDialog.show();

                    ParseUser.logInInBackground(editLoginEmail.getText().toString(),
                            editLoginPassword.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        FancyToast.makeText(LoginActivity.this, user.getUsername() + " is logged up successfully!",
                                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        transitionToSocialMediaActivity();

                                    } else {
                                        FancyToast.makeText(LoginActivity.this, e.getMessage(),
                                                FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
                }
                break;

            case R.id.buttonSignUpLoginActivity:
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
