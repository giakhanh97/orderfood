package com.example.appnhac.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.ChiTietDonHangActivity;
import com.example.appnhac.Activity.QuanLyDonHangActivity;
import com.example.appnhac.Model.DonHang;
import com.example.appnhac.R;

import java.util.ArrayList;

public class QuanLyDonHangAdapter extends BaseAdapter {
    private  QuanLyDonHangActivity context;
    private ArrayList<DonHang> arrayDonHang;

    public QuanLyDonHangAdapter(QuanLyDonHangActivity context, ArrayList<DonHang> arrayDonHang) {
        this.context = context;
        this.arrayDonHang = arrayDonHang;
    }

    @Override
    public int getCount() {
        return arrayDonHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayDonHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public ImageView img_xoadh;
        public TextView txttenkh, txtmadh, txtdiachi, txtsdt, txtemail;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_quanlydonhang,null);
            viewHolder.txtmadh = view.findViewById(R.id.txt_madonhang);
            viewHolder.txttenkh = view.findViewById(R.id.txt_tenkh);
            viewHolder.txtsdt = view.findViewById(R.id.txt_sdt);
            viewHolder.txtdiachi = view.findViewById(R.id.txt_diachi);
            viewHolder.txtemail = view.findViewById(R.id.txt_email);
            viewHolder.img_xoadh = view.findViewById(R.id.img_xoadh);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        DonHang donHang = (DonHang) getItem(position);
        viewHolder.txtmadh.setText(donHang.getId()+"");
        viewHolder.txttenkh.setText(donHang.getTenkh());
        viewHolder.txtsdt.setText(donHang.getSdt()+"");
        viewHolder.txtdiachi.setText(donHang.getDiachi());
        viewHolder.txtemail.setText(donHang.getEmail());

        viewHolder.img_xoadh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogXoa(donHang.getTenkh(),donHang.getId());
                Log.d("xoa",donHang.getTenkh());
            }
        });
        return  view;
    }

    private void DialogXoa(String ten,final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa " + ten + " không");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteDH(id);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_quanlydonhang, parent,false);
//
////        ViewHolder viewHolder = new ViewHolder(v);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        DonHang donHang = arrayDonHang.get(position);
//
//        holder.txtmadh.setText("Mã Đơn Hàng :"+donHang.getId()+"");
//        holder.txttenkh.setText("Tên Khách Hàng :"+donHang.getTenkh());
//        holder.txtsdt.setText("Số Điện Thoại :"+donHang.getSdt()+"");
//        holder.txtdiachi.setText("Địa Chỉ :"+donHang.getDiachi());
//        holder.txtemail.setText("Email :"+donHang.getEmail());
//
////        holder.setItemClickListener(new ItemClickListener() {
////            @Override
////            public void onClick(View view, int position, boolean isLongClick) {
////                Toast.makeText(context, donHang.getId(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(context,ChiTietDonHangActivity.class);
////                intent.putExtra("id_dh",arrayDonHang.get());
////                context.startActivity(intent);
////            }
////        });
//
//        holder.img_xoadh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogXoa(donHang.getTenkh(),donHang.getId());
//            }
//        });
//    }
//    private void DialogXoa(String ten,final int id){
//        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
//        dialogXoa.setMessage("Bạn có muốn xóa " + ten + " không");
//        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                context.DeleteDH(id);
//            }
//        });
//        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        dialogXoa.show();
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayDonHang ==null ? 0 :arrayDonHang.size();
//    }
//
//
//
//    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public ImageView img_xoadh;
//        public TextView txttenkh, txtmadh, txtdiachi, txtsdt, txtemail;
//        private ItemClickListener itemClickListener;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            txtmadh = itemView.findViewById(R.id.txt_madonhang);
//            txttenkh = itemView.findViewById(R.id.txt_tenkh);
//            txtsdt = itemView.findViewById(R.id.txt_sdt);
//            txtdiachi = itemView.findViewById(R.id.txt_diachi);
//            txtemail = itemView.findViewById(R.id.txt_email);
//            img_xoadh = itemView.findViewById(R.id.img_xoadh);
//
//            itemView.setOnClickListener((View.OnClickListener) this);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                Intent intent = new Intent(context,ChiTietDonHangActivity.class);
//                intent.putExtra("id_dh",arrayDonHang.get(getLayoutPosition()));
//                context.startActivity(intent);
//                }
//            });
//        }
//
//        public void setItemClickListener(ItemClickListener itemClickListener) {
//            this.itemClickListener = itemClickListener;
//        }
//        @Override
//        public void onClick(View v) {
//            itemClickListener.onClick(v,this.getAdapterPosition(),false);
//        }
//    }
}