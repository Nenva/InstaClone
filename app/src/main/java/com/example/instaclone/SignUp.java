package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSave;
    private EditText editName, editPunchSpeed, editPunchPower, editKickSpeed, editKickPower;
    private TextView textGetData;
    private Button buttonGetAllData;
    private String allKickBoxers;
    private Button buttonTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(SignUp.this);
        editName = findViewById(R.id.editName);
        editPunchSpeed = findViewById(R.id.editPunchSpeed);
        editPunchPower = findViewById(R.id.editPunchPower);
        editKickSpeed = findViewById(R.id.editKickSpeed);
        editKickPower = findViewById(R.id.editKickPower);
        textGetData = findViewById(R.id.textGetData);
        buttonGetAllData = findViewById(R.id.buttonGetAllData);
        buttonTransition = findViewById(R.id.buttonNextActivity);

        textGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Kickboxer");
                parseQuery.getInBackground("Vmucg5XL0Z", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            textGetData.setText(object.get("name") + "");
                        }
                    }
                });
            }
        });

        buttonGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null){
                            if (objects.size() > 0) {

                                for (ParseObject kickBoxer : objects) {
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                }

                                FancyToast.makeText(SignUp.this, allKickBoxers,
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                        }
                    }
                });
            }
        });

        buttonTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", editName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(editPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(editPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(editKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(editKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server",
                                FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(),
                                FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    }
                }
            });

        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(),
                    FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
        }
    }
}
