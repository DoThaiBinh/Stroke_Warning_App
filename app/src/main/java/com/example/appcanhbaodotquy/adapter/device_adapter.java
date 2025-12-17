package com.example.appcanhbaodotquy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appcanhbaodotquy.Models.thietBiFull;
import com.example.appcanhbaodotquy.R;

import java.util.List;

public class device_adapter extends BaseAdapter {
    private Context context;
    private List<thietBiFull> deviceList;
    private LayoutInflater inflater;
    public device_adapter(Context context, List<thietBiFull> List) {
        this.context = context;
        this.deviceList = List;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return deviceList.size(); // Trả về số lượng sinh viên trong danh sách
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position); // Trả về đối tượng sinh viên tại vị trí position
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về ID của đối tượng (ở đây chỉ sử dụng vị trí làm ID)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Sử dụng convertView để tái sử dụng View cũ, tránh tạo lại nhiều lần
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_device, parent, false);
        }

        // Lấy đối tượng sinh viên tại vị trí hiện tại
        thietBiFull dv = deviceList.get(position);

        // Liên kết các thành phần giao diện với dữ liệu
        TextView tvten = convertView.findViewById(R.id.ten);
        TextView tvtinhtrang = convertView.findViewById(R.id.tinhtrang);

        tvten.setText( dv.getTenNguoiDung());
        tvtinhtrang.setText( dv.getTinhTrang());

        return convertView;
    }

}
