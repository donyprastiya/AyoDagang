package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
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
    private TextView btnLoginUser, btnLoginStaff;
    private MaterialCardView btnCoba;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        initView();
        coba();

    }

    private void coba() {
        btnCoba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = edtUserName.getText().toString().trim();
                final String passwordUser = edtPassword.getText().toString().trim();

                if (userName.isEmpty()) {
                    edtUserName.setError("Username tidak boleh kosong");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()) {
                    edtPassword.setError("Password tidak boleh kosong");
                } else {
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
                                                                           "1 Login Tidak Berhasil!" + task.getException()
                                                                           , Toast.LENGTH_LONG).show();
                                                               }
                                                           }

                                                       } else {

                                                           Toast.makeText(Login1Activity.this,
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

    private void checkUser(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.getString("role").equals("1")) {
                        Intent intent = new Intent(Login1Activity.this, Admin.class).putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login1Activity.this,
                                "Anda bukan admin!"
                                , Toast.LENGTH_LONG).show();
                    }
                } else {
                    auth.signOut();
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
        btnCoba = findViewById(R.id.btnLogin);
        btnLoginStaff = findViewById(R.id.loginStaff);
        btnLoginUser = findViewById(R.id.loginUser);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }


    public void userLogin(View view) {
        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login1Activity.this, LoginUser.class);
                startActivity(intent);
            }
        });
    }

    public void staffLogin(View view) {
        btnLoginStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login1Activity.this, LoginStaff.class);
                startActivity(intent);
            }
        });
    }

//

}