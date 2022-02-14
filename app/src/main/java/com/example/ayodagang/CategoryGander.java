package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryGander extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialCardView btnMen, btnWomen;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gander);

        initView();
        toMen();
        toeWomen();
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnMen = findViewById(R.id.btnMen);
        btnWomen = findViewById(R.id.btnWomen);
    }
    private void toMen() {
        btnMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "men";
                Intent intent = new Intent(CategoryGander.this, CategoryClothing.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
    private void toeWomen() {
        btnWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "women";
                Intent intent = new Intent(CategoryGander.this, CategoryClothing.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
}