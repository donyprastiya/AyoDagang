package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginUser extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtUserName;
    private String emailget;
    private TextView btnRegister, btnLoginUser, btnLoginStaff;
    private MaterialCardView btnCoba;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        initView();
        register();
        coba();

    }

    private void coba() {
        btnCoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = edtUserName.getText().toString().trim();
                final String passwordUser = edtPassword.getText().toString().trim();

                if (userName.isEmpty()) {
                    edtUserName.setError("Nomor tidak boleh kosong");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()) {
                    edtPassword.setError("Password tidak boleh kosong");
                } else {

                    fStore.collection("Users").whereEqualTo("phone", userName).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                           if (task.isSuccessful()) {
                                                               for (QueryDocumentSnapshot document : task.getResult()) {

                                                                   emailget = document.getString("email");
                                                                   if (emailget != null) {

                                                                       loginadmin(emailget);
                                                                   } else {
                                                                       Toast.makeText(LoginUser.this,
                                                                               "1 Login Tidak Berhasil!" + task.getException()
                                                                               , Toast.LENGTH_LONG).show();
                                                                   }
                                                               }

                                                           } else {

                                                               Toast.makeText(LoginUser.this,
                                                                       "2 Login Tidak Berhasil!" + task.getException()
                                                                       , Toast.LENGTH_LONG).show();
                                                           }
                                                       }

                                                   }
                            );
                }
            }
        });
    }

    private void loginadmin(String emailget) {
        final String emailUser = emailget;
        final String passwordUser = edtPassword.getText().toString().trim();

        auth.signInWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener(LoginUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // ketika gagal locin maka akan do something
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginUser.this,
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
                Intent intent = new Intent(LoginUser.this, RegisterActivity.class);
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
                    if (document.getString("role").equals("3")) {
                        Intent intent = new Intent(LoginUser.this, CategoryShop.class).putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    } else {
                        auth.signOut();
                        Toast.makeText(LoginUser.this,
                                "Anda bukan user!"
                                , Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginUser.this,
                            "DocumentSnapshot data: " + task.getException()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView() {
        edtPassword = findViewById(R.id.edt_password);
        edtUserName = findViewById(R.id.edt_username);
        btnCoba = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.txtSignUp);
        btnLoginStaff = findViewById(R.id.loginStaff2);
        btnLoginUser = findViewById(R.id.loginAdmin2);
        btnRegister = findViewById(R.id.txtSignUp);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public void register(View view) {
        register();
    }

    public void userLogin(View view) {
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUser.this, Login1Activity.class);
                startActivity(intent);
            }
        });
    }

    public void staffLogin(View view) {
        btnLoginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUser.this, LoginStaff.class);
                startActivity(intent);
            }
        });
    }
}