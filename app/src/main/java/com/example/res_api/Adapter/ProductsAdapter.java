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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Products> mProducts;

    public ProductsAdapter(Context context, List<Products> mProducts) {
        this.context = context;
        this.mProducts = mProducts;
    }

    @NonNull
    @Override
    public ProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ViewHolder holder, int position) {
        Products products = mProducts.get(position);
        holder.txt_name.setText(products.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTien = decimalFormat.format(products.getDonGiaBan());
        holder.txt_pirce.setText(formattedTien + " đ");
        Picasso.get().load(products.getHinh()).into(holder.img_main);


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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name,txt_pirce;
        private ImageView img_main,img_tym;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_pirce = itemView.findViewById(R.id.txt_price);
            img_main = itemView.findViewById(R.id.img_main);
            img_tym = itemView.findViewById(R.id.img_tym);
        }
    }
}
