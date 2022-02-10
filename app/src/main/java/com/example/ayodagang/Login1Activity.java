package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login1Activity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtUserName;
    private String emailget;
    private Button btnRegister, btnCoba;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        initView();
        register();
        coba();

    }

    private void coba() {
        btnCoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = edtUserName.getText().toString().trim();

                fStore.collection("Users").whereEqualTo("userName", userName).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        emailget = document.getString("email");
                                        if (emailget != null) {

                                            loginadmin(emailget);
                                        } else {
                                            Toast.makeText(Login1Activity.this,
                                                    "Login Tidak Berhasil!"
                                                    , Toast.LENGTH_LONG).show();
                                        }
                                    }

                                } else {

                                        Toast.makeText(Login1Activity.this,
                                                " Login Tidak Berhasil!"
                                                , Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        );
            }
        });
    }

    private void loginadmin(String emailget) {
        final String emailUser = emailget;
        final String passwordUser = edtPassword.getText().toString().trim();

        auth.signInWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(Login1Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // ketika gagal locin maka akan do something
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login1Activity.this,
                                    "Username dan Password salah"
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            checkUser(uid);
//
                        }
                    }
                });
    }

    private void register() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login1Activity.this, RegisterActivity.class);
                startActivity(intent);


            }
        });

    }

    private void checkUser(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getString("role").equals("1")) {
                        Intent intent = new Intent(Login1Activity.this, MainActivity.class).putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login1Activity.this,
                                "Tidak ada Dokumen"
                                , Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(Login1Activity.this,
                            "DocumentSnapshot data: " + task.getException()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView() {
        edtPassword = findViewById(R.id.edt_password);
        edtUserName = findViewById(R.id.edt_username);
        btnCoba = findViewById(R.id.btn_coba);
        btnRegister = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

//    private void login() {
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //menampung imputan user
//                final String emailUser = edtEmail.getText().toString().trim();
//                final String passwordUser = edtPassword.getText().toString().trim();
//
//                //validasi email dan password
//                // jika email kosong
//                if (emailUser.isEmpty()) {
//                    edtEmail.setError("Email tidak boleh kosong");
//                }
//                // jika email not valid
//                else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
//                    edtEmail.setError("Email tidak valid");
//                }
//                // jika password kosong
//                else if (passwordUser.isEmpty()) {
//                    edtPassword.setError("Password tidak boleh kosong");
//                }
//                //jika password kurang dari 6 karakter
//                else if (passwordUser.length() < 6) {
//                    edtPassword.setError("Password minimal terdiri dari 6 karakter");
//                } else {
//                    auth.signInWithEmailAndPassword(emailUser, passwordUser)
//                            .addOnCompleteListener(Login1Activity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    // ketika gagal locin maka akan do something
//                                    if (!task.isSuccessful()) {
//                                        Toast.makeText(Login1Activity.this,
//                                                "Gagal login karena " + task.getException().getMessage()
//                                                , Toast.LENGTH_LONG).show();
//                                    } else {
//                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                        checkUser(uid);
////
//                                    }
//                                }
//                            });
//                }
//            }
//        });
//    }



}