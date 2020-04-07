package com.project.designeng.e_healthcare;

import android.content.Intent;
/*import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button signUpButton;
    String email, password;
    TextInputEditText emailEditText, nameEditText, passwordEditText, confirmpasswordEditText, mobNoEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email);
        nameEditText = findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        confirmpasswordEditText = findViewById(R.id.confirm_password);
        mobNoEditText = findViewById(R.id.phone_no);
        signUpButton = findViewById(R.id.email_sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                boolean a=false;
                password = passwordEditText.getText().toString();
                    if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        emailEditText.setError("Email is empty or not valid ");
                        a=true;
                    }
                    if(nameEditText.getText().toString().isEmpty()){
                        nameEditText.setError("Name can't be empty!");
                        a=true;
                    }
                    if(password.isEmpty() || password.length()<6) {
                        passwordEditText.setError("Password can't be less than 6");
                        a = true;
                    }
                    if(!confirmpasswordEditText.getText().toString().equals(password)){
                        confirmpasswordEditText.setError("confirm password didn't matched");
                        a = true;
                    }
                    if(mobNoEditText.getText().toString().isEmpty() || mobNoEditText.getText().toString().length() !=10){
                        mobNoEditText.setError("Mobile number is invalid!");
                        a = true;
                    }
                    if(LoginActivity.isConnected(SignUpActivity.this) && !a){
                        createNewUser(email,password);
                    }
            }
        });
    }

    private void createNewUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Sign up status", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this,ForPatientActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w("sign up status", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.\n"+task.getException(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
