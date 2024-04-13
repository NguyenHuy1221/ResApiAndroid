package com.example.res_api.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.res_api.Model.ChiTietHoaDon;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.Products;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {

    private List<ChiTietHoaDon> mListHoaDon;

    public ChiTietDonHangAdapter(List<ChiTietHoaDon> mListHoaDon) {
        this.mListHoaDon = mListHoaDon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = mListHoaDon.get(position);
        holder.item_giaspchitiet.setText("SL : " + chiTietHoaDon.getSoLuong());
        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txt_gia.setText("Tổng : " + formatter.format(chiTietHoaDon.getDonGia()));

        duLieuChiTietSanPham(chiTietHoaDon.getChiTietSanPhamID(),holder);
    }

    private void duLieuChiTietSanPham(String idChiTietSanPham, ViewHolder holder) {
        ApiService.apiService.chitietsanphamID(idChiTietSanPham,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<ChiTietSanPham>() {
            @Override
            public void onResponse(Call<ChiTietSanPham> call, Response<ChiTietSanPham> response) {
                if (response.isSuccessful()) {
                    ChiTietSanPham chiTietSanPham = response.body();
                    if (chiTietSanPham != null) {
                        holder.txt_size.setText(chiTietSanPham.getSize());
                        String masp = chiTietSanPham.getProducts();
                        DuLieuSanPham(masp,holder);
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Không có dữ liệu chi tiết sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Lỗi khi lấy dữ liệu chi tiết sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietSanPham> call, Throwable t) {

            }
        });
    }

    private void DuLieuSanPham(String masp, ViewHolder holder){
        ApiService.apiService.showProductsID(masp,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()){
                    Products products = response.body();
                    if (products != null) {
                        holder.item_tenspchitiet.setText("Tên sp : " + products.getTen());
                        Picasso.get().load(products.getHinh()).into(holder.item_img_chhitiet);
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return mListHoaDon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_img_chhitiet;
        private TextView item_giaspchitiet,item_tenspchitiet,txt_size,txt_gia;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_img_chhitiet = itemView.findViewById(R.id.item_img_chhitiet);
            item_tenspchitiet = itemView.findViewById(R.id.item_tenspchitiet);
            item_giaspchitiet = itemView.findViewById(R.id.item_giaspchitiet);
            txt_gia = itemView.findViewById(R.id.item_giachitiet);
            txt_size = itemView.findViewById(R.id.item_sizechitiet);


        }
    }
}
