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

public class MoncomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Monan> arraymoncom;

    public MoncomAdapter(Context context, ArrayList<Monan> arraymoncom) {
        this.context = context;
        this.arraymoncom = arraymoncom;
    }

    @Override
    public int getCount() {
        return arraymoncom.size();
    }

    @Override
    public Object getItem(int position) {
        return arraymoncom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txttenmoncom, txtgia;
        public ImageView imgmoncom;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_moncom, null);
            viewHolder.imgmoncom = view.findViewById(R.id.img_moncom);
            viewHolder.txttenmoncom = view.findViewById(R.id.txt_tenmoncom);
            viewHolder.txtgia = view.findViewById(R.id.txt_gia_item);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Monan monan = (Monan) getItem(position);
        viewHolder.txttenmoncom.setText(monan.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("Giá :" + decimalFormat.format(monan.getGia()) + " Đ");
        Picasso.with(context).load("http://192.168.56.1/server/images/" + monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgmoncom);
        return view;
    }
}
