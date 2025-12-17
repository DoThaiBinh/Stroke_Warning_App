package com.example.appcanhbaodotquy.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.appcanhbaodotquy.R;

public class setting_AccountActivity extends AppCompatActivity {
    private Button save,dx;
    private TextView t, sdt;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);
        //SharedPreferences sharedPreferences1 = getSharedPreferences("data", MODE_PRIVATE);
        String id = sharedPreferences.getString("sdt", "0");
        String ten = sharedPreferences.getString("ten", "0");

        dx=findViewById(R.id.dangxuat);
        t=findViewById(R.id.ten);
        sdt=findViewById(R.id.sdt);
        t.setText("Tên: "+ten);
        sdt.setText("Số điện thoại: "+id);
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(setting_AccountActivity.this, homeActivity.class);
//                startActivity(intent);
//            }
//        });
        dx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("trangthaiview", "0");
                editor.commit();
                Intent intent= new Intent(setting_AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}