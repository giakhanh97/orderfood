package com.example.appnhac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnhac.Activity.ChiTietMonAnActivity;
import com.example.appnhac.Activity.GioHangActivity;
import com.example.appnhac.Activity.MainActivity;
import com.example.appnhac.Model.GioHang;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    GioHangActivity context;
    ArrayList<GioHang> arraygiohang;

    public GioHangAdapter(GioHangActivity context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttengiohang , txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus , btnvalues ,btnplus;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = convertView.findViewById(R.id.txttengiohang);
            viewHolder.txtgiagiohang = convertView.findViewById(R.id.txtgiagiohang);
            viewHolder.imggiohang = convertView.findViewById(R.id.imggiohang);
            viewHolder.btnminus = convertView.findViewById(R.id.btnminus);
            viewHolder.btnvalues = convertView.findViewById(R.id.btnvalues);
            viewHolder.btnplus = convertView.findViewById(R.id.btnplus);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GioHang  gioHang = (GioHang) getItem(position);
        viewHolder.txttengiohang.setText(gioHang.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText("Giá :"+decimalFormat.format(gioHang.getGia())+ " Đ");

        Picasso.with(context).load("http://192.168.56.1/server/images/"+gioHang.getHinhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(gioHang.getSoluongmon()+"");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl >= 15){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl == 1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl > 1){
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }


        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString()) +1;
                int slht = MainActivity.manggiohang.get(position).getSoluongmon();
                long giaht = MainActivity.manggiohang.get(position).getGia();
                MainActivity.manggiohang.get(position).setSoluongmon(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText("Giá :"+decimalFormat.format(giamoinhat)+ " Đ");
                GioHangActivity.EventUltil();
                if (slmoinhat > 14){
                    viewHolder.btnplus.setVisibility(View.INVISIBLE);
                }else if (slmoinhat == 1) {
                    viewHolder.btnminus. setVisibility(View.INVISIBLE);

                }else if (slmoinhat > 1 && slmoinhat <= 14){
                    viewHolder.btnminus. setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                }
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));


            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(viewHolder.btnvalues.getText().toString()) -1;
                int slht = MainActivity.manggiohang.get(position).getSoluongmon();
                long giaht = MainActivity.manggiohang.get(position).getGia();
                MainActivity.manggiohang.get(position).setSoluongmon(slmoinhat);
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.manggiohang.get(position).setGia(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText("Giá :"+decimalFormat.format(giamoinhat)+ " Đ");
                GioHangActivity.EventUltil();
                if (slmoinhat > 14){
                    viewHolder.btnplus.setVisibility(View.INVISIBLE);
                }else if (slmoinhat == 1) {
                    viewHolder.btnminus. setVisibility(View.INVISIBLE);

                }else if (slmoinhat > 1 && slmoinhat <= 14){
                    viewHolder.btnminus. setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                }
                viewHolder.btnvalues.setText(String.valueOf(slmoinhat));

            }
        });
        return convertView;
    }
}
