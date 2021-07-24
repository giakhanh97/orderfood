package com.example.appnhac.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.Adapter.MonlauAdapter;
import com.example.appnhac.Adapter.QuanLyMonAdapter;
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

public class QuanLyMonActivity extends AppCompatActivity {
    Toolbar toolbarquanly;
    ListView lsquanly;
    QuanLyMonAdapter quanLyMonAdapter;
    ArrayList<Monan> arrayQLmon;
    int idmon = 0;
    String duongdan = Server.duongdanQLmon ;
    private static final String url ="http://192.168.56.1/server/xoamonan.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_mon);
        Anhxa();
        ActionToolbar();
        GetData();
    }

    public void DeleteMon(int idmon){
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                map.put("idmon",String.valueOf(idmon));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            startActivity(new Intent(QuanLyMonActivity.this,ThemMonActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,duongdan,null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                if(response != null){
                    int ID = 0;
                    String TenMonAn ="";
                    Integer Gia = 0;
                    String Hinhanhmon;
                    String Mota = "";
                    String DanhGia ="";
                    int IDLoai =0;
                    arrayQLmon.clear();
                    for (int i = 0; i <response.length() ; i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("idmon");
                            TenMonAn = object.getString("tenmon");
                            Gia = object.getInt("gia");
                            Hinhanhmon =  object.getString("hinhanhmon");
                            Mota = object.getString("mota");
                            DanhGia = object.getString("danhgia");
                            IDLoai = object.getInt("idloai");
                            arrayQLmon.add(new Monan(ID,TenMonAn,Gia,Hinhanhmon,Mota,DanhGia,IDLoai));
                            quanLyMonAdapter.notifyDataSetChanged();
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
                Toast.makeText(QuanLyMonActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonArrayRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarquanly);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarquanly.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarquanly = findViewById(R.id.toolbarQuanLy);
        lsquanly = findViewById(R.id.listviewQuanLy);
        arrayQLmon = new ArrayList<>();
        quanLyMonAdapter = new QuanLyMonAdapter(QuanLyMonActivity.this, arrayQLmon);
        lsquanly.setAdapter(quanLyMonAdapter);

    }


}