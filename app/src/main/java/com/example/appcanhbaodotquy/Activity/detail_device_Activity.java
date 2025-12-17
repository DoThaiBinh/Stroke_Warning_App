package com.example.appcanhbaodotquy.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appcanhbaodotquy.Models.thongSodevice;
import com.example.appcanhbaodotquy.Models.thietBiFull;
import com.example.appcanhbaodotquy.R;
import com.example.appcanhbaodotquy.api;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_device_Activity extends AppCompatActivity {
    ImageView setting, bk, te;
    TextView duong, nhietdo, canhbao, o2, ten, nhiptim,huyetap;
    private LineChart lineChart, oxiInBloodChart;
    Button vt;
    int x1;
    public ArrayList<thongSodevice> dvList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    thietBiFull selectItem;

    private ArrayList<Entry> entries;
    private LineDataSet dataSet;
    private int phase = 0, DBP=0,SBP=0;
    private float gtcu, gtmoi, gtapi;
    PointArray pointArray = new PointArray();

    // oxi trong máu
    private ArrayList<Entry> entriesoxi;
    private LineDataSet dataSetoxi;
    private int phaseoxi = 0;
    private float gtcuoxi, gtmoioxi, gtapioxi;

    private boolean isAlerted = false;
    private long lastAlertTime = 0;
    private final long ALERT_INTERVAL = 1 * 60 * 1000;

    PointArray pointArrayoxi = new PointArray();

    // gửi vị trí từ activity sang fragment
    private Runnable sendLocationRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_device);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        selectItem = (thietBiFull) intent.getSerializableExtra("key_thietbi");
        lineChart = findViewById(R.id.heartRateChart);
        oxiInBloodChart = findViewById(R.id.oxiInBlood);
        vt = findViewById(R.id.vitri);
        duong = findViewById(R.id.luongduong);
        nhietdo = findViewById(R.id.nhietdo);
        canhbao = findViewById(R.id.canhbao);
        ten = findViewById(R.id.tennguoidung);
        o2 = findViewById(R.id.tvoxi);
        nhiptim = findViewById(R.id.nhiptim);
        te = findViewById(R.id.hinh);
        huyetap = findViewById(R.id.huyetap);
        ten.setText(selectItem.getTenNguoiDung());
        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detail_device_Activity.this, setting_inf_deviceActivity.class);
                intent.putExtra("key_thietbisetting", selectItem);
                startActivity(intent);
            }
        });

        bk = findViewById(R.id.back);
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detail_device_Activity.this, homeActivity.class);
                startActivity(intent);
            }
        });

        // === THAY ĐỔI LIÊN QUAN ĐẾN CHAT ===
        // Tải dữ liệu API ngay khi khởi tạo để đảm bảo dvList sẵn sàng cho chat
        loadInitialData();

        // biểu đồ
        setupChart();
        setupChartoxi();

        gtmoi = (float) 0;
        gtmoioxi = (float) 0;
        entries = new ArrayList<>();
        entriesoxi = new ArrayList<>();
        for (float x = 0; x < 600; x += 5) {
            float y = x * (float) 0.3;
            entries.add(new Entry(x, y));
        }
        for (float x = 0; x < 600; x += 5) {
            float y = x * (float) 0.3;
            entriesoxi.add(new Entry(x, y));
        }

        dataSet = new LineDataSet(entries, "Nhịp tim");
        styleDataSet();
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        // Bắt đầu animation di chuyển
        startMovingAnimation();

        dataSetoxi = new LineDataSet(entriesoxi, "Lượng oxi trong máu");
        styleDataSetoxi();
        LineData lineDataOxi = new LineData(dataSetoxi);
        oxiInBloodChart.setData(lineDataOxi);
        // Bắt đầu animation di chuyển
        startMovingAnimationoxi();

        // Gửi vị trí sang fragment
        sendLocationRunnable = new Runnable() {
            @Override
            public void run() {
                sendLocationToFragment();
                handler.postDelayed(this, 5000); // Lặp lại sau 5 giây
            }
        };

        // Bắt đầu gửi vị trí
        handler.postDelayed(sendLocationRunnable, 5000);

        vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double latitude = Double.parseDouble(dvList.get(0).getToaDoX());
                double longitude = Double.parseDouble(dvList.get(0).getToaDoY());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Tên địa điểm)");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 500);
            }
        });
    }

    // === THAY ĐỔI LIÊN QUAN ĐẾN CHAT ===
    // Tải dữ liệu API ngay khi khởi tạo activity
    private void loadInitialData() {
        api.apiService.GetBieuDo(selectItem.getIdThietBi()).enqueue(new Callback<List<thongSodevice>>() {
            @Override
            public void onResponse(Call<List<thongSodevice>> call, Response<List<thongSodevice>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dvList.clear();
                    dvList.addAll(response.body());
                    Log.d("DetailActivity", "Initial dvList size: " + dvList.size());
                } else {
                    Toast.makeText(detail_device_Activity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<thongSodevice>> call, Throwable t) {
                Toast.makeText(detail_device_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Gửi vị trí sang fragment
    private void sendLocationToFragment() {
        if (dvList != null && dvList.size() > 0) {
            double latitude = Double.parseDouble(dvList.get(0).getToaDoX());
            double longitude = Double.parseDouble(dvList.get(0).getToaDoY());

            // Lấy fragment bản đồ
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);

            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {
                    LatLng location = new LatLng(latitude, longitude);
                    googleMap.clear(); // Xóa các marker cũ
                    googleMap.addMarker(new MarkerOptions().position(location).title("Vị trí cập nhật"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                });
            }
        }
    }

    // hủy khi activity kết thúc
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendLocationRunnable);
    }

    private void setupChart() {
        // Cài đặt cơ bản cho chart
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // Cài đặt trục X
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Cài đặt trục Y
        lineChart.getAxisRight().setEnabled(false);
    }

    private void setupChartoxi() {
        // Cài đặt cơ bản cho chart
        oxiInBloodChart.getDescription().setEnabled(false);
        oxiInBloodChart.setTouchEnabled(true);
        oxiInBloodChart.setDragEnabled(true);
        oxiInBloodChart.setScaleEnabled(true);

        // Cài đặt trục X
        XAxis xAxis = oxiInBloodChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Cài đặt trục Y
        oxiInBloodChart.getAxisRight().setEnabled(false);
    }

    private void styleDataSet() {
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Làm mượt đường
        dataSet.setColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.BLUE);
        dataSet.setFillAlpha(50);
    }

    private void styleDataSetoxi() {
        dataSetoxi.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Làm mượt đường
        dataSetoxi.setColor(Color.BLUE);
        dataSetoxi.setLineWidth(2f);
        dataSetoxi.setDrawCircles(false);
        dataSetoxi.setDrawFilled(true);
        dataSetoxi.setFillColor(Color.BLUE);
        dataSetoxi.setFillAlpha(50);
    }

    private void startMovingAnimation() {
        // Tạo timer để di chuyển đường
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    // Cập nhật dữ liệu mới
                    updateData();
                    lineChart.invalidate();
                });
            }
        }, 0, 30); // Cập nhật mỗi 50ms
    }

    private void startMovingAnimationoxi() {
        // Tạo timer để di chuyển đường
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    // Cập nhật dữ liệu mới
                    updateData();
                    oxiInBloodChart.invalidate();
                });
            }
        }, 0, 30); // Cập nhật mỗi 50ms
    }

    private void updateData() {
        // Tăng phase để tạo hiệu ứng di chuyển
        ArrayList<Entry> newEntries = new ArrayList<>();
        ArrayList<Entry> newEntriesOxi = new ArrayList<>();
        gtapi = 0;
        gtapioxi = 0;
        // lấy gtmoi từ api

        // thêm 100 số vào cuối mảng
        api.apiService.GetBieuDo(selectItem.getIdThietBi()).enqueue(new Callback<List<thongSodevice>>() {
            @Override
            public void onResponse(Call<List<thongSodevice>> call, Response<List<thongSodevice>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dvList.clear();
                    dvList.addAll(response.body());
                    if (dvList.size() > 0) {
                        if ((float) dvList.get(0).getNhipTim() > 0) {
                            gtapi = (float) dvList.get(0).getNhipTim();
                        }
                        if ((float) dvList.get(0).getDBP() > 0) {
                            DBP =dvList.get(0).getDBP();
                        }
                        if ((float) dvList.get(0).getSBP() > 0) {
                            SBP =dvList.get(0).getSBP();
                        }
                        if ((float) dvList.get(0).getLuongOxiTrongMau() > 0) {
                            gtapioxi = (float) dvList.get(0).getLuongOxiTrongMau();
                        }
                    } else {
                        Toast.makeText(detail_device_Activity.this, "Dữ liệu không đủ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<thongSodevice>> call, Throwable t) {
                Toast.makeText(detail_device_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new android.os.Handler().postDelayed(() -> {
            if (gtapi > 0) gtmoi = gtapi;
            if (gtapioxi > 0) gtmoioxi = gtapioxi;

            float gt = tinhduongTim(gtmoi, phase);
            float gtoxi = tinhduongOxi(gtmoioxi, phaseoxi);

            if (phase > 100) phase = 0;
            if (phaseoxi > 100) phaseoxi = 0;
            phase = phase + 1;
            phaseoxi = phaseoxi + 1;
            pointArray.addEnd(gt);
            pointArrayoxi.addEnd(gtoxi);
            for (int i = 0; i < 490; i += 1) {
                newEntries.add(new Entry(i, pointArray.getPoint(i).x));
                newEntriesOxi.add(new Entry(i, pointArrayoxi.getPoint(i).x));
            }

            dataSet.setValues(newEntries);
            dataSetoxi.setValues(newEntriesOxi);
            lineChart.notifyDataSetChanged();
            oxiInBloodChart.notifyDataSetChanged();

            //
            if (dvList.get(0).getTinhTrang().contains("Khả năng nhồi máu cơ tim cao") || dvList.get(0).getTinhTrang().contains("Khả năng nhồi máu cơ tim")) {
                long currentTime = System.currentTimeMillis();

                if (lastAlertTime == 0 || (currentTime - lastAlertTime) > ALERT_INTERVAL) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Nguy hiểm! Lập tức kiểm tra tình trạng của " + selectItem.getTenNguoiDung(), Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setTranslationY(-1500);
                    snackbar.show();
                    lastAlertTime = currentTime;
                }
            } else {
                lastAlertTime = 0;
            }

            if (dvList.get(0).getTinhTrang().contains("Khả năng nhồi máu cơ tim cao") || dvList.get(0).getTinhTrang().contains("nhồi máu cơ tim") || dvList.get(0).getTinhTrang().contains("ngã") ) {
                te.setImageResource(R.drawable.peoplete);
            } else {
                te.setImageResource(R.drawable.warking);
            }
            duong.setText(String.valueOf(dvList.get(0).getGluco()));

            nhietdo.setText(String.valueOf( (int)(dvList.get(0).getNhietDo())));
            canhbao.setText(dvList.get(0).getTinhTrang());
            o2.setText(String.valueOf(dvList.get(0).getLuongOxiTrongMau()));
            nhiptim.setText(String.valueOf(dvList.get(0).getNhipTim()));
            huyetap.setText(String.valueOf(SBP)+"/"+String.valueOf(DBP));
        }, 500);
    }

    public float tinhduongTim(float x, int i) {
        if (i > 0 && i < 10) return x;
        if (i > 10 && i < 50) return x * (float) 0.25;
        else return 0;
    }

    public float tinhduongOxi(float x, int i) {
        if (i > 0 && i < 10) return x;
        if (i > 10 && i < 50) return x * (float) 0.85;
        else return x * (float) 0.6;
    }

    public String getFormattedHealthDataForChat() {
        if (dvList != null && !dvList.isEmpty()) {
            thongSodevice latestData = dvList.get(0);
            String nhietDoStr = String.valueOf(latestData.getNhietDo());
            String luongOxiStr = String.valueOf(latestData.getLuongOxiTrongMau());
            String nhipTimStr = String.valueOf(latestData.getNhipTim());
            String tinhTrangStr = latestData.getTinhTrang() != null ? latestData.getTinhTrang() : "Không rõ";

            String message = "Tôi đang theo dõi người thân có nguy cơ bị nhồi máu cơ tim. " +
                    "Người nhà tôi đang có lượng oxi trong máu là " + luongOxiStr +
                    ", nhịp tim là " + nhipTimStr +
                    ", huyết áp là " + SBP +"/"+DBP+
                    ", và nhiệt độ cơ thể là " + nhietDoStr +
                    ", tình trạng hiện tại là '" + tinhTrangStr + "'" +
                    ". Cho tôi hỏi bây giờ tốt nhất nên làm gì?";
            Log.d("DetailActivity", "Formatted message: " + message);
            return message;
        } else {
            String message = "Dữ liệu sức khỏe đang được tải. Vui lòng chờ...";
            Log.d("DetailActivity", "Formatted message: " + message);
            return message;
        }
    }
}

// class điểm vẽ đường
class Point {
    float x;
    float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    // Constructor
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

// Class chính chứa mảng và phương thức dịch chuyển
class PointArray {
    // Khai báo mảng 500 điểm
    private Point[] points = new Point[500];

    // Constructor để khởi tạo mảng
    public PointArray() {
        // Khởi tạo các điểm với giá trị mặc định
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(0.0f, 0.0f);
        }
    }

    public Point getPoint(int index) {
        if (index >= 0 && index < points.length) {
            return points[index];
        }
        return null;
    }

    // Phương thức dịch chuyển 1 đơn vị về đầu mảng
    public void shiftLeft() {
        Point temp = points[0];  // Lưu phần tử đầu tiên

        // Dịch tất cả phần tử sang trái 1 vị trí
        for (int i = 0; i < points.length - 1; i++) {
            points[i] = points[i + 1];
        }

        // Đặt phần tử đầu vào cuối mảng
        points[points.length - 1] = temp;
    }

    public void addEnd(float x) {
        Point temp = points[0];  // Lưu phần tử đầu tiên

        // Dịch tất cả phần tử sang trái 1 vị trí
        for (int i = 0; i < points.length - 1; i++) {
            points[i] = points[i + 1];
        }

        // Đặt phần tử đầu vào cuối mảng
        points[points.length - 1] = temp;
        points[points.length - 1].x = x;
    }

    // Phương thức để set giá trị cho một điểm
    public void setPoint(int index, float x, float y) {
        if (index >= 0 && index < points.length) {
            points[index] = new Point(x, y);
        }
    }
}