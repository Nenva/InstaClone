package com.example.instaclone;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.awt.font.TextAttribute;

public class ProfileTab extends Fragment {

    private EditText editProfileName, editProfileBio, editProfileProfession, editProfileHobbies, editProfileSports;
    private Button buttonUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        editProfileName = view.findViewById(R.id.editProfileName);
        editProfileBio = view.findViewById(R.id.editProfileBio);
        editProfileProfession = view.findViewById(R.id.editProfileProfession);
        editProfileHobbies = view.findViewById(R.id.editProfileHobbies);
        editProfileSports = view.findViewById(R.id.editProfileSports);
        buttonUpdateInfo = view.findViewById(R.id.buttonUpdateInfo);


        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null) {
            editProfileName.setText("");
        } else {
            editProfileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileBio") == null) {
            editProfileBio.setText("");
        } else {
            editProfileBio.setText(parseUser.get("profileBio").toString());
        }

        if (parseUser.get("profileProfession") == null) {
            editProfileProfession.setText("");
        } else {
            editProfileProfession.setText(parseUser.get("profileProfession").toString());
        }

        if (parseUser.get("profileHobbies") == null) {
            editProfileHobbies.setText("");
        } else {
            editProfileHobbies.setText(parseUser.get("profileHobbies").toString());
        }

        if (parseUser.get("profileSports") == null) {
            editProfileSports.setText("");
        } else {
            editProfileSports.setText(parseUser.get("profileSports").toString());
        }

        buttonUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", editProfileName.getText().toString());
                parseUser.put("profileBio", editProfileBio.getText().toString());
                parseUser.put("profileProfession", editProfileProfession.getText().toString());
                parseUser.put("profileHobbies", editProfileHobbies.getText().toString());
                parseUser.put("profileSports", editProfileSports.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), "Info Updated!",
                                    FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                            transitionToUserProfileActivity();
                        } else {
                            FancyToast.makeText(getContext(), e.getMessage(),
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR,false).show();
                        }
                    }
                });

            }
        });

        return view;
    }

    private void transitionToUserProfileActivity() {
        Intent intent = new Intent(getActivity(), UserProfile.class);
        String name = editProfileName.getText().toString();
        String bio = editProfileBio.getText().toString();
        String profession = editProfileProfession.getText().toString();
        String hobbies = editProfileHobbies.getText().toString();
        String sports = editProfileSports.getText().toString();

        intent.putExtra("Bio", bio);
        intent.putExtra("Profession", profession);
        intent.putExtra("Hobbies", hobbies);
        intent.putExtra("Sports", sports);
        startActivity(intent);
    }
}
