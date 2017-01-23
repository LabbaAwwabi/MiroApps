package com.tekkom.rpl.miro.listMikrolet.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.listMikrolet.models.Mikrolet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awwabi on 07/01/17.
 */

public class MikroletBottomsheetAdapter extends RecyclerView.Adapter<MikroletBottomsheetAdapter.ViewHolder> {
    Context context;
    List<Mikrolet> mikroletList = new ArrayList<>();
    Listener listener;

    public MikroletBottomsheetAdapter(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate item layout
        View bottomsheetView = inflater.inflate(R.layout.bottomsheet_mikrolet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(bottomsheetView);
        return viewHolder;
    }

    public void append(List<Mikrolet> data) {
        if(data!=null) {
            this.mikroletList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Mikrolet mikrolet = mikroletList.get(position);

        holder.plat.setText(mikrolet.getPlat_nomor());
        Glide.with(context)
                .load(mikrolet.getTipe().getUrlGambar())
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);
        holder.supir.setText(mikrolet.getSupir().getNama());
        holder.latlng.setText(mikrolet.getLokasiSekarang().latitude + ", " + mikrolet.getLokasiSekarang().longitude);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.viewDetail(mikrolet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mikroletList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public static int itemHeight = 160;
        ImageView gambar;
        TextView plat, latlng, supir;
        RelativeLayout wrapper;
        ViewHolder(View itemView) {
            super(itemView);
            gambar = (ImageView) itemView.findViewById(R.id.thumbnail);
            plat = (TextView) itemView.findViewById(R.id.plat_nomor);
            latlng = (TextView) itemView.findViewById(R.id.latLng);
            supir = (TextView) itemView.findViewById(R.id.supir);
            wrapper = (RelativeLayout) itemView.findViewById(R.id.wrapper);
            ViewTreeObserver vto = itemView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    ViewHolder.this.itemView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ViewHolder.this.itemHeight = ViewHolder.this.itemView.getMeasuredHeight();
                }
            });
        }
    }

    public interface Listener {
        void viewDetail(Mikrolet mikrolet);
    }
}
