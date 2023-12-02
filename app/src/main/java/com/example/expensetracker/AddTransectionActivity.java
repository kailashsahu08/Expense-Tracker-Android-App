package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddTransectionActivity extends AppCompatActivity {
    //creating a firebaseFirestore Object
    private FirebaseFirestore fstore;
    //creation of firebase auth and firebase user object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String type = "";
    private EditText user_amount_add,user_description_add;
    private CheckBox income_checkbox,expense_chekbox;
    private Button add_transection_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transection);
        user_amount_add = findViewById(R.id.user_amount_add);
        user_description_add = findViewById(R.id.user_description_add);
        income_checkbox = findViewById(R.id.income_checkbox);
        expense_chekbox = findViewById(R.id.expense_checkbox);
        add_transection_btn = findViewById(R.id.add_transection_btn);
        //instantiate firebaseFireStore Object
        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        add_transection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = user_amount_add.getText().toString().trim();
                String desc = user_description_add.getText().toString().trim();
                if(income_checkbox.isChecked()){
                    type = "Income";
                }
                else if(expense_chekbox.isChecked()){
                    type = "Expense";
                }
                else {
                    Toast.makeText(AddTransectionActivity.this, "Select Transection Type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(amount.length()<=0){
                    return;
                }
                //create  uid
                String id = UUID.randomUUID().toString();
                //creating a date format object
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
                String currDate = sdf.format(new Date());

                //creating a collecttion of data
                Map<String,Object> transection = new HashMap<>();
                transection.put("Id",id);
                transection.put("Amount",amount);
                transection.put("Desc",desc);
                transection.put("Type",type);
                transection.put("date",currDate);
                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes").document(id)
                        .set(transection)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddTransectionActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                user_amount_add.setText("");
                                user_description_add.setText("");
                                income_checkbox.setChecked(false);
                                expense_chekbox.setChecked(false);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddTransectionActivity.this, "Failed To Insert Data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}