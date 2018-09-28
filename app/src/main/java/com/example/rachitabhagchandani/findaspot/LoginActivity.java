package com.example.rachitabhagchandani.findaspot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
        Button loginButton, signupButton;
        EditText usernameEditText, passwordEditText;

        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login_activity);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

                loginButton = (Button) findViewById(R.id.loginButton);
                signupButton = (Button) findViewById(R.id.registerButton);

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usernameEditText = (EditText) findViewById(R.id.userName);
                        passwordEditText = (EditText) findViewById(R.id.password);
                        String username = usernameEditText.getText().toString();
                        String password = passwordEditText.getText().toString();
                        boolean check = checkValidation(username, password);
                        if(!check){
                            Toast.makeText(getApplicationContext(), "Username/Password incorrect",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            //proceed to login(check from firebase)
                        }
                    }
                });

                signupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //open registreation activity here
                    }
                });
        }

        boolean checkValidation(String usename, String password){
            boolean check = true;
            if(usename == "" || password == "" || usename.length() <3 || password.length() <5){
                check = false;
            }
            return check;
        }
}
