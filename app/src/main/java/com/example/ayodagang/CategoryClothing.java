package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryClothing extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialCardView btnT, btnF, btnB, btnS;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_clothing);

        initView();
        toTshirt();
        toFormal();
        toBottom();
        toShoes();

    }

    private String menWomen(String gen) {

        String gender;
        if (gen.equals("men")){
            gender = "m";
        } else {
            gender = "w";
        }
        return gender;
    }


    private void toShoes() {

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pilih = getIntent().getStringExtra("type");
                String gen = menWomen(pilih);
                String type = "shoes"+gen;
                Intent intent = new Intent(CategoryClothing.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void toBottom() {
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pilih = getIntent().getStringExtra("type");
                String gen = menWomen(pilih);
                String type = "bottom"+gen;
                Intent intent = new Intent(CategoryClothing.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void toFormal() {
        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pilih = getIntent().getStringExtra("type");
                String gen = menWomen(pilih);
                String type = "formal"+gen;
                Intent intent = new Intent(CategoryClothing.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void toTshirt() {
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pilih = getIntent().getStringExtra("type");
                String gen = menWomen(pilih);
                String type = "tshirt"+gen;
                Intent intent = new Intent(CategoryClothing.this, ListProduk.class).putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnT = findViewById(R.id.btnTshirt);
        btnF = findViewById(R.id.btnTFormal);
        btnB = findViewById(R.id.btnBottom);
        btnS = findViewById(R.id.btnShoes);
    }
}