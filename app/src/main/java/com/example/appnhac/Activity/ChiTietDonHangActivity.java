package com.example.appnhac.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.Adapter.ChitietdonhangAdapter;
import com.example.appnhac.Model.ChiTietDonHang;
import com.example.appnhac.Model.DonHang;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.example.appnhac.ultil.CheckConnection;
import com.example.appnhac.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietDonHangActivity extends AppCompatActivity {
    ListView listviewCTDH;
    Toolbar toolbarctdh;
    ChitietdonhangAdapter chitietdonhangAdapter;
    ArrayList<ChiTietDonHang> arrayCTDH;
    int idctdh;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        Anhxa();
        ActionBar();
        getIdDonHang();
        getdata(page);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarctdh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarctdh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanQLCTDH + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID_DH = 0;
                int ID_CTDH = 0;
                int ID_MON = 0;
                String TenMonAn ="";
                Integer Gia = 0;
                Integer SoLuong = 0;

                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ID_CTDH = object.getInt("idctdh");
                            ID_DH = object.getInt("iddh");
                            ID_MON = object.getInt("idmon");
                            TenMonAn = object.getString("tenmon");
                            Gia = object.getInt("gia");
                            SoLuong = object.getInt("soluong");

                            arrayCTDH.add(new ChiTietDonHang(ID_CTDH,ID_DH,ID_MON,Gia,SoLuong,TenMonAn));
                            chitietdonhangAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    CheckConnection.showToast_short(getApplicationContext(),"Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("id_dh", String.valueOf(idctdh));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getIdDonHang() {
        idctdh = getIntent().getIntExtra("id_ctdh", -1);
        Log.d("Iddh", idctdh + "");
    }

    private void Anhxa() {
        listviewCTDH = findViewById(R.id.ListQL_CTDH);
        toolbarctdh = findViewById(R.id.toolbarchitietdonhang);
        arrayCTDH = new ArrayList<>();
        chitietdonhangAdapter = new ChitietdonhangAdapter(this,arrayCTDH);
        listviewCTDH.setAdapter(chitietdonhangAdapter);
    }
}