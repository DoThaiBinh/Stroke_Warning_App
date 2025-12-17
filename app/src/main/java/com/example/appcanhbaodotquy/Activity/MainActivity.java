package com.example.appcanhbaodotquy.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcanhbaodotquy.Models.RspnguoidungLogin;
import com.example.appcanhbaodotquy.Models.login;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.api;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView dangki;
    EditText sdt,pass;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);


        btn=findViewById(R.id.buttonLogin);
        dangki=findViewById(R.id.dangki);
        sdt=findViewById(R.id.txtsdt);
        pass=findViewById(R.id.txtpassword);

        sdt.setText(sharedPreferences.getString("sdt", ""));
        pass.setText(sharedPreferences.getString("password", ""));
        if(sharedPreferences.getString("trangthaiview", "").equals("1")){
            dangnhap();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dangnhap();


            }

        });

         dangki.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent= new Intent( MainActivity.this, Register_Activity.class);

                 startActivity(intent);
                 finish();
             }
         });

    }

    public void dangnhap(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sdt", sdt.getText().toString());
        editor.putString("password", pass.getText().toString());
        editor.commit();

        login ngdung= new login();
        ngdung.setId(sdt.getText().toString());
        ngdung.setPass(pass.getText().toString());

        api.apiService.dangNhap(ngdung).enqueue(new Callback<RspnguoidungLogin>() {
            @Override
            public void onResponse(Call<RspnguoidungLogin> call, Response<RspnguoidungLogin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RspnguoidungLogin nd= response.body();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ten", nd.tennd);
                    editor.putString("trangthaiview", "1");
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this, homeActivity.class);
                    startActivity(intent);
                    finish();
                    /*String message = response.body();
                    if (message.contains("ok")) {
                        // đặt trạng thái view
                        //sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                       // editor.putString("ten", message.substring(3));
                        editor.putString("trangthaiview", "1");
                        editor.commit();
                        //
                        Intent intent = new Intent(MainActivity.this, homeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Sai mật khẩu " , Toast.LENGTH_SHORT).show();
                    }*/
                } else {
                    if (response.code() == 400) {
                        try {
                            String errorMessage = response.errorBody() != null ? response.errorBody().string() : "tk đã tồn tại";

                                Toast.makeText(MainActivity.this, "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, "Lỗi khi xử lý phản hồi", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Lỗi máy chủ: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RspnguoidungLogin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Không có kết nối internet: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Failure: " + t.getMessage() + "\nStackTrace: " + Log.getStackTraceString(t));}
        });


    }

}