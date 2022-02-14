package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Category1 extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialCardView btnClothing, btnElectronic, btnBook, btnOthers;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categori1);

        initView();
        toclothing();
        toelectronic();
        tobook();
        toothers();
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnClothing = findViewById(R.id.cate1);
        btnElectronic = findViewById(R.id.cate2);
        btnBook = findViewById(R.id.cate3);
        btnOthers = findViewById(R.id.cate4);
    }
    private void toothers() {
        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "others";
                Intent intent = new Intent(Category1.this, MainActivity.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
    private void tobook() {
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "book";
                Intent intent = new Intent(Category1.this, MainActivity.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
    private void toelectronic() {
        btnElectronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "electronic";
                Intent intent = new Intent(Category1.this, CategoryElectronic.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void toclothing() {
        btnClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "clothing";
                Intent intent = new Intent(Category1.this, CategoryClothing.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }
}