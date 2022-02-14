package com.example.ayodagang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ayodagang.model.Barang;

import java.util.ArrayList;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    private final ArrayList<Barang> mBarangData;
    private Context mContext;


    public BarangAdapter(Context context, ArrayList<Barang> barangData) {
        this.mBarangData = barangData;
        this.mContext = context;
    }

    @Override
    public BarangAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.listproduk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BarangAdapter.ViewHolder holder, int position) {
        Barang currentBarang = mBarangData.get(position);

        holder.bindTo(currentBarang);
    }

//    @Override
//    public void onBindViewHolder(BarangAdapter.ViewHolder holder, int position) {
//        // Get current sport.
//        Barang currentBarang = mBarangData.get(position);
//
//        // Populate the textviews with data.
////        holder.bindTo(currentBarang);
//    }

    @Override
    public int getItemCount() {
        return mBarangData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mBrand;
        private TextView mName;
        private TextView mQty;
        private ImageView mImg;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mBrand = itemView.findViewById(R.id.brandName);
            mName = itemView.findViewById(R.id.productName);
            mQty = itemView.findViewById(R.id.qty);
            mImg = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        void bindTo(Barang currentBarang){
            // Populate the textviews with data.
            mBrand.setText(currentBarang.getBrand());
            mName.setText(currentBarang.getName());
            mQty.setText(currentBarang.getQty());
            Glide.with(itemView.getContext()).load(currentBarang.getImg()).into(mImg);
        }

        @Override
        public void onClick(View v) {
            Barang currentBarang = mBarangData.get(getAdapterPosition());

            Intent detailIntent = new Intent(mContext, DetailProduk.class);
            detailIntent.putExtra("name", currentBarang.getName());
            detailIntent.putExtra("brand", currentBarang.getBrand());
            detailIntent.putExtra("qty", currentBarang.getQty());
            detailIntent.putExtra("image", currentBarang.getImg());
            mContext.startActivity(detailIntent);

        }
    }

}
