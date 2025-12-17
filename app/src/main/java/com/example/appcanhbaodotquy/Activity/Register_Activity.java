package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcanhbaodotquy.Models.requestRegister;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Activity extends AppCompatActivity {

    TextView dn,t,sdt,em,pass;
    Button dk;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dn=findViewById(R.id.dangnhap);
        t=findViewById(R.id.ten);
        sdt=findViewById(R.id.sdt);
        em=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        dk=findViewById(R.id.dk);

        dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( Register_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRegister k= new requestRegister();
                k.setId(sdt.getText().toString());
                k.setName(t.getText().toString());
                k.setEmail(em.getText().toString());
                k.setPassword(pass.getText().toString());
                api.apiService.DangKiTaiKhoan(k).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful() && response.body() != null){
                            if(response.body().contains("ok")){
                                sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);
                               SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("sdt", sdt.getText().toString());
                                editor.putString("password", pass.getText().toString());
                                editor.putString("ten", t.getText().toString());
                                editor.putString("trangthaiview", "0");
                                editor.commit();
                                Intent intent= new Intent( Register_Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(Register_Activity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(Register_Activity.this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Register_Activity.this, "Lỗi kết nối internet!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}