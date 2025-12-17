package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcanhbaodotquy.Models.RequestAddThietBi;
import com.example.appcanhbaodotquy.Models.thietBiFull;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class setting_inf_deviceActivity extends AppCompatActivity {
    ImageView imgback;
    TextView idthietbi;
    Button btnok;
    EditText ten, tuoi ,cao, nang, sodieu1ngay,cholesterol;
    SharedPreferences sharedPreferences;
    Spinner spinnerGioiTinh,  spinnerDaTungBiDotQuy, spinnerHutThuoc, spinnerTieuDuong, spinnerBiTangHA, spinnerDungThuocHA, spinnergiaoDuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        thietBiFull selectItem = (thietBiFull) intent.getSerializableExtra("key_thietbisetting");

        addControls();

        imgback= findViewById(R.id.back);
        ten= findViewById(R.id.tennguoidung);
        tuoi= findViewById(R.id.tuoi);
        //tuoi.setText(selectItem.getTuoi());
        idthietbi= findViewById(R.id.idThietBi);
        cao= findViewById(R.id.cao);
        nang= findViewById(R.id.cannang);
        idthietbi.setText(selectItem.getIdThietBi().toString());
        //ten.setText(selectItem.getTenNguoiDung());
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(setting_inf_deviceActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });
        btnok = findViewById(R.id.btnxong);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //xử lý insert tại dây
                addEvents();
            }
        });
    }


    private void addControls() {
        spinnerGioiTinh = findViewById(R.id.gioitinh);
        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Nam", "Nữ"}
        );
        adapterGioiTinh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGioiTinh.setAdapter(adapterGioiTinh);

        ArrayAdapter<String> adapterCoKhong = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Có", "Không"}
        );
        ArrayAdapter<String> adapterGiaoDuc = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Không", "Trung học","Đại học","Sau đại học"}
        );
        //fasdf
        adapterCoKhong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTieuDuong = findViewById(R.id.tieuduong);
        spinnerTieuDuong.setAdapter(adapterCoKhong);
        spinnerTieuDuong.setSelection(1);

        spinnerDaTungBiDotQuy = findViewById(R.id.tiensu);
        spinnerDaTungBiDotQuy.setAdapter(adapterCoKhong);
        spinnerDaTungBiDotQuy.setSelection(1);

        spinnerHutThuoc = findViewById(R.id.hutthuoc);
        spinnerHutThuoc.setAdapter(adapterCoKhong);
        spinnerHutThuoc.setSelection(1);

        spinnerTieuDuong = findViewById(R.id.tieuduong);
        spinnerTieuDuong.setAdapter(adapterCoKhong);
        spinnerTieuDuong.setSelection(1);

        spinnerBiTangHA = findViewById(R.id.tanghuyetap);
        spinnerBiTangHA.setAdapter(adapterCoKhong);
        spinnerBiTangHA.setSelection(1);

        spinnerDungThuocHA = findViewById(R.id.dungthuocHA);
        spinnerDungThuocHA.setAdapter(adapterCoKhong);
        spinnerDungThuocHA.setSelection(1);

        spinnergiaoDuc = findViewById(R.id.giaoDuc);
        spinnergiaoDuc.setAdapter(adapterGiaoDuc);
        spinnergiaoDuc.setSelection(1);

    }

    private void addEvents(){
        RequestAddThietBi x= new RequestAddThietBi();
        sharedPreferences= getSharedPreferences("data", MODE_PRIVATE);
        x.setSdt(sharedPreferences.getString("sdt", ""));
        x.setTen(ten.getText().toString());
        x.setTuoi(Integer.parseInt(tuoi.getText().toString()));
        x.setIdThietBi(idthietbi.getText().toString());
        x.setGioitinh(chuyen(spinnerGioiTinh.getSelectedItem().toString()));
        x.setDaBiDotQuy(chuyen(spinnerDaTungBiDotQuy.getSelectedItem().toString()));
        x.setHutThuoc(chuyen(spinnerHutThuoc.getSelectedItem().toString()));
        x.setSoDieu1Ngay(Integer.parseInt(sodieu1ngay.getText().toString()));
        x.setTieuDuong(chuyen(spinnerTieuDuong.getSelectedItem().toString()));
        x.setTangHuyetAp(chuyen(spinnerBiTangHA.getSelectedItem().toString()));
        x.setDungThuocHuyetAp(chuyen(spinnerDungThuocHA.getSelectedItem().toString()));
        x.setGiaoDuc(chuyenGiaoDuc(spinnergiaoDuc.getSelectedItem().toString()));
        x.setCholesterol(Integer.parseInt(cholesterol.getText().toString()));

        double c=  Double.parseDouble(cao.getText().toString());
        double n=  Double.parseDouble(nang.getText().toString());
        x.setBmi(n/(c*c));
        //x.setBmi(2.3);
        api.apiService.SuaThietBi(x).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    if(response.body().equals("ok")){
                        Intent intent= new Intent(setting_inf_deviceActivity.this,homeActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(setting_inf_deviceActivity.this, "Không thể thêm thiết bị"+response.code(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(setting_inf_deviceActivity.this, "Không có kết nối internet"+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean chuyen( String k){
        if (k.equals("Nam")|| k.equals("Có")){
            return true;
        }
        return false;
    }
    private int chuyenGiaoDuc( String k){
        if (k.equals("Không")){
            return 0;
        } else if (k.equals("Trung học")) {
            return 1;
        }else if (k.equals("Đại học")) {
            return 2;
        }
        else return 3;
    }

}