package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcanhbaodotquy.Models.thietBiFull;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.adapter.device_adapter;
import com.example.appcanhbaodotquy.api;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeActivity extends AppCompatActivity {
    ImageView add, setting;
    ListView thietbi;
    TextView tv,ten;
    private device_adapter dvAdapter;
    private ArrayList<thietBiFull> dvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Không lấy được token!", task.getException());
                        return;
                    }

                    String fcmToken = task.getResult();


                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    String userId = sharedPreferences.getString("sdt", "0");


                    api.apiService.saveFcmToken(userId, fcmToken)
                            .enqueue(new retrofit2.Callback<okhttp3.ResponseBody>() {
                                @Override
                                public void onResponse(Call<okhttp3.ResponseBody> call, retrofit2.Response<okhttp3.ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Log.d("FCM", "Token FCM đã được gửi lên server");
                                    } else {
                                        Log.e("FCM", "Lỗi server: " + response.code());
                                    }
                                }
                                @Override
                                public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
                                    Log.e("FCM", "Lỗi gửi API: " + t.getMessage());
                                }
                            });
                });

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String id = sharedPreferences.getString("sdt", "0");
        String t = sharedPreferences.getString("ten", "");

        add = findViewById(R.id.adddevice);
        setting = findViewById(R.id.st);
        tv = findViewById(R.id.textView7);
        ten = findViewById(R.id.ten);
        ten.setText("Xin chào "+t);
        add.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, add_deviceActivity.class);
            startActivity(intent);
        });

        setting.setOnClickListener(view -> {
            Intent intent = new Intent(homeActivity.this, setting_AccountActivity.class);
            startActivity(intent);
        });

        thietbi = findViewById(R.id.thietbi);
        thietbi.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(homeActivity.this, detail_device_Activity.class);
            startActivity(intent);
        });

        dvList = new ArrayList<>();


        if (id == null || id.equals("0")) {
            Toast.makeText(homeActivity.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        api.apiService.getThietBi(id).enqueue(new Callback<List<thietBiFull>>() {
            @Override
            public void onResponse(Call<List<thietBiFull>> call, Response<List<thietBiFull>> response) {
                if (response.isSuccessful()) {
                    List<thietBiFull> devices = response.body();
                    if (devices != null && !devices.isEmpty()) {
                        dvList.clear();
                        dvList.addAll(devices);
                        dvAdapter.notifyDataSetChanged(); // Cập nhật giao diện sau khi thay đổi dữ liệu
                        //Toast.makeText(homeActivity.this, "Thành công gọi API", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(homeActivity.this, "Dữ liệu trống", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(homeActivity.this, "Lỗi từ server: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<thietBiFull>> call, Throwable t) {


                Toast.makeText(homeActivity.this, "Lỗi  kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

// Khởi tạo adapter chỉ một lần trong activity (nếu chưa khởi tạo)
        if (dvAdapter == null) {
            dvAdapter = new device_adapter(homeActivity.this, dvList);
            thietbi.setAdapter(dvAdapter);
        }
        thietbi.setOnItemClickListener((parent, view, position, l) ->{
        thietBiFull selectItem= dvList.get(position);
            Intent intent = new Intent(homeActivity.this, detail_device_Activity.class);
            intent.putExtra("key_thietbi", selectItem);
            startActivity(intent);
        });


    }
}
