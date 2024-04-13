package com.example.res_api.Activiti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Adapter.MauSizeAdapter;
import com.example.res_api.Adapter.ProductsAdapter;
import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.GioHang;
import com.example.res_api.Model.User;
import com.example.res_api.R;

import com.example.res_api.SharedPreferences.MySharedPreferences;
import com.example.res_api.api.ApiService;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private ImageView imgPic, imgBack, imgCart;
    private TextView txtName, txtPrice, txtTitle;
    private Button btnAdd;
    private Context context = this;
    private RecyclerView recyclerView;
    private String color = null;
    private String size = null;

    private MauSizeAdapter mauSizeAdapter;

    private List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();


    private List<GioHang> mListGioHang = new ArrayList<>();
    private List<ChiTietGioHang> mListChiTiet = new ArrayList<>();
    private int so = 1;
    BottomSheetDialog dialog;
//    NotificationBadge badge;

    private String idChiTietSanPham;
    private double soLuongTrongKho = 0;

    RecyclerView rcyMau;
    RecyclerView rcySize;


    private String user ;
    TextView soLuongKho;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        user = mySharedPreferences.getValue("remember_id_tk");

        anhXa();
        getIntentSanPham();
//        hienThiSoLuong();
    }

    private void anhXa() {
        imgPic = findViewById(R.id.itemPic);
        imgCart = findViewById(R.id.imageView4);
        txtName = findViewById(R.id.titleTxt);
        txtPrice = findViewById(R.id.priceTxt);
        txtTitle = findViewById(R.id.moTaTxt);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imgBack = findViewById(R.id.backBtn);
        imgBack.setOnClickListener(v -> finish());
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> addToCart());
//        badge = findViewById(R.id.menu_sl);

        imgCart.setOnClickListener(v -> {
            Intent intent = new Intent(ChiTietSanPhamActivity.this, Gio_Hang.class);
            startActivity(intent);
        });
    }

    public void getIntentSanPham() {
        Intent intent = getIntent();
        String tenSP = intent.getStringExtra("tensp");
        double giaban = intent.getDoubleExtra("giaban", 0.0);
        String ghiChu = intent.getStringExtra("ghichu");
        String imageUrl = intent.getStringExtra("imageUrl");
        Picasso.get().load(imageUrl).into(imgPic);


        txtName.setText(tenSP);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTien = decimalFormat.format(giaban);
        txtPrice.setText(formattedTien + " đ ");
        txtTitle.setText(ghiChu);
        Picasso.get().load(imageUrl).into(imgPic);
    }

    public void addToCart() {
        View dialogSheetView = LayoutInflater.from(context).inflate(R.layout.dialog_add_to_cart, null);
        dialog = new BottomSheetDialog(context);
        dialog.setContentView(dialogSheetView);
        dialog.show();

        ImageView imgPic = dialogSheetView.findViewById(R.id.itemPicCt);
        TextView txtTien = dialogSheetView.findViewById(R.id.txtMoney);
        TextView txtCong = dialogSheetView.findViewById(R.id.tvCong);
        TextView txtSo = dialogSheetView.findViewById(R.id.tvSo);
        TextView txtTru = dialogSheetView.findViewById(R.id.tvtru);
        Button btnAdd = dialogSheetView.findViewById(R.id.btnAddCt);

        rcyMau = dialogSheetView.findViewById(R.id.rcy_mau);
        hienThiSizeMau();

        Intent intent = getIntent();

        double giaban = intent.getDoubleExtra("giaban", 0.0);
        String imageUrl = intent.getStringExtra("imageUrl");

        soLuongKho = dialogSheetView.findViewById(R.id.soLuongKho);
        int slKho = intent.getIntExtra("soluongnhap", 0);
        soLuongKho.setText(Integer.toString(slKho));

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTien = decimalFormat.format(giaban);
        txtTien.setText(formattedTien + " đ ");

        Picasso.get().load(imageUrl).into(imgPic);

        txtTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (so > 1) {
                    so--;
                    txtSo.setText(String.valueOf(so));
                }
            }
        });

        txtCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so++;
                txtSo.setText(String.valueOf(so));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart();
            }
        });

    }

    private void addProductToCart() {
        Intent intent = getIntent();
        int maSP = intent.getIntExtra("masp", 0);
        String tenSP = intent.getStringExtra("tensp");
        double giaban = intent.getDoubleExtra("giaban", 0.0);
        String imageUrl = intent.getStringExtra("imageUrl");


        double tongTien = giaban * so;
        Log.d("HUY", String.valueOf(tongTien));

        if (color == null || size == null) {
            Toast.makeText(context, "Chưa chọn màu hoặc size", Toast.LENGTH_SHORT).show();
            return;
        }

        if (so > soLuongTrongKho) {
            Toast.makeText(context, "Số lượng sản phẩm trong kho không đủ", Toast.LENGTH_SHORT).show();
            return;
        }

        checkCart(giaban,tongTien);
    }

    private void checkCart(double giaBan,double tongTien) {
        ApiService.apiService.getCartByUser(user).enqueue(new Callback<GioHang>() {
            @Override
            public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getUserId().equals(user)) {
                    addChiTietGioHang(response.body().getGioHangID(),giaBan ,tongTien);
                } else {
                    addCart(giaBan,tongTien);
                }
            }

            @Override
            public void onFailure(Call<GioHang> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi khi gọi API kiểm tra giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addCart(double giaBan,double tongTien) {
        Random random = new Random();
        int randomInt = random.nextInt();
        String idGioHang = Integer.toString(randomInt);
        GioHang gioHang = new GioHang(idGioHang,user,tongTien);

        ApiService.apiService.creatCart(gioHang).enqueue(new Callback<GioHang>() {
            @Override
            public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                if (response.isSuccessful()) {
                    GioHang gioHangID = response.body();
                    if (gioHangID != null) {
                        Toast.makeText(context, "Thêm giỏ hàng thành công ID : " + gioHangID.get_id(), Toast.LENGTH_SHORT).show();
                        addChiTietGioHang(idGioHang,giaBan,tongTien);
                    }else {
                        Toast.makeText(context, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GioHang> call, Throwable t) {
                Toast.makeText(ChiTietSanPhamActivity.this, "API ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addChiTietGioHang(String idGioHang, double donGia, double tongTien) {
        ChiTietGioHang chiTietGioHang = new ChiTietGioHang(idGioHang, idChiTietSanPham, so, donGia, tongTien);
        ApiService.apiService.creatChiTietGioHangList(chiTietGioHang).enqueue(new Callback<ChiTietGioHang>() {
            @Override
            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm chi tiết giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(ChiTietSanPhamActivity.this, "Thêm chi tiết giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hienThiSizeMau() {
        Intent intent = getIntent();
        String maSP = intent.getStringExtra("masp");
        Log.d("HUY",maSP);


        ApiService.apiService.chiTietSanPhamListApi("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<ChiTietSanPham>>() {
            @Override
            public void onResponse(Call<List<ChiTietSanPham>> call, Response<List<ChiTietSanPham>> response) {
                chiTietSanPhamList = response.body();

                List<ChiTietSanPham> filteredList = new ArrayList<>();
                for (ChiTietSanPham ctSP : chiTietSanPhamList) {
                    if (ctSP.getProducts().equals(maSP)) {
                        filteredList.add(ctSP);
                    }
                }

                MauSizeAdapter mauSizeAdapter = new MauSizeAdapter(context, filteredList);
                rcyMau.setLayoutManager(new LinearLayoutManager(context));
                rcyMau.setAdapter(mauSizeAdapter);

                mauSizeAdapter.setOnItemClickListener(new MauSizeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String size, String mau, String idChiTietSanPham, double soLuongTrongKho) {
                        color = mau;
                        ChiTietSanPhamActivity.this.size = size;
                        ChiTietSanPhamActivity.this.idChiTietSanPham = idChiTietSanPham;
                        ChiTietSanPhamActivity.this.soLuongTrongKho = soLuongTrongKho;

                        soLuongKho.setText(String.valueOf(soLuongTrongKho));
                        Log.d("HUY", "Size and color:" + color + size + ", idChiTietSanPham: " + idChiTietSanPham + " số lượng :" + soLuongTrongKho);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ChiTietSanPham>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

