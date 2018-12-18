package com.example.x9090.buku;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.BukuViewHolder>{

    private Context mCtx;
    private List<Buku> bukuList;
    OnBukuClicked clickHandler;

    public BukuAdapter() {
    }

    public BukuAdapter(Context mCtx, List<Buku> bukuList) {
        this.mCtx = mCtx;
        this.bukuList = bukuList;
    }

    public void setmCtx(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void setBukuList(List<Buku> bukuList) {
        this.bukuList = bukuList;
    }

    public void setClickHandler(OnBukuClicked clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public BukuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.buku_list, null);
        return new BukuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BukuViewHolder holder, int position) {
        Buku buku = bukuList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(buku.getPhoto_path())
                .into(holder.imageView);

        holder.textViewJudul.setText(buku.getJudul());
        holder.textViewPenulis.setText(buku.getPenulis());

        if(buku.fav == 1){
            holder.imageFav.setVisibility(View.VISIBLE);
        }else{
            holder.imageFav.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return bukuList.size();
    }

    class BukuViewHolder extends RecyclerView.ViewHolder {

        TextView textViewJudul, textViewPenulis;
        ImageView imageView,imageFav;

        public BukuViewHolder(View itemView) {
            super(itemView);

            textViewJudul = itemView.findViewById(R.id.textViewJudul);
            textViewPenulis = itemView.findViewById(R.id.textViewPenulis);
            imageView = itemView.findViewById(R.id.imageView);
            imageFav = itemView.findViewById(R.id.imageFav);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Buku buku = bukuList.get(getAdapterPosition());
                    clickHandler.BukuClicked(buku);
                }
            });
        }
    }

    public interface OnBukuClicked{
        void BukuClicked(Buku buku);
    }
}
