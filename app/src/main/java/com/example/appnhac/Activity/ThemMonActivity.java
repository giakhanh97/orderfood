package com.example.appnhac.Activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ThemMonActivity extends AppCompatActivity {
    Toolbar toolbarthem;
    EditText edtten, edtgia, edtmota, edtloai;
    Button btnchonanh, btnthemmon;
    ImageView imgthem;
    String endcodeimagestring;
    Bitmap bitmap;
    private static final String url = "http://192.168.56.1/server/themmonan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon);
        Anhxa();
        ActionToolbar();
        btnchonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(ThemMonActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Chọn Ảnh"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        btnthemmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtten.getText().toString().trim();
                final String gia = edtgia.getText().toString().trim();
                final String mota = edtmota.getText().toString().trim();
                final String loai = edtloai.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        edtten.setText("");
                        edtgia.setText("");
                        edtmota.setText("");
                        edtloai.setText("");
                        imgthem.setImageResource(R.drawable.ic_launcher_foreground);
                        if (response.trim().equals("Thêm Thành Công")) {
                            Toast.makeText(ThemMonActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ThemMonActivity.this, QuanLyMonActivity.class));
                        } else {
                            Toast.makeText(ThemMonActivity.this, "Lỗi!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThemMonActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("tenmon", ten);
                        map.put("gia", gia);
                        map.put("mota", mota);
                        map.put("id_loai", loai);
                        map.put("hinhanhmon", endcodeimagestring);
                        return map;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarthem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarthem.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imgthem.setImageBitmap(bitmap);
                endcodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void endcodeBitmapImage(Bitmap bitmap) {
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgthem.getDrawable();
//        bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        endcodeimagestring = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);

    }

    private void Anhxa() {
        toolbarthem = findViewById(R.id.toolbarThem);
        imgthem = findViewById(R.id.imgthemmon);
        edtten = findViewById(R.id.edtTenmon);
        edtgia = findViewById(R.id.edtgia);
        edtmota = findViewById(R.id.edtmota);
        edtloai = findViewById(R.id.edtloaimon);
        btnchonanh = findViewById(R.id.btnchonanh);
        btnthemmon = findViewById(R.id.btnThemmon);

    }
}