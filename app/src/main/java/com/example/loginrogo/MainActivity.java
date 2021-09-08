package com.example.loginrogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button btnDangKy,btnXacThucOTP;
    Switch aSwitch;
    String checkEmail;
    ImageView imgGL;
    String checkMatKhau;
    ImageButton imgbtnDangNhap;
    EditText aedtEmail,aedtMatKhau;
    SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDangKy = findViewById(R.id.btnDangKy);
        mAuth = FirebaseAuth.getInstance();
        imgbtnDangNhap= findViewById(R.id.imgbtnDangNhap);
        aedtEmail= findViewById(R.id.edtEmail);
        aedtMatKhau = findViewById(R.id.edtMatKhau);
        aSwitch= findViewById(R.id.swdarkmode);
        imgGL = findViewById(R.id.imgGoogle);
        btnXacThucOTP = findViewById(R.id.btnXacThucOTP);
        btnXacThucOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogbtnxacthucotp(Gravity.CENTER);
            }
        });

        imgGL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScreenActivity.class);
                startActivity(intent);
            }
        });
        checkEmail= aedtEmail.getText().toString();
        checkMatKhau = aedtMatKhau.getText().toString();
imgbtnDangNhap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dangnhap();
    }
});


//        imgbtnDangNhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!(checkEmail.equals("")) || !(checkMatKhau.equals(""))){
//                    Toast.makeText(MainActivity.this,"dung de trong",Toast.LENGTH_SHORT).show();
//                }
//                else if (checkEmail.equals("admin") && checkMatKhau.equals("admin")){
//                    Toast.makeText(MainActivity.this,"dang nhap thanh cong",Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Toast.makeText(MainActivity.this,"Email hoac mat khau khong chinh xac",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DangKyActivity.class);
                startActivity(intent);
                finish();
            }
        });




        sharedPreferences = getSharedPreferences("night",0);
        Boolean aBoolean = sharedPreferences.getBoolean("night_mode",true);
        if (aBoolean){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setChecked(true);

            aSwitch.setText("Dark Mode");
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            aSwitch.setChecked(false);
            aSwitch.setText("Night Mode");
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    aSwitch.setChecked(true);
                    editor.commit();

                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean("night_mode",false);
                    aSwitch.setChecked(false);

                    editor.commit();

                }
            }
        });
    }

    private void dialogbtnxacthucotp(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_otp);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        if (Gravity.BOTTOM==gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        Button btnXacNhan = dialog.findViewById(R.id.btnXacNhan);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Dang gui yeu cau xac thuc",Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
    }

    private void dangnhap() {
        String email = aedtEmail.getText().toString().trim();
        String pass = aedtMatKhau.getText().toString().trim();
//        String rePass = .getText().toString().trim();
        if (email.isEmpty()){
            aedtEmail.setError("nhap email");
            aedtEmail.requestFocus();
            return;
        }
        else if (pass.isEmpty()){
            aedtMatKhau.setError("nhap mat khau");
            aedtMatKhau.requestFocus();
            return;
        }
        else {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"dang nhap thanh cong",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(MainActivity.this,"dang nhap khong thanh cong",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }




}