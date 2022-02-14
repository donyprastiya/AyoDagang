package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ayodagang.model.Barang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListProduk extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private RecyclerView mRecyclerView;
    private ArrayList<Barang> mSportsData;
    private BarangAdapter mAdapter;
    private final ArrayList<Barang> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);

        fStore = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new BarangAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        viewData();
    }

    private void viewData() {
        String category = getIntent().getStringExtra("type");

        Toast.makeText(ListProduk.this,
                category
                , Toast.LENGTH_LONG).show();

        fStore.collection("Items").whereEqualTo("sub_category", category).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Barang barang = new Barang(document.getString("brand"), document.getString("img"), document.getString("name"), document.getString("qty"), document.getString("sub_catagory"));
                                productList.add(barang);
                            }
                            mAdapter = new BarangAdapter( ListProduk.this, productList);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {

                            Toast.makeText(ListProduk.this,
                                    "DATA GAADA BOY!" + task.getException()
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                });

//        fStore.collection("Items").whereEqualTo("sub_category", category).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Barang barang = new Barang(document.getString("brand"), document.getString("img"), document.getString("name"), document.getString("qty"), document.getString("sub_catagory"));
//                                productList.add(barang);
//                            }
//                            mAdapter = new BarangAdapter( ListProduk.this, productList);
//                            mRecyclerView.setAdapter(mAdapter);
//                        } else {
//
//                            Toast.makeText(ListProduk.this,
//                                    "DATA GAADA BOY!" + task.getException()
//                                    , Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
    }

}