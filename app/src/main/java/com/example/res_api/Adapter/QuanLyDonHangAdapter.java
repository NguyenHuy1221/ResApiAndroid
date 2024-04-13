package com.example.res_api.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDonHangAdapter extends RecyclerView.Adapter<QuanLyDonHangAdapter.ViewHolder> {

    private Context context;
    private List<HoaDon> mHoadon;

    public QuanLyDonHangAdapter(Context context, List<HoaDon> mHoadon) {
        this.context = context;
        this.mHoadon = mHoadon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = mHoadon.get(position);
        holder.tvSohoadon.setText("Số Hóa Đơn: " + hoaDon.getHoaDonID() + "");

        holder.tvNgaytao.setText("Ngày Tạo : " + hoaDon.getNgayTao());
        holder.tvDiaChi.setText("Địa chỉ : " + hoaDon.getDiaChi());


        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        DuLieuTaiKhoan(hoaDon.getKhachHangID(),holder);

//        holder.tvTrangThai.setText(trangThaiDonHang(hoaDon.getTrangThai()));
        holder.tvTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateStatusDialog(hoaDon);
            }
        });

    }

    private void DuLieuTaiKhoan(String idtk, QuanLyDonHangAdapter.ViewHolder holder){
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

    private void showUpdateStatusDialog(HoaDon hoaDon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chọn trạng thái mới");
        final String[] options = {"Đang xử lý", "Đã chấp nhận", "Thành công", "Đã hủy"};

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newStatus = which;
                updateOrderStatus(hoaDon, String.valueOf(newStatus));
            }
        });

        builder.show();
    }

    private void updateOrderStatus(HoaDon hoaDon, String newStatus) {
        hoaDon.setTrangThai(newStatus);
        notifyDataSetChanged();
    }



    private String trangThaiDonHang(int status) {
        String result = "";

        switch (status) {
            case 0:
                result = "Đơn hàng đang được xử lý ";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận ";
                break;
            case 2:
                result = "Thành công ";
                break;
            case 3:
                result = "Đơn hàng đã hủy ";
                break;
        }

        return result;
    }


    @Override
    public int getItemCount() {
        if (mHoadon != null) {
            return mHoadon.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSohoadon;
        private TextView tvTongtien;
        private TextView tvDiaChi;

        private TextView tvTinhtrang;
        private TextView tvNguoimua;
        private TextView tvDanhsachmathang;
        private TextView tvNgaytao;
        private TextView tvTrangThai;

        private TextView tvGiotao;
        private Button btnThanhToan;
        private CardView view_hoadon;
        private RecyclerView rcy_don_hang;

        public ViewHolder(@NonNull View itemView) {
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
