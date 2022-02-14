package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryElectronic extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialCardView btnLaptop, btnPhone;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_electronic);

        initView();
        toLaptop();
        toePhone();
    }

    private void toLaptop() {
        btnLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "computer";
                Intent intent = new Intent(CategoryElectronic.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLaptop = findViewById(R.id.btnComputer);
        btnPhone = findViewById(R.id.btnPhone);
    }

    private void toePhone() {
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "phone";
                Intent intent = new Intent(CategoryElectronic.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
}