package com.example.res_api.Activiti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.res_api.Adapter.ProductsAdapter;
import com.example.res_api.Adapter.SliderAdapters;
import com.example.res_api.Adapter.TaiKhoanAdapter;
import com.example.res_api.Model.Category;
import com.example.res_api.Model.Products;
import com.example.res_api.Model.SliderItems;
import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangChuActiviti extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private Handler slideHander = new Handler();
    private RecyclerView recyclerViewCategory,recyclerViewProducts,recyclerViewHot,recyclerViewQuan;
//    private CategoryMainAdapter categoryMainAdapter;
    private RecyclerView.Adapter sanPhamMainAdapter,AoQuanAdapter;

    private  List<Products> productsList = new ArrayList<>();
    private ProductsAdapter productsAdapter;
    private List<Category> mThuongHieu;
    private Context context = this;
    private LinearLayout Lme,next_gio_hang,nextTym;

    private TextView search,textView3;

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu_activiti);



        anhxa();
        banner();
        chuyenTrang();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerViewProducts.setLayoutManager(gridLayoutManager);

        ApiService.apiService.productsListApi("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                productsList = response.body();
                productsAdapter = new ProductsAdapter(context,productsList);
                recyclerViewProducts.setAdapter(productsAdapter);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void anhxa(){
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActiviti.this, LichSuHoaDonMainActivity.class);
                startActivity(intent);
            }
        });

        textView3 = findViewById(R.id.textView3);
        Lme = findViewById(R.id.Lme);
        search = findViewById(R.id.editTextText);
        next_gio_hang = findViewById(R.id.next_gio_hang);
        nextTym = findViewById(R.id.next_tym);
        viewPager2 = findViewById(R.id.viewpagerSlider);
        recyclerViewCategory = findViewById(R.id.rcy_category);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewProducts = findViewById(R.id.rcy_products);
        //rcy ÁO
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // rcy Quan
        recyclerViewQuan = findViewById(R.id.rcy_quan);
        recyclerViewQuan.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        // rcy All
        recyclerViewHot = findViewById(R.id.rcy_all);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        recyclerViewHot.setLayoutManager(gridLayoutManager);
    }


    private void banner() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.slider1));
        sliderItems.add(new SliderItems(R.drawable.slider2));
        sliderItems.add(new SliderItems(R.drawable.slider3));
        viewPager2.setAdapter(new SliderAdapters(sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHander.removeCallbacks(sliderRunnable);
            }
        });
    }
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem());
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHander.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHander.postDelayed(sliderRunnable,2000);
    }

    public void chuyenTrang() {
        Lme.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActiviti.this, Trang_Ca_Nhan.class);
            startActivity(intent);
        });

        next_gio_hang.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActiviti.this,Gio_Hang.class);
            startActivity(intent);
        });

        nextTym.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChuActiviti.this, QuanLySanPhamActivity.class);
            startActivity(intent);
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuActiviti.this, SearchMainActivity.class);
                startActivity(intent);

            }
        });

    }

}