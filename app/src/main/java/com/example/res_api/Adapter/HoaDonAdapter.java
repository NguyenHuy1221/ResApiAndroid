package com.example.res_api.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Model.ChiTietHoaDon;
import com.example.res_api.Model.HoaDon;
import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;


import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHoder> {

    private Context context;
    private List<HoaDon> mHoadon;

    public HoaDonAdapter(Context context, List<HoaDon> mHoadon) {
        this.context = context;
        this.mHoadon = mHoadon;
    }

    @NonNull
    @NotNull
    @Override
    public HoaDonViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hoadon, parent, false);
        return new HoaDonViewHoder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull HoaDonViewHoder holder, int position) {
        HoaDon hoaDon = mHoadon.get(position);
        holder.tvSohoadon.setText("Số Hóa Đơn: " + hoaDon.getHoaDonID() + "");

        holder.tvNgaytao.setText("Ngày Tạo : " + hoaDon.getNgayTao());
        holder.tvDiaChi.setText("Địa chỉ : " + hoaDon.getDiaChi());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
//        String formattedTien = decimalFormat.format(hoaDon.get());
//        holder.tvTongtien.setText("Tổng tiền : " + formattedTien + " đ");

        DuLieuTaiKhoan(hoaDon.getKhachHangID(),holder);

        ApiService.apiService.showCTHDidHD(hoaDon.getHoaDonID(),"42334ca8-99e0-4935-91d7-ee568d5b3f6a").enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (response.isSuccessful()) {
                    List<ChiTietHoaDon> chiTietHoaDonList = response.body();
                    if (chiTietHoaDonList != null && !chiTietHoaDonList.isEmpty()) {
                        ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(chiTietHoaDonList);
                        holder.rcy_don_hang.setAdapter(chiTietDonHangAdapter);
                    } else {
                        Toast.makeText(context, "Không có chi tiết hóa đơn", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Lỗi khi lấy chi tiết hóa đơn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
                Toast.makeText(context, "Lỗi khi lấy cthd", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void DuLieuTaiKhoan(String idtk, HoaDonAdapter.HoaDonViewHoder holder){
        ApiService.apiService.showUserId(idtk,"").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    holder.tvNguoimua.setText("Khách hàng : "+user.getTen());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    @Override
    public int getItemCount() {
        if (mHoadon != null) {
            return mHoadon.size();
        }
        return 0;
    }

    public class HoaDonViewHoder extends RecyclerView.ViewHolder {
        private TextView tvSohoadon;
        private TextView tvTongtien;
        private TextView tvTinhtrang;
        private TextView tvDiaChi;
        private TextView tvNguoimua;
        private TextView tvDanhsachmathang;
        private TextView tvNgaytao;
        private TextView tvTrangThai;
        private TextView tvGiotao;
        private Button btnThanhToan;
        private CardView view_hoadon;
        private RecyclerView rcy_don_hang;

        public HoaDonViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
//            tvTinhtrang = itemView.findViewById(R.id.tvTinhtrang);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            tvNguoimua = itemView.findViewById(R.id.tvNguoimua);
            tvSohoadon = itemView.findViewById(R.id.tvSohoadon);
            tvNgaytao = itemView.findViewById(R.id.tvNgaymua);
            tvGiotao = itemView.findViewById(R.id.tvGiomua);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            view_hoadon = itemView.findViewById(R.id.view_hoadon);
            rcy_don_hang = itemView.findViewById(R.id.rcy_donhang);
            rcy_don_hang.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        }

    }
}
