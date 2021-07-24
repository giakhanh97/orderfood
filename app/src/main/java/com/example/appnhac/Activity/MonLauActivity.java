package com.example.appnhac.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.Adapter.MonlauAdapter;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.R;
import com.example.appnhac.ultil.CheckConnection;
import com.example.appnhac.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonLauActivity extends AppCompatActivity {
    Toolbar toolbarmonlau;
    ListView listViewmonlau;
    MonlauAdapter monlauAdapter;
    ArrayList<Monan> mangmonlau;
    int idmonlau = 0;
    int page = 1;
    View footerview;
    boolean limitdata = false;
    boolean isLoading = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_lau);
        Anhxa();
        GetIdLoaimon();
        ActionToolbar();
        GetData(page);
        LoadmoreData();
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
    private void LoadmoreData() {
        listViewmonlau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MonLauActivity.this , ChiTietMonAnActivity.class);
                intent.putExtra("Thongtinmonan",mangmonlau.get(position));
                startActivity(intent);
            }
        });
        listViewmonlau.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount !=0 && isLoading ==false && limitdata==false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanMonlau + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID = 0;
                String TenMonAn = "";
                Integer Gia = 0;
                String Hinhanhmon = null;
                String Mota = "";
                String DanhGia = "";
                int IDLoai = 0;
                if (response != null && response.length() != 2) {
                    listViewmonlau.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ID = object.getInt("idmon");
                            TenMonAn = object.getString("tenmon");
                            Gia = object.getInt("gia");
                            Hinhanhmon = object.getString("hinhanhmon");
                            Mota = object.getString("mota");
                            DanhGia = object.getString("danhgia");
                            IDLoai = object.getInt("idloai");
                            mangmonlau.add(new Monan(ID, TenMonAn, Gia, Hinhanhmon, Mota, DanhGia, IDLoai));
                            monlauAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata = true;
                    listViewmonlau.removeFooterView(footerview);
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
                param.put("id_loai", String.valueOf(idmonlau));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarmonlau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarmonlau.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdLoaimon() {
        idmonlau = getIntent().getIntExtra("idLoai", -1);
        Log.d("giatriloaimon", idmonlau + "");
    }

    private void Anhxa() {
        toolbarmonlau = findViewById(R.id.toolbarMonlau);
        listViewmonlau = findViewById(R.id.listviewmonlau);
        mangmonlau = new ArrayList<>();
        monlauAdapter = new MonlauAdapter(MonLauActivity.this, mangmonlau);
        listViewmonlau.setAdapter(monlauAdapter);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewmonlau.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}