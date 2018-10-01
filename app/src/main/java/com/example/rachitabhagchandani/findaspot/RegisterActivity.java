package com.example.rachitabhagchandani.findaspot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
      Button signupButton;
      EditText usernameEditText, passwordEditText , emailEditText, phoneEditText;
      private FirebaseAuth mAuth;

     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.register);
         mAuth = FirebaseAuth.getInstance();

         signupButton = (Button) findViewById(R.id.signup);
         signupButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 usernameEditText = (EditText) findViewById(R.id.name);
                 passwordEditText = (EditText) findViewById(R.id.password);
                 phoneEditText = (EditText) findViewById(R.id.phone);
                 emailEditText = (EditText) findViewById(R.id.email);
                 //Use .trim to trim the spaces. Declare variables to be final
                 final String username = usernameEditText.getText().toString();
                 String password = passwordEditText.getText().toString();
                 final String email = emailEditText.getText().toString();
                 final String phone = phoneEditText.getText().toString();
                  boolean check = checkValidation(username, password, email, phone);
                        if(!check){
                            Toast.makeText(getApplicationContext(), "Please enter all the details correctly",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            registerUser(username, password, email, phone);
                            //proceed to login(check from firebase)
                        }
             }
         });

     }

     boolean checkValidation(String usename, String password, String email, String phone){

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

            if(email.isEmpty()){
                emailEditText.setError("Email is required");
                emailEditText.requestFocus();
                check = false;
            }

            if(phone.isEmpty()){
                phoneEditText.setError("Phone is required");
                phoneEditText.requestFocus();
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
            return check;
        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    void registerUser(final String username, String password, final String email, final String phone){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User user = new User(email, username, phone);
                            Log.d("Username", username);
                            Log.d("Email", email);
                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>(){
                                       public void onComplete(@NonNull Task<Void> task){
                                           if(task.isSuccessful()){
                                               Toast.makeText(RegisterActivity.this, "Authentication success.",
                                                       Toast.LENGTH_SHORT).show();
                                           }else{
                                               Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                       Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
