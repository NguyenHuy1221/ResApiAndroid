package com.example.res_api.Adapter;

import static com.example.res_api.Activiti.Gio_Hang.mListChiTiet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.res_api.Model.ChiTietGioHang;
import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.Model.Products;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{
    private Context context;

    private List<ChiTietGioHang> productList;
    private final IclickListener iclickListener;

    public GioHangAdapter(Context context, List<ChiTietGioHang> productList, IclickListener iclickListener) {
        this.context = context;
        this.productList = productList;
        this.iclickListener = iclickListener;
    }

    public void updateData(List<ChiTietGioHang> newData) {
        mListChiTiet.clear();
        mListChiTiet.addAll(newData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChiTietGioHang chiTietGioHang = productList.get(position);

        double tongTien = chiTietGioHang.getSoLuong() * chiTietGioHang.getDonGia();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.txtGia.setText(decimalFormat.format(tongTien) + " đ");
        holder.txtSo.setText(String.valueOf(chiTietGioHang.getSoLuong()));

        duLieuChiTietSanPham(chiTietGioHang.getChiTietSanPhamID(),holder);


        holder.txtCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkSoLuongTrongKho(chiTietGioHang, holder);
            }
        });

        holder.txtTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chiTietGioHang.getSoLuong() > 1) {
                    chiTietGioHang.setSoLuong(chiTietGioHang.getSoLuong() - 1);

                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    holder.txtSo.setText(String.valueOf(chiTietGioHang.getSoLuong()));
                    updateChiTietGioHang(chiTietGioHang);
                    iclickListener.onItemChanged(chiTietGioHang);
                }
            }
        });

        holder.txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.onclickDeleteCart(chiTietGioHang);
            }
        });

    }

    private void duLieuChiTietSanPham(String idChiTietSanPham, ViewHolder holder) {
      ApiService.apiService.chitietsanphamID(idChiTietSanPham,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<ChiTietSanPham>() {
          @Override
          public void onResponse(Call<ChiTietSanPham> call, Response<ChiTietSanPham> response) {
              if (response.isSuccessful()) {
                  ChiTietSanPham chiTietSanPham = response.body();
                  if (chiTietSanPham != null) {
                      holder.txtMau.setText(chiTietSanPham.getColor());
                      holder.txtSize.setText(chiTietSanPham.getSize());
                      Log.d("HUY","id pro ducts : "+chiTietSanPham.getProducts());
                      duLieuSanPham(chiTietSanPham.getProducts(),holder);
                  }
              }
          }

          @Override
          public void onFailure(Call<ChiTietSanPham> call, Throwable t) {
              Toast.makeText(context, "CAll Api Error", Toast.LENGTH_SHORT).show();
          }
      });
    }

    private void duLieuSanPham(String masp, ViewHolder holder) {
        ApiService.apiService.showProductsID(masp,"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()) {
                    Products products = response.body();
                    if (products != null) {
                        holder.txtName.setText(products.getTen());
                        Picasso.get().load(products.getHinh()).into(holder.imgPic);
                    }
                }
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(context, "CAll API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkSoLuongTrongKho(ChiTietGioHang chiTietGioHang, ViewHolder holder) {
        ApiService.apiService.chiTietSanPhamListApi("42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<ChiTietSanPham>>() {
            @Override
            public void onResponse(Call<List<ChiTietSanPham>> call, Response<List<ChiTietSanPham>> response) {
                if (response.isSuccessful()){
                    List<ChiTietSanPham> chiTietSanPhams = response.body();
                    if (chiTietSanPhams != null) {
                        // Tính toán số lượng sản phẩm tồn kho và kiểm tra
                        int soLuongTrongKho = 0;
                        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
                            if (chiTietSanPham.get_id().equals(chiTietGioHang.getChiTietSanPhamID())) {
                                soLuongTrongKho = chiTietSanPham.getTotal();
                                break;
                            }
                        }

                        // Kiểm tra và xử lý tương ứng
                        if (soLuongTrongKho >= chiTietGioHang.getSoLuong() + 1) {
                            // Số lượng trong kho đủ, có thể cập nhật giỏ hàng
                            chiTietGioHang.setSoLuong(chiTietGioHang.getSoLuong() + 1);
                            holder.txtSo.setText(String.valueOf(chiTietGioHang.getSoLuong()));
                            updateChiTietGioHang(chiTietGioHang);
                            iclickListener.onItemChanged(chiTietGioHang);
                        } else {
                            // Hiển thị thông báo cho người dùng về việc số lượng trong kho không đủ
                            Toast.makeText(context, "Số lượng trong kho không đủ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietSanPham>> call, Throwable t) {
                Toast.makeText(context, "Call API Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateChiTietGioHang(ChiTietGioHang chiTietGioHang) {
        ApiService.apiService.updateChiTietGioHang(chiTietGioHang.get_id(), chiTietGioHang).enqueue(new Callback<ChiTietGioHang>() {
            @Override
            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                if (response.isSuccessful()) {
                    ChiTietGioHang updatedCTGH = response.body();
                    if (updatedCTGH != null) {
                        Toast.makeText(context, "Cập nhật chi tiết giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Cập nhật chi tiết giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Cập nhật chi tiết giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                Toast.makeText(context, "Lỗi khi gửi yêu cầu cập nhật chi tiết giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface IclickListener {
        void onItemChanged(ChiTietGioHang chiTietGioHang);
        void onclickDeleteCart(ChiTietGioHang chiTietGioHang);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPic;
        TextView txtName,txtGia,txtMau,txtSize,txtCong,txtSo,txtTru,txtXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPic = itemView.findViewById(R.id.img_pic_gh);
            txtName = itemView.findViewById(R.id.txt_ten_gh);
            txtGia = itemView.findViewById(R.id.txt_gia_gh);
            txtMau = itemView.findViewById(R.id.txt_mau_gh);
            txtSize = itemView.findViewById(R.id.txt_size_gh);
            txtCong = itemView.findViewById(R.id.tvCong);
            txtSo = itemView.findViewById(R.id.tvSo);
            txtTru = itemView.findViewById(R.id.tvtru);
            txtXoa = itemView.findViewById(R.id.txtXoa);
        }
    }

}
