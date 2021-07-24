package com.example.appnhac.Adapter;

import android.content.Context;
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

public class MonanvatAdapter extends BaseAdapter {
    Context context;
    ArrayList<Monan> arraymonanvat;

    public MonanvatAdapter(Context context, ArrayList<Monan> arraymonanvat) {
        this.context = context;
        this.arraymonanvat = arraymonanvat;
    }

    @Override
    public int getCount() {
        return arraymonanvat.size() ;
    }

    @Override
    public Object getItem(int position) {
        return arraymonanvat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttenanvat,txtgiaanvat;
        public ImageView imgmonanvat;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_monanvat,null);
            viewHolder.imgmonanvat = view.findViewById(R.id.img_monanvat);
            viewHolder.txttenanvat = view.findViewById(R.id.txt_tenanvat);
            viewHolder.txtgiaanvat = view.findViewById(R.id.txt_gia_anvat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Monan monan = (Monan) getItem(position);
        viewHolder.txttenanvat.setText(monan.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiaanvat.setText("Giá :"+decimalFormat.format(monan.getGia())+ " Đ");
        Picasso.with(context).load("http://192.168.56.1/server/images/"+monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgmonanvat);
        return view;
    }
}
