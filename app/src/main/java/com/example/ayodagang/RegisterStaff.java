package com.example.ayodagang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterStaff extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private EditText editUsername,editPhone;
    private MaterialCardView btnSubmit;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_staff);

        initView();
        registerUser();
    }

    private void registerUser() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menampung imputan user
                String emailUser = edtEmail.getText().toString().trim();
                String passwordUser = edtPassword.getText().toString().trim();
                String userName = editUsername.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                //validasi email dan password
                // jika email kosong
                if (userName.isEmpty()){
                    editUsername.setError("Username tidak boleh kosong");
                } else if (phone.isEmpty()) {
                    editPhone.setError("Nomor tidak boleh kosong");
                } else if (emailUser.isEmpty()) {
                    edtEmail.setError("Email tidak boleh kosong");
                }
                // jika email not valid
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                    edtEmail.setError("Email tidak valid");
                }
                // jika password kosong
                else if (passwordUser.isEmpty()){
                    edtPassword.setError("Password tidak boleh kosong");
                }
                //jika password kurang dari 6 karakter
                else if (passwordUser.length() < 6){
                    edtPassword.setError("Password minimal terdiri dari 6 karakter");
                }

                else {
                    CollectionReference usersRef = fStore.collection("Users");
                    Query query = usersRef.whereEqualTo("userName", userName);
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for(DocumentSnapshot documentSnapshot : task.getResult()){
                                    String userCheck = documentSnapshot.getString("userName");
                                    if(userCheck.equals(userName)){
                                        Toast.makeText(RegisterStaff.this, "USERNAME SUDAH ADA!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            if(task.getResult().size() == 0 ){

                                CollectionReference phoneRef = fStore.collection("Users");
                                Query query = phoneRef.whereEqualTo("phone", phone);
                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            for(DocumentSnapshot documentSnapshot : task.getResult()){
                                                String phoneCheck = documentSnapshot.getString("phone");
                                                if(phoneCheck.equals(phone)){
                                                    Toast.makeText(RegisterStaff.this, "NOMOR UDAH ADA!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                        if(task.getResult().size() == 0 ){

                                            authRegister();
                                        }
                                    }
                                });
                            }
                        }
                    });
                    //create user dengan firebase auth

                }
            }
        });
    }

    private void authRegister(){
        String emailUser = edtEmail.getText().toString().trim();
        String passwordUser = edtPassword.getText().toString().trim();
        String userName = editUsername.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();

        auth.createUserWithEmailAndPassword(emailUser,passwordUser)
                .addOnCompleteListener(RegisterStaff.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //jika gagal register do something
                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterStaff.this,
                                    "Register gagal karena "+ task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }else {
                            //CHECK USERNAME DAN PHONE ADA APA ENGGA
                            //You can store new user information here
                            FirebaseUser user = auth.getCurrentUser();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("email", emailUser);
                            userInfo.put("userName", userName);
                            userInfo.put("phone", phone);
                            //ROLE USER
                            userInfo.put("role", "2");

                            df.set(userInfo);


                            Toast.makeText(RegisterStaff.this,
                                    "Membuat staff baru berhasil!",
                                    Toast.LENGTH_LONG).show();
                            //jika sukses akan menuju ke login activity
                            startActivity(new Intent(RegisterStaff.this,RegisterStaff.class));
                        }
                    }
                });
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_email_register5);
        edtPassword = findViewById(R.id.edt_password_register5);
        btnSubmit = findViewById(R.id.btnSubmitStaff);
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        editUsername = findViewById(R.id.edt_username5);
        editPhone = findViewById(R.id.edt_phone5);

    }
}