package com.example.res_api.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Activiti.ChiTietSanPhamActivity;
import com.example.res_api.Model.Products;
import com.example.res_api.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context mContext;
    private List<Products> mProducts;
//    private final OnClickItem onClickItem;

    public SanPhamAdapter(Context mContext, List<Products> mProducts) {
        this.mContext = mContext;
        this.mProducts = mProducts;
    }

//    public SanPhamAdapter(Context mContext, List<Products> mProducts, OnClickItem onClickItem) {
//        this.mContext = mContext;
//        this.mProducts = mProducts;
//        this.onClickItem = onClickItem;
//    }

    public void updateList(List<Products> sanPhamList){
        this.mProducts = sanPhamList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_san_pham,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products products = mProducts.get(position);
        Picasso.get().load(products.getHinh()).into(holder.imgSanPham);
        holder.tensp.setText(products.getTen());
//        holder.tenth.setText((CharSequence) products.getCategory());
        holder.giasp.setText(products.getDonGiaBan() +" $");

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickItem.onclikUpdate(products);
//            }
//        });
//
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickItem.onclickDelete(products);
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("masp",products.get_id());
                intent.putExtra("tensp",products.getTen());
                intent.putExtra("giaban",products.getDonGiaBan());
                intent.putExtra("soluongnhap",products.getSoLuongNhap());
                intent.putExtra("ghichu",products.getMoTa());
                if (products.getHinh() != null){
                    intent.putExtra("imageUrl",products.getHinh());
                }
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public interface OnClickItem {

        void onclikUpdate(Products sanPham);

        void onclickDelete(Products sanPham);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tensp,tenth,giasp;
        private ImageView edit, delete, imgSanPham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.tv_ten);
            tenth = itemView.findViewById(R.id.tv_thuonghieu);
            giasp = itemView.findViewById(R.id.tv_gia);
            edit = itemView.findViewById(R.id.suaThuongHieu);
            delete = itemView.findViewById(R.id.xoaThuongHieu);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
        }
    }
}
