package com.example.appnhac.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonlauAdapter extends BaseAdapter {
    Context context;
    ArrayList<Monan> arraymonlau;

    public MonlauAdapter(Context context, ArrayList<Monan> arraymonlau) {
        this.context = context;
        this.arraymonlau = arraymonlau;
    }

    @Override
    public int getCount() {
        return arraymonlau.size() ;
    }

    @Override
    public Object getItem(int position) {
        return arraymonlau.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttenmonlau,txtgia;
        public ImageView imgmonlau;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_monlau,null);
            viewHolder.imgmonlau = view.findViewById(R.id.img_monlau);
            viewHolder.txttenmonlau = view.findViewById(R.id.txt_tenmonlau);
            viewHolder.txtgia = view.findViewById(R.id.txt_gia_item);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Monan monan = (Monan) getItem(position);
        viewHolder.txttenmonlau.setText(monan.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("Giá :"+decimalFormat.format(monan.getGia())+ " Đ");
        Picasso.with(context).load("http://192.168.56.1/server/images/"+monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgmonlau);
        return view;
    }
}
