package com.example.appnhac.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnhac.Activity.QuanLyMonActivity;
import com.example.appnhac.Activity.SuaMonActivity;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanLyMonAdapter extends BaseAdapter {
    QuanLyMonActivity context;
    ArrayList<Monan> arrayQLmon;

    public QuanLyMonAdapter(QuanLyMonActivity context, ArrayList<Monan> arrayQLmon) {
        this.context = context;
        this.arrayQLmon = arrayQLmon;
    }

    @Override
    public int getCount() {
        return arrayQLmon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayQLmon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttenmon,txtgia;
        public ImageView imgmon , img_sua,img_xoa;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder =  null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_quanly,null);
            viewHolder.txttenmon = view.findViewById(R.id.txt_tenmon);
            viewHolder.txtgia = view.findViewById(R.id.txt_gia);
            viewHolder.imgmon = view.findViewById(R.id.img_mon);
            viewHolder.img_sua = view.findViewById(R.id.img_sua);
            viewHolder.img_xoa = view.findViewById(R.id.img_xoa);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Monan monan = (Monan) getItem(position);
        viewHolder.txttenmon.setText(monan.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("Giá :"+decimalFormat.format(monan.getGia())+ " Đ");
        Picasso.with(context).load("http://192.168.56.1/server/images/"+monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgmon);

        viewHolder.img_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuaMonActivity.class);
                intent.putExtra("thongtinmonan",monan);
                context.startActivity(intent);
            }
        });

        viewHolder.img_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogXoa(monan.getTenmon(),monan.getId());
            }
        });
        return view;
    }

    private void DialogXoa(String ten,final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa " + ten + " không");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteMon(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
}
