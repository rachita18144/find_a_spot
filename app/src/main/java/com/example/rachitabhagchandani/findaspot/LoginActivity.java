package com.example.rachitabhagchandani.findaspot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
        Button loginButton, signupButton;
        EditText usernameEditText, passwordEditText;
        private FirebaseAuth mAuth;


        protected void onCreate(Bundle savedInstanceState)
        {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.login_activity);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                mAuth = FirebaseAuth.getInstance();

                loginButton = (Button) findViewById(R.id.loginButton);
                signupButton = (Button) findViewById(R.id.registerButton);
               //final Intent intent1= new Intent(this,MapsActivity.class);
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
                            loginUser(username, password);
                            //proceed to login(check from firebase)

                            //startActivity(intent1);
                            Log.d("MAP","hereeee");
                        }
                    }
                });

                signupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(i);
                    }
                });
        }

        boolean checkValidation(String usename, String password){

            boolean check = true;
            if(usename.isEmpty()){
                usernameEditText.setError("Username is required");
                usernameEditText.requestFocus();
                check = false;
            }

            if(password.isEmpty()){
                passwordEditText.setError("Password is required");
                passwordEditText.requestFocus();
                check = false;
            }

            if(usename.length() <3){
                usernameEditText.setError("Username should be atleast 3 characters long");
                usernameEditText.requestFocus();
                check = false;
            }

            if(password.length() <5){
                passwordEditText.setError("password should be atleast 5 characters long");
                passwordEditText.requestFocus();
                check = false;
            }
            if(usename == "" || password == "" || usename.length() <3 || password.length() <5){
                check = false;
            }
            return check;
        }

    void loginUser(String username, String password){
            final boolean check;
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("userid",user.getUid());
                            final Intent intent1= new Intent(getApplicationContext(),MapsActivity.class);
                            startActivity(intent1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
