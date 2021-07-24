package com.example.appnhac.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.R;
import com.example.appnhac.ultil.CheckConnection;
import com.example.appnhac.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    EditText edttenkh , edtsdt,edtdiachi,edtemail;
    Button btnxacnhan,btntrove;
//    Spinner spinnerhttt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        Anhxa();
//        CacthEventSpinner();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else{
            CheckConnection.showToast_short(getApplicationContext(),"Kiểm tra kết nối");
        }
    }

    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String ten =edttenkh.getText().toString().trim();
               final String sdt =edtsdt.getText().toString().trim();
               final String diachi =edtdiachi.getText().toString().trim();
               final String email =edtemail.getText().toString().trim();
                if(ten.length()>0 && sdt.length()>0 && diachi.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanthongtinkh, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String id_dh) {
                            Log.d("Madonhang", id_dh);
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanChitietdonhang, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("test", response);
                                    if (response != null) {
                                        MainActivity.manggiohang.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        CheckConnection.showToast_short(getApplicationContext(), "You have successfully added data to your cart");
                                        startActivity(intent);
                                        CheckConnection.showToast_short(getApplicationContext(), "\n" +
                                                "Please continue shopping");
                                    } else {
                                        CheckConnection.showToast_short(getApplicationContext(), "Cart data has failed");
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("id_dh", id_dh);
                                            jsonObject.put("id_mon", MainActivity.manggiohang.get(i).getIdmon());
                                            jsonObject.put("tenmon", MainActivity.manggiohang.get(i).getTenmon());
                                            jsonObject.put("gia", MainActivity.manggiohang.get(i).getGia());
                                            jsonObject.put("soluong", MainActivity.manggiohang.get(i).getSoluongmon());

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                    hashMap.put("json", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(request);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkh",ten);
                            hashMap.put("sdt",sdt);
                            hashMap.put("diachi",diachi);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.showToast_short(getApplicationContext(),"Kiểm tra lại dữ liệu");
                }
            }
        });
    }
//    private void CacthEventSpinner() {
//        String[] httt = new String[]{"Tiền mặt" , "ATM"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,httt);
//        spinnerhttt.setAdapter(arrayAdapter);
//    }
    private void Anhxa() {
        edttenkh = findViewById(R.id.edtTenKH);
        edtsdt = findViewById(R.id.edtSDT);
        edtdiachi = findViewById(R.id.edtDiaChi);
        edtemail = findViewById(R.id.edtEmail);
        btnxacnhan = findViewById(R.id.btnxacnhan);
        btntrove = findViewById(R.id.btntrove);

    }
}