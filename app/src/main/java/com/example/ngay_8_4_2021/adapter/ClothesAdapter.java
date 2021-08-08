package com.example.ngay_8_4_2021.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ngay_8_4_2021.R;
import com.example.ngay_8_4_2021.model.Clothes;

import java.util.ArrayList;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {
    private ArrayList<Clothes> arrayList;
    Context context;
    private OnItemClickListener mListener;

    public void setArrayList(ArrayList<Clothes> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public ClothesAdapter(ArrayList<Clothes> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_danhsachsp, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Clothes clothes = arrayList.get(position);
        ((ViewHolder) holder).setData(clothes);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_logo;
        TextView tv_name, tv_category, tv_price, tv_numbersave;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            img_logo = itemView.findViewById(R.id.img_clothes_logo);
            tv_name = itemView.findViewById(R.id.tv_clothes_name);
            tv_category = itemView.findViewById(R.id.tv_clothes_category);
            tv_price = itemView.findViewById(R.id.tv_clothes_price);
            tv_numbersave = itemView.findViewById(R.id.tv_clothes_numbersave);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void setData(Clothes clothes) {
            Glide
                    .with(context)
                    .load(clothes.getLogoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(img_logo);
            tv_name.setText(clothes.getName());
            tv_category.setText(clothes.getCategory());
            tv_price.setText("" + clothes.getPrice());
            tv_numbersave.setText("(" + clothes.getNumberSave() + ")");
        }
    }
}
