package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class updateActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private  String newType;
    private Button update_transection_btn,delete_transection_btn;
    private EditText update_amount,update_description;
    private CheckBox update_income_checkbox,update_expense_checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();
        Intent in = getIntent();
        String id = in.getStringExtra("id");
        String amount = in.getStringExtra("amount");
        String desc = in.getStringExtra("desc");
        String type = in.getStringExtra("type");
        newType = type;
        update_amount.setText(amount);
        update_description.setText(desc);
        switch(type)
        {
            case "Income":
                update_income_checkbox.setChecked(true);
                break;
            case "Expense":
                update_expense_checkbox.setChecked(true);
                break;
        }
        update_income_checkbox.setOnClickListener(view -> {
            newType = "Income";
            update_income_checkbox.setChecked(true);
            update_expense_checkbox.setChecked(false);
        });
        update_expense_checkbox.setOnClickListener(view -> {
            newType = "Expense";
            update_income_checkbox.setChecked(false);
            update_expense_checkbox.setChecked(true);
        });
        update_transection_btn.setOnClickListener(view -> {
            String amt = update_amount.getText().toString().trim();
            String note = update_description.getText().toString().trim();
            firestore.collection("Expenses").document(firebaseAuth.getUid())
                    .collection("Notes").document(id).update("Amount",amt,"Desc",note,"Type",newType)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(updateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(updateActivity.this, "Failed To Update", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        delete_transection_btn.setOnClickListener(view -> {
            firestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                    .document(id).delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                            Toast.makeText(updateActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(updateActivity.this, "Error Occur", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
    public void init(){
        update_transection_btn =  findViewById(R.id.update_transection_btn);
        delete_transection_btn = findViewById(R.id.delete_transection_btn);
        update_amount = findViewById(R.id.user_amount_update);
        update_description = findViewById(R.id.user_description_update);
        update_income_checkbox = findViewById(R.id.update_income_checkbox);
        update_expense_checkbox = findViewById(R.id.update_expense_checkbox);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }
}