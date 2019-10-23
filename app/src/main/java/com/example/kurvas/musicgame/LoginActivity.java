package com.example.kurvas.musicgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {



    // UI references.




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.


    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */


    private boolean isPasswordFormatValid(String password) {
        boolean valid = true;
        if (password.length() > 15 || password.length() < 8) {
            Toast.makeText(this, "Password should be less than 15 and more than 8 characters in length.", Toast.LENGTH_LONG).show();
            valid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars)) {
            Toast.makeText(this, "Password should contain at least one upper case alphabet", Toast.LENGTH_LONG).show();
            valid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars)) {
            Toast.makeText(this, "Password should contain at least one lower case alphabet", Toast.LENGTH_LONG).show();

            valid = false;
        }
        /*String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            Toast.makeText(this,"Password should contain at least one number.",Toast.LENGTH_LONG).show();

            valid = false;
        }
        String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";
        if (!password.matches(specialChars ))
        {
            Toast.makeText(this,"Password should contain at least one special character",Toast.LENGTH_LONG).show();

            valid = false;
        }*/
        return valid;

    }

}

