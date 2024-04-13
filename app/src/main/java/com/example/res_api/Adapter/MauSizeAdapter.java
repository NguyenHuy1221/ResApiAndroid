package com.example.res_api.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Model.ChiTietSanPham;
import com.example.res_api.R;


import java.util.List;

public class MauSizeAdapter extends RecyclerView.Adapter<MauSizeAdapter.ViewHolder> {

    private Context context;
    private List<ChiTietSanPham> mListSanPham;
    private int selectedItemPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(String size, String mau,String idChiTietSanPham, double soLuongTrongKho);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public MauSizeAdapter(Context context, List<ChiTietSanPham> mListSanPham) {
        this.context = context;
        this.mListSanPham = mListSanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_size,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChiTietSanPham chiTietSanPham = mListSanPham.get(position);
        holder.txtSize.setText("size : "+chiTietSanPham.getSize());
        holder.txtMau.setText("màu : "+chiTietSanPham.getColor());


        // Kiểm tra xem mục hiện tại có được chọn không
        if (position == selectedItemPosition) {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.red));
        } else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(
                            chiTietSanPham.getSize(),
                            chiTietSanPham.getColor(),
                            chiTietSanPham.get_id(),
                            chiTietSanPham.getTotal()
                    );

                    int previousSelectedItem = selectedItemPosition;
                    selectedItemPosition = holder.getAdapterPosition();

                    notifyItemChanged(previousSelectedItem);
                    notifyItemChanged(selectedItemPosition);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return mListSanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSize,txtMau;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSize = itemView.findViewById(R.id.size_click);
            txtMau = itemView.findViewById(R.id.mau_click);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
