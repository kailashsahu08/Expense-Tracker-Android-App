package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private ImageView refresh_btn,logout_btn;
    private RecyclerView history_recycler_view;
    private TextView total_balance,total_income,total_expense;
    private CardView add_floating_btn ;
    private int incomeSum=0,expenseSum=0;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private ArrayList<TransectionModel> transectionModelArrayList;
    private TransectionAdapter transectionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        add_floating_btn = findViewById(R.id.add_floating_btn);
        history_recycler_view =  findViewById(R.id.history_recycler_view);
        total_balance=findViewById(R.id.total_balance);
        total_income = findViewById(R.id.total_income);
        total_expense = findViewById(R.id.total_expense);
        refresh_btn = findViewById(R.id.refresh_btn);
        logout_btn = findViewById(R.id.logout_btn);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transectionModelArrayList = new ArrayList<>();

        history_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        history_recycler_view.setHasFixedSize(true);
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                    finish();
                }
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
            }
        });
        refresh_btn.setOnClickListener(view -> {
            try{
                startActivity(new Intent(DashboardActivity.this,DashboardActivity.class));
                finish();
            }catch(Exception e){
                Toast.makeText(this, "Error Occur During Refresh Page", Toast.LENGTH_SHORT).show();
            }
        });
        add_floating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(new Intent(DashboardActivity.this,AddTransectionActivity.class));
                }
                catch(Exception e){
                    Toast.makeText(DashboardActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadData();

    }
    private void loadData(){
        firestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot ds : task.getResult()) {
                            TransectionModel model = new TransectionModel(
                                    ds.getString("Id"),
                                    ds.getString("Amount"),
                                    ds.getString("Desc"),
                                    ds.getString("Type"),
                                    ds.getString("date")
                            );
                            transectionModelArrayList.add(model);
                            int amount = Integer.parseInt(ds.getString("Amount"));
                            if(ds.getString("Type").equals("Expense")){
                                expenseSum = expenseSum+amount;
                            }
                            else{
                                incomeSum = incomeSum+amount;
                            }
                        }
                        total_income.setText(String.valueOf(incomeSum));
                        total_expense.setText(String.valueOf(expenseSum));
                        total_balance.setText(String.valueOf(incomeSum-expenseSum));
                        transectionAdapter = new TransectionAdapter(DashboardActivity.this,transectionModelArrayList);
                        history_recycler_view.setAdapter(transectionAdapter);
                    }
                });

    }
}