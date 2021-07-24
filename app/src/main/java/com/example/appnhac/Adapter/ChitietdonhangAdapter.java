package com.example.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhac.Model.ChiTietDonHang;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChitietdonhangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ChiTietDonHang> arrayCTDH;

    public ChitietdonhangAdapter(Context context, ArrayList<ChiTietDonHang> arrayCTDH) {
        this.context = context;
        this.arrayCTDH = arrayCTDH;
    }

    @Override
    public int getCount() {
        return arrayCTDH.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayCTDH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txttenmon, txtgia, txtsoluong;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_quanly_ctdh,null);


            viewHolder.txttenmon = view.findViewById(R.id.txt_tenmon_ctdh);
            viewHolder.txtgia = view.findViewById(R.id.txt_gia_ctdh);
            viewHolder.txtsoluong = view.findViewById(R.id.txt_soluong_ctdh);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        ChiTietDonHang chiTietDonHang = (ChiTietDonHang) getItem(position);

        viewHolder.txttenmon.setText("Tên Món :"+chiTietDonHang.getTenmon());
        viewHolder.txtgia.setText("Giá :"+chiTietDonHang.getGia()+"");
        viewHolder.txtsoluong.setText("Số Lượng Đặt :"+chiTietDonHang.getSoluong()+"");
        return view;
    }
}
