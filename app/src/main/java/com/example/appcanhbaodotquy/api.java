package com.example.appcanhbaodotquy;
import com.example.appcanhbaodotquy.Models.RequestAddThietBi;
import com.example.appcanhbaodotquy.Models.RspnguoidungLogin;
import com.example.appcanhbaodotquy.Models.thongSodevice;
import com.example.appcanhbaodotquy.Models.login;
import com.example.appcanhbaodotquy.Models.requestRegister;
import com.example.appcanhbaodotquy.Models.thietBiFull;
import com.example.appcanhbaodotquy.Models.vitri;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface api {
//    Gson gson = new GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//            .create();
    Gson gson1 = new GsonBuilder()
            .setLenient()
            .create();

    // Tạo Retrofit instance
    api apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:5019/api/API/")
            .addConverterFactory(GsonConverterFactory.create(gson1))
            .build()
            .create(api.class);
    @GET("getThietBi")
    Call<List<thietBiFull>> getThietBi(@Query("sdt") String sdt);
    @GET("GetViTri")
    Call<vitri> GetViTri(@Query("id") String id);
    @GET("GetBieuDo")
    Call<List<thongSodevice>> GetBieuDo(@Query("id") String id);

    @POST("dangNhap")
    Call<RspnguoidungLogin> dangNhap(@Body login lg);
    @POST("DangKiTaiKhoan")
    Call<String> DangKiTaiKhoan(@Body requestRegister dk);
    @POST("ThemThietBi")
    Call<String> ThemThietBi(@Body RequestAddThietBi tb);
    @PUT("SuaThietBi")
    Call<String> SuaThietBi(@Body RequestAddThietBi tb);

    @POST("SaveFcmToken")
    Call<ResponseBody> saveFcmToken(
            @Query("idTaiKhoan") String idTaiKhoan,
            @Query("fcmToken") String fcmToken
    );


}
