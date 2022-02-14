package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterBarang extends AppCompatActivity {

    private EditText editUID,editQty;
    private MaterialCardView btnRegisStock, btnSubmit;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_barang);

        editUID = findViewById(R.id.uidItem);
        editQty = findViewById(R.id.qtyItem);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnSubmit = findViewById(R.id.btnRegisStock);

        editStock();
    }

    private void editStock() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uid = editUID.getText().toString().trim();
                final String qty = editQty.getText().toString().trim();

                if (uid.isEmpty()) {
                    editUID.setError("ID tidak boleh kosong");
                }
                // jika password kosong
                else if (qty.isEmpty()) {
                    editQty.setError("Stock tidak boleh kosong");
                } else{
                    fStore.collection("Items").document(uid).update("qty",
                    qty).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterBarang.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                                editQty.setText("");
                                editUID.setText("");
                            }else {
                                Toast.makeText(RegisterBarang.this, "Update Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}