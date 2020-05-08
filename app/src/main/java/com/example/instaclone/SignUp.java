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
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    //UI Components
    private EditText editEmailSignUp, editUsernameSignUp, editPasswordSignUp;
    private Button buttonSignUp, buttonLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        editEmailSignUp = findViewById(R.id.editEmailSignUpActivity);
        editUsernameSignUp = findViewById(R.id.editUsernameSignUpActivity);
        editPasswordSignUp = findViewById(R.id.editPasswordSignUpActivity);

        editPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(buttonSignUp);
                }
                return false;
            }
        });

        buttonSignUp = findViewById(R.id.buttonSignUpActivity);
        buttonLogIn = findViewById(R.id.buttonLogInSignUpActivity);

        buttonSignUp.setOnClickListener(this);
        buttonLogIn.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonSignUpActivity:
                if (editEmailSignUp.getText().toString().equals("") ||
                editUsernameSignUp.getText().toString().equals("") ||
                editPasswordSignUp.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "All fields are required!",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(editEmailSignUp.getText().toString());
                    appUser.setUsername(editUsernameSignUp.getText().toString());
                    appUser.setPassword(editPasswordSignUp.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + editUsernameSignUp.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up!",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),
                                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;

            case R.id.buttonLogInSignUpActivity:
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
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
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }
}
