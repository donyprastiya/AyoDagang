package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Admin extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialCardView btnAddStaff, btnEditQty;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initView();
        addStaff();
        editItem();
    }



    private void initView() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnAddStaff = findViewById(R.id.btnAddStaff);
        btnEditQty = findViewById(R.id.btnEditQty);
    }


    private void addStaff() {
        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, RegisterStaff.class);
                startActivity(intent);
            }
        });
    }

    private void editItem() {
        btnEditQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, RegisterBarang.class);
                startActivity(intent);
            }
        });
    }


}