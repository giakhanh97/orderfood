package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appnhac.R;

public class AdminActivity extends AppCompatActivity {
    Button btnQLMon , btnQLDonHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnQLMon = findViewById(R.id.btnQuanLyMon);
        btnQLDonHang = findViewById(R.id.btnQuanLyDonHang);

        btnQLMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , QuanLyMonActivity.class);
                startActivity(intent);
            }
        });
        btnQLDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this , QuanLyDonHangActivity.class);
                startActivity(intent);
            }
        });
    }
}