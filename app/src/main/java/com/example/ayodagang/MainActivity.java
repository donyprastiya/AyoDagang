package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnLogOut;
    private FirebaseFirestore fStore;
    private static ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String uid = getIntent().getStringExtra("uid");

        auth = FirebaseAuth.getInstance();
        btnLogOut = findViewById(R.id.btnLogout);

        TextView tvUser = findViewById(R.id.tv_user);
        tvUser.setText(" "+uid);

//        getData();

    }

    private void getData() {
        Bundle bundle = getIntent().getBundleExtra("emailpass");
        String email = bundle.getString("email");
        String password = bundle.getString("pass");

        fStore.collection("Users").whereEqualTo("email", email).get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Toast.makeText(MainActivity.this,
                                document.getId() + "INI DATA " + document.getData()
                                , Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "ERROR SOB "
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void logout(View view) {
        auth.signOut();
    }


}