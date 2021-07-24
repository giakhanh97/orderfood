package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnhac.Activity.ChiTietMonAnActivity;
import com.example.appnhac.Activity.MainActivity;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.example.appnhac.ultil.CheckConnection;
import com.karumi.dexter.Dexter;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MonanAdapter extends RecyclerView.Adapter<MonanAdapter.ItemHolder> implements Filterable  {
    private Context context;
    private ArrayList<Monan> arraymonan;
    private ArrayList<Monan> arraymonanold;

    public MonanAdapter(Context context, ArrayList<Monan> arraymonan) {
        this.context = context;
        this.arraymonan = arraymonan;
        this.arraymonanold = arraymonan;

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_monanmoi, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Monan monan = arraymonan.get(position);
        holder.txttenmon.setText(monan.getTenmon());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText("Giá :" + decimalFormat.format(monan.getGia()) + " Đ");

        Picasso.with(context).load("http://192.168.56.1/server/images/" + monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgmonan);
    }

    @Override
    public int getItemCount() {
        return arraymonan.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d("BBB","onSearch");
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    arraymonan = arraymonanold;
                }else{
                    ArrayList<Monan> arrayListmonan = new ArrayList<>();
                    for (Monan monan: arraymonanold) {
                        if (monan.getTenmon().toLowerCase().contains(strSearch.toLowerCase())){
                            arrayListmonan.add(monan);
                        }
                    }
                    arraymonan = arrayListmonan;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arraymonan;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arraymonan = (ArrayList<Monan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgmonan;
        public TextView txttenmon, txtgia;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgmonan = itemView.findViewById(R.id.imgmonanmoi);
            txttenmon = itemView.findViewById(R.id.txttenmon);
            txtgia = itemView.findViewById(R.id.txtgia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietMonAnActivity.class);
                    intent.putExtra("Thongtinmonan", arraymonan.get(getLayoutPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToast_short(context, arraymonan.get(getLayoutPosition()).getTenmon());
                    context.startActivity(intent);
                }
            });
        }
    }
}
