package com.example.res_api.Activiti;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.res_api.Model.Category;
import com.example.res_api.Model.Products;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.example.res_api.api.RealPathUtil;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPhamActivity extends AppCompatActivity {

    private List<Category> categoryList = new ArrayList<>();
    private List<Products> sanPhams = new ArrayList<>();
    private List<String> spinerThuongHieu = new ArrayList<>();
    private Context context = this;
    private ArrayList<Uri> selectedImages = new ArrayList<>();
    private static final int My_REQUEST_CODE = 10;
    private Uri mImageUri;
    private ImageView img_chinh;
    private ImageView img_ct;
    private EditText edt_masp, edt_tensp, edt_so_luong_sp, edt_gia_nhap, edt_gia_ban, edt_ghi_chu,Edt_so_luong_sp;
    private Spinner spn_loaisp, spn_mausp, spn_sizesp, spn_trang_thai;
    private Button btnAdd;
    private RecyclerView rcy_ct_anh;

    private LinearLayout sizeLayout;
    private Button buttonAddSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);




        anhxa();
        addSizeAndColor();
        showCategory();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSanPham();
            }
        });

        img_chinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermisson();
            }
        });

    }

    private void onClickRequestPermisson() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,My_REQUEST_CODE);
        }
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, My_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == My_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(img_chinh);
        }
    }

    public void anhxa() {
        img_chinh = findViewById(R.id.image_chinh);
        edt_masp = findViewById(R.id.edt_ma_sp);
        edt_tensp = findViewById(R.id.edt_ten_sp);
        edt_so_luong_sp = findViewById(R.id.edt_so_luong);
        edt_gia_nhap = findViewById(R.id.edt_gia_sp);
        edt_gia_ban = findViewById(R.id.edt_gia_ban_sp);
        spn_loaisp = findViewById(R.id.spn_loai_sp);
        spn_trang_thai = findViewById(R.id.spn_trang_thai_sp);
        edt_ghi_chu = findViewById(R.id.edt_ghi_chu_sp);
        sizeLayout = findViewById(R.id.sizeLayout);
        buttonAddSize = findViewById(R.id.buttonAddSize);
        btnAdd = findViewById(R.id.btn_them_sp);

    }

    public void addSizeAndColor() {

        buttonAddSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.generateViewId();
                TextView tvName= new TextView(ThemSanPhamActivity.this);
                tvName.setId(id);
                tvName.setText("Thêm sản phẩm " + id);

                Spinner spinnerSize = new Spinner(ThemSanPhamActivity.this);
                spinnerSize.setId(id);
                List<String> sizeMoi = new ArrayList<>();
                sizeMoi.add("none");
                sizeMoi.add("M");
                sizeMoi.add("L");
                sizeMoi.add("Xl");
                sizeMoi.add("XXL");

                ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(ThemSanPhamActivity.this, android.R.layout.simple_spinner_item, sizeMoi);
                sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSize.setAdapter(sizeAdapter);

                TextView tvMau = new TextView(ThemSanPhamActivity.this);
                tvMau.setText("Màu");
                Spinner spinnerMau = new Spinner(ThemSanPhamActivity.this);
                spinnerMau.setId(id);
                List<String> color = new ArrayList<>();
                color.add("none");
                color.add("Trắng");
                color.add("Vàng");
                color.add("Xanh");
                color.add("Đen");
                color.add("Đỏ");

                ArrayAdapter<String> mauAdapter = new ArrayAdapter<>(ThemSanPhamActivity.this, android.R.layout.simple_spinner_item, color);
                mauAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMau.setAdapter(mauAdapter);

                EditText editTextSoLuong = new EditText(ThemSanPhamActivity.this);
                editTextSoLuong.setText(String.valueOf(0));
                editTextSoLuong.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextSoLuong.setId(id);


                Button btnXoa = new Button(ThemSanPhamActivity.this);
                btnXoa.setText("Xóa");


                LinearLayout newLinearLayout = new LinearLayout(ThemSanPhamActivity.this);
                newLinearLayout.setOrientation(LinearLayout.VERTICAL);

                newLinearLayout.addView(tvName);
                newLinearLayout.addView(spinnerSize);
                newLinearLayout.addView(spinnerMau);
                newLinearLayout.addView(editTextSoLuong);
                newLinearLayout.addView(btnXoa);
                String uniqueTag = "layout_" + System.currentTimeMillis();

                newLinearLayout.setTag(uniqueTag);

                btnXoa.setTag(uniqueTag);

                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Lấy tag được gán cho LinearLayout cha
                        String tag = (String) view.getTag();

                        // Tìm LinearLayout có tag cụ thể
                        for (int i = 0; i < sizeLayout.getChildCount(); i++) {
                            View child = sizeLayout.getChildAt(i);
                            if (child instanceof LinearLayout && Objects.equals(child.getTag(), tag)) {
                                // Loại bỏ LinearLayout được tìm thấy khỏi sizeLayout
                                sizeLayout.removeView(child);
                                break; // Dừng tìm kiếm khi tìm thấy
                            }
                        }
                    }
                });

                sizeLayout.addView(newLinearLayout);

            }
        });


    }



    public void themSanPham() {

        String strRealPath = RealPathUtil.getRealPath(this,mImageUri);
        Log.d("HUY","thông tin" + strRealPath);
        File file = new File(strRealPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",file.getName(),requestBody);

        String masp = edt_masp.getText().toString().trim();
        String tensp = edt_tensp.getText().toString().trim();
        String solgspString  = String.valueOf(edt_so_luong_sp.getText().toString().trim());
        String gianhap = (edt_gia_nhap.getText().toString().trim());
        String giaban = (edt_gia_ban.getText().toString().trim());
        String loaisp = spn_loaisp.getSelectedItem().toString();
//        String trangthaisp = spn_trang_thai.getSelectedItem().toString();
        String ghichu = edt_ghi_chu.getText().toString().trim();

//        if (masp.isEmpty() || tensp.isEmpty() || gianhap.isEmpty() || giaban.isEmpty() ||
//                ghichu.isEmpty() ||  loaisp == null  || trangthaisp == null || solgspString.isEmpty()) {
//            Toast.makeText(context, "Chưa nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (masp.isEmpty() || tensp.isEmpty() || gianhap.isEmpty() || giaban.isEmpty()) {
            Toast.makeText(context, "Chưa nhập đủ dữ liệu", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService.apiService.creatProductsMulti(tensp,123.3,234,2,0,""
                ,"oke","hđ",filePart).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                Toast.makeText(context, "Thêm Thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(context, "Thêm Thất bại", Toast.LENGTH_SHORT).show();

            }
        });
//        Products products = new Products(tensp,mImageUri.toString(),Double.parseDouble(gianhap),Double.parseDouble(giaban),10,0,"65ffbf0cea77e8df2f481ad4","oke","123");
//        ApiService.apiService.creatProducts(products).enqueue(new Callback<Products>() {
//            @Override
//            public void onResponse(Call<Products> call, Response<Products> response) {
//                Toast.makeText(context, "Call API Succcses", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Products> call, Throwable t) {
//                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    public void showCategory() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinerThuongHieu);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_loaisp.setAdapter(arrayAdapter);

        ApiService.apiService.showCategory("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<String> categories = new ArrayList<>();
                for (Category category : response.body()) {
                    categories.add(category.getName());
                }

                arrayAdapter.addAll(categories);
                arrayAdapter.notifyDataSetChanged();

                spn_loaisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedCategory = categories.get(position);

                        for (Category category : response.body()) {
                            if (category.getName().equals(selectedCategory)) {
                                Log.d("HUY","id : " + category.get_id());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
