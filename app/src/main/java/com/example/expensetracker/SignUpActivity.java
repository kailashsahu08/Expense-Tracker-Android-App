package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailForSignup,passwordForSignup,reEnteredPasswordForSignup;
    private TextView goToLogInPage;
    private Button signupButton;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailForSignup = findViewById(R.id.emailForSignup);
        passwordForSignup = findViewById(R.id.passwordForSignup);
        reEnteredPasswordForSignup = findViewById(R.id.reEnteredPasswordForSignup);
        goToLogInPage =  findViewById(R.id.goToLogInPage);
        signupButton = findViewById(R.id.signupButton);
        firebaseAuth = FirebaseAuth.getInstance();
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailForSignup.getText().toString();
                String pass = passwordForSignup.getText().toString();
                String repass = reEnteredPasswordForSignup.getText().toString();
                if(email.trim().length()<=0 || pass.trim().length()<=0){
                    Toast.makeText(SignUpActivity.this, "Not Entered Any Email Or Password", Toast.LENGTH_SHORT).show();
                }
                else  if(!pass.equals(repass)){
                    Toast.makeText(SignUpActivity.this, "Entered Password Are Mismatch", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignUpActivity.this, "User Crated", Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUpActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        goToLogInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoginPage = new Intent(SignUpActivity.this,MainActivity.class);
                try{
                    startActivity(toLoginPage);
                }
                catch(Exception e){

                }
            }
        });
    }
}