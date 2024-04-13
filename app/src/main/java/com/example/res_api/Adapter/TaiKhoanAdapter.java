package com.example.res_api.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.res_api.Model.User;
import com.example.res_api.R;
import com.example.res_api.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public TaiKhoanAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public TaiKhoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tai_khoan_, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaiKhoanAdapter.ViewHolder holder, int position) {

        User user = userList.get(position);
        holder.txtmk.setText(user.getMatKhau());
        holder.txtid.setText(user.get_id());
        holder.txtemail.setText(user.getGmail());
        holder.txttentk.setText(user.getTen());
        holder.txtdctk.setText(user.getDiaChi());
        holder.txtsdttk.setText(String.valueOf(user.getSoDienThoai()));
//        holder.txtnttk.setText(user.getNgayTao());
        holder.txttttk.setText(user.getTinhTrang());

    }


    @Override
    public int getItemCount() {
        if(userList != null ){
            return userList.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgtkh ;
        Button btnsua;
        TextView txtemail,txtmk,txtid,txttentk,txtdctk,txtsdttk,txtnttk,txttttk;
        CardView crdItemtk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            crdItemtk = itemView.findViewById(R.id.crdItemtk);
            txtid = itemView.findViewById(R.id.txtid);
            txtmk=itemView.findViewById(R.id.txtmk);
            imgtkh = itemView.findViewById(R.id.imgtk);
            txtemail = itemView.findViewById(R.id.txtemail);
            txttentk = itemView.findViewById(R.id.txttentk);
            txtdctk = itemView.findViewById(R.id.txtdctk);
            txtsdttk = itemView.findViewById(R.id.txtsdttk);
            txtnttk = itemView.findViewById(R.id.txtnttk);
            txttttk = itemView.findViewById(R.id.txttttk);
            btnsua=itemView.findViewById(R.id.btnsua);
        }
    }
}
