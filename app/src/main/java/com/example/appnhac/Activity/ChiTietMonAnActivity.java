package com.example.appnhac.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appnhac.Model.GioHang;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietMonAnActivity extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    ImageView imgChiTiet , imgincrease, imgdecrease;
    TextView txtten, txtgia,txtmota,edtAmount;
    Button btnDatMua;
    Spinner spinner;
    int ID = 0;
    String TenMonAn = "";
    Integer GiaCT = 0;
    String Hinhanhmon = "";
    String Mota = "";
    String DanhGia = "";
    int IDLoai = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        Anhxa();
        ActionToolBar();
        GetInformation();
        CacthEventSpinner();
        EventButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() >0){

                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for(int i =0 ; i <MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdmon() == ID){
                            MainActivity.manggiohang.get(i).setSoluongmon(MainActivity.manggiohang.get(i).getSoluongmon() + sl);
                            MainActivity.manggiohang.get(i).setGia(GiaCT* MainActivity.manggiohang.get(i).getSoluongmon());
                            break;
                        }
                        exists = true;
                    }
                    if(exists){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * GiaCT ;
                        MainActivity.manggiohang.add(new GioHang(ID,TenMonAn,Giamoi,Hinhanhmon,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * GiaCT ;
                    MainActivity.manggiohang.add(new GioHang(ID,TenMonAn,Giamoi,Hinhanhmon,soluong));

                }
                Intent intent = new Intent(ChiTietMonAnActivity.this,GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CacthEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Monan monan = (Monan) getIntent().getSerializableExtra("Thongtinmonan");
        Log.d("Thongtinmonan",monan.getId()+"");
        ID = monan.getId();
        TenMonAn = monan.getTenmon();
        GiaCT = monan.getGia();
        Hinhanhmon = monan.getHinhanhmon();
        Mota = monan.getMota();
        DanhGia = monan.getDanhgia();
        IDLoai = monan.getId_loai();
        txtten.setText(TenMonAn);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá :"+decimalFormat.format(GiaCT)+ " Đ");
        txtmota.setText(Mota);
        Picasso.with(ChiTietMonAnActivity.this).load("http://192.168.56.1/server/images/"+monan.getHinhanhmon())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChiTiet);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChiTiet = findViewById(R.id.toolbarchitietmonan);
        imgChiTiet = findViewById(R.id.imgCTMonan);
        //imgincrease = findViewById(R.id.imageViewInCrease);
        //imgdecrease = findViewById(R.id.imageViewDeCrease);
        txtten = findViewById(R.id.txt_ten_ctma);
        txtgia = findViewById(R.id.txt_gia_ctma);
        txtmota = findViewById(R.id.txtMotaCT);
        //edtAmount =findViewById(R.id.editTextAmount);
        btnDatMua = findViewById(R.id.btnDatMua);
        spinner = findViewById(R.id.spinner);

    }
}