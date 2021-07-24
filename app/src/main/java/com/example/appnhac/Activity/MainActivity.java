package com.example.appnhac.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnhac.Adapter.LoaispAdapter;
import com.example.appnhac.Adapter.MainViewPagerAdapter;
import com.example.appnhac.Adapter.MonanAdapter;
import com.example.appnhac.Adapter.UserLocalStore;
import com.example.appnhac.Fragment.Fragment_Tim_Kiem;
import com.example.appnhac.Fragment.Fragment_Trang_Chu;
import com.example.appnhac.Model.GioHang;
import com.example.appnhac.Model.Loaisp;
import com.example.appnhac.Model.Monan;
import com.example.appnhac.Model.User;
import com.example.appnhac.R;
import com.example.appnhac.ultil.CheckConnection;
import com.example.appnhac.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Blob;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String urlGetdata = "http://192.168.0.101/server/getloaisp.php";
    String urlGetdatamonan = "http://192.168.0.101/server/getmonanmoi.php";

    String urlGetdatagen = "http://192.168.56.1/server/getloaisp.php";
    String urlGetdataMonanmoigen = "http://192.168.56.1/server/getmonanmoi.php";

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    RecyclerView recyclerViewmanhinhchinh;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    SearchView mSearchView;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;

    ArrayList<Monan> mangMonan;
    MonanAdapter monanAdapter;
    public static ArrayList<GioHang> manggiohang;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        if (CheckConnection.haveNetworkConnection(this)) {
            ActionBar();
            ActionViewFlipper();
            getDuLieuLoaisp(urlGetdatagen);
            getDuLieuMonanMoi(urlGetdataMonanmoigen);
            userLocalStore = new UserLocalStore(this);
            catchOnItemListView();
        } else {
            CheckConnection.showToast_short(this, "Kiểm tra lại kết nối");
            finish();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //Hiển thị searchview full chiêu ngang của toolbar
//        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                monanAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                monanAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private void catchOnItemListView() {
        User user = userLocalStore.getLoggedInUser();
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MonLauActivity.class);
                            intent.putExtra("idLoai", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MonAnVatActivity.class);
                            intent.putExtra("idLoai", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, MonComActivity.class);
                            intent.putExtra("idLoai", mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);

                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, ThongtinActivity.class);

                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, AdminActivity.class);

                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if (CheckConnection.haveNetworkConnection(MainActivity.this)) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(MainActivity.this, "Kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 8:
                       final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Đăng Xuất");
                        builder.setMessage("Username : " + user.username + "\nEmail :" + user.email);
                        builder.setCancelable(false);
                        builder.setPositiveButton("Trở về", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "Mời bạn tiếp tục mua sắm", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Code Logout
                                userLocalStore.clearUserData();
                                userLocalStore.setUserLoggedIn(false);
                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(loginIntent);
                                Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
            }
        });
    }

    private void getDuLieuMonanMoi(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                if (response != null) {
                    int ID = 0;
                    String TenMonAn = "";
                    Integer Gia = 0;
                    String Hinhanhmon ="";
                    String Mota = "";
                    String DanhGia = "";
                    int IDLoai = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("idmon");
                            TenMonAn = object.getString("tenmon");
                            Gia = object.getInt("gia");
                            Hinhanhmon = object.getString("hinhanhmon");
                            Mota = object.getString("mota");
                            DanhGia = object.getString("danhgia");
                            IDLoai = object.getInt("idloai");
                            mangMonan.add(new Monan(ID, TenMonAn, Gia, Hinhanhmon, Mota, DanhGia, IDLoai));
                            monanAdapter.notifyDataSetChanged();
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
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaisp(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    int ID = 0;
                    String TenLoaiMonAn = "";
                    String HinhanhLoai ="";
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("idloai");
                            TenLoaiMonAn = object.getString("tenloaisanpham");
                            HinhanhLoai = object.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(ID, TenLoaiMonAn, HinhanhLoai));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                mangloaisp.add(4, new Loaisp(5, "Liên Hệ", "https://fptcantho.net.vn/wp-content/uploads/2017/09/MetroUI-Other-Phone-icon.png"));
                mangloaisp.add(5, new Loaisp(6, "Thông tin", "https://banner2.cleanpng.com/20180331/qpw/kisspng-information-organization-computer-icons-directory-contact-5abf086ac987d2.6132345915224689708255.jpg"));
                mangloaisp.add(6, new Loaisp(7, "Quản Lý ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4Np1-QrW_1GXrLV-TJEFKf4uFFQNhLM-Miw&usqp=CAU"));
                mangloaisp.add(7, new Loaisp(8, "Đăng Nhập", "https://hanhtrinhmouoc2018.thanhnien.vn/img/general/none-avatar.png"));
                mangloaisp.add(8, new Loaisp(9, "Đăng Xuất", "https://png.pngtree.com/png-vector/20190917/ourlarge/pngtree-logout-icon-vectors-png-image_1737872.jpg"));


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> arrayQuangCao = new ArrayList<>();
        arrayQuangCao.add("https://i.pinimg.com/474x/50/35/47/503547525681e0d85b1ef49ed82e0f79.jpg");
        arrayQuangCao.add("https://img.pikbest.com/01/28/28/79G888piCIE4.jpg-0.jpg!bw700");
        arrayQuangCao.add("https://i.pinimg.com/236x/b0/0c/42/b00c423bebf12bb748def4ba99485ad0.jpg");

        for (int i = 0; i < arrayQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewFlipper);
        navigationView = findViewById(R.id.navigationView);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerViewmanhinhchinh);
        listViewmanhinhchinh = findViewById(R.id.listViewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawLayout);
        //Loai
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(4, "Trang chính", "https://store-images.s-microsoft.com/image/apps.64280.14494019834309616.a59ce6b7-8a7c-48ee-8ab8-977eece71157.4b5aeadb-1665-4e2a-9ce8-2df2faa155b9?mode=scale&q=90&h=300&w=300"));

        loaispAdapter = new LoaispAdapter(mangloaisp, this);
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangMonan = new ArrayList<>();
        //Mon an
        monanAdapter = new MonanAdapter(this, mangMonan);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewmanhinhchinh.setAdapter(monanAdapter);

        if (manggiohang != null) {

        } else {
            manggiohang = new ArrayList<>();
        }
    }

}