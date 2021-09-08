package com.example.loginrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button abtnBackDangNhap,btnDangKy;
    private EditText edtEmail,edtpass;
    private ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        btnDangKy= findViewById(R.id.btnDangKy1);
        abtnBackDangNhap=findViewById(R.id.btnBackDangNhap1);
        edtEmail = findViewById(R.id.edtEmail1);
        edtpass=findViewById(R.id.edtMatKhau1);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this,MainActivity.class));
                finish();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CcreateUser();
            }
        });


        abtnBackDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this,MainActivity.class));

            }
        });
    }

    private void CcreateUser() {
//        String email= edtEmail.getText().toString();
//        String pass = edtpass.getText().toString();
        String email = edtEmail.getText().toString();
        String pass = edtpass.getText().toString();
        if (email.isEmpty()){
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        else if (pass.isEmpty()){
            edtpass.setError("Password is required");
            edtpass.requestFocus();
            return;
        }


        else {
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(DangKyActivity.this,"dang ky thanh cong",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangKyActivity.this,MainActivity.class));

                    }else {
                        Toast.makeText(DangKyActivity.this,"dang ky khong thanh cong",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


}