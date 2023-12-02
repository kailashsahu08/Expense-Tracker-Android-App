package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth LogInfirebaseAuth;
    private TextView goToSignupPage,forgotPassword;
    private  EditText emailForLogin,passwordForLogin;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToSignupPage =  findViewById(R.id.goToSignupPage);
        emailForLogin = findViewById(R.id.emailForLogin);
        passwordForLogin = findViewById(R.id.passwordForLogin);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        LogInfirebaseAuth =  FirebaseAuth.getInstance();
        LogInfirebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(LogInfirebaseAuth.getCurrentUser()!=null){
                    try{
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        finish();
                    }
                    catch(Exception e){
                        Toast.makeText(MainActivity.this, "Error Occur in Log In Page", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        goToSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSignUpPage = new Intent(MainActivity.this,SignUpActivity.class);
                try{
                    startActivity(toSignUpPage);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailForLogin.getText().toString().trim();
                String pass = passwordForLogin.getText().toString().trim();
                if(email.length() == 0 || pass.length() == 0){
                    Toast.makeText(MainActivity.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                }
                LogInfirebaseAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        try{
                            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        }
                        catch(Exception e){
                            Toast.makeText(MainActivity.this, "Error Occurred During Intent Passing", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Log in Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}