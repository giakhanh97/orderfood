package com.example.appnhac.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.Adapter.QuanLyDonHangAdapter;
import com.example.appnhac.Model.DonHang;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.example.appnhac.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyDonHangActivity extends AppCompatActivity {
    Toolbar toolbarquanlydonhang;
    ListView lsquanlydonhang;

    QuanLyDonHangAdapter quanLyDonHangAdapter;
   private ArrayList<DonHang> arrayQLDonHang = new ArrayList<>();
    int iddh = 0;
    int ID = 0;
    String Tenkh ="";
    Integer sdt = 0;
    String diachi = "";
    String email = "";
    String duongdan = Server.duongdanQLdonhang ;
    private static final String url ="http://192.168.56.1/server/xoadh.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        Anhxa();
        ActionToolbar();
        GetData();
        catchOnItemListView();
    }

    private void catchOnItemListView() {
        lsquanlydonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuanLyDonHangActivity.this , ChiTietDonHangActivity.class);
                intent.putExtra("id_ctdh",arrayQLDonHang.get(position).getId());
                startActivity(intent);
            }
        });
    }


    private void GetData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, duongdan, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(QuanLyDonHangActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response != null) {
                    arrayQLDonHang.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("iddh");
                            Tenkh = object.getString("tenkh");
                            sdt = object.getInt("sdt");
                            diachi =  object.getString("diachi");
                            email = object.getString("email");
                            arrayQLDonHang.add(new DonHang(ID,Tenkh,sdt,diachi,email));
                            quanLyDonHangAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(QuanLyDonHangActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(QuanLyDonHangActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarquanlydonhang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarquanlydonhang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarquanlydonhang = findViewById(R.id.toolbarQuanLyDonHang);
        lsquanlydonhang = findViewById(R.id.listviewQLDH);

        arrayQLDonHang = new ArrayList<>();
        quanLyDonHangAdapter = new QuanLyDonHangAdapter(this,arrayQLDonHang);
        lsquanlydonhang.setAdapter(quanLyDonHangAdapter);
    }

    public void DeleteDH(int iddh){
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response.toString());
                if(response.trim().equals("success")) {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    GetData();
                }else{
                    Toast.makeText(getApplicationContext(), "Lỗi!!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("iddh",String.valueOf(iddh));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}