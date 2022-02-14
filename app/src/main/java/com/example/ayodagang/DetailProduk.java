package com.example.ayodagang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailProduk extends AppCompatActivity {

    private TextView textViewProductName;
    private TextView textViewProductBrand;
    private TextView textViewQuantity;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk);

        textViewProductName     = findViewById(R.id.textViewProductName);
        textViewProductBrand    = findViewById(R.id.textViewProductBrand);
        textViewQuantity        = findViewById(R.id.textViewProductQty);
        image                   = findViewById(R.id.imageDetail);
        textViewProductName.setText(getIntent().getStringExtra("name"));
        textViewProductBrand.setText(getIntent().getStringExtra("brand"));
        textViewQuantity.setText(getIntent().getStringExtra("qty"));
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(image);
    }


}