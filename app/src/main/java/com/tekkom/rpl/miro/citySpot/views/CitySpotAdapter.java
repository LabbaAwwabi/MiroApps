package com.tekkom.rpl.miro.citySpot.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.citySpot.models.CitySpot;
import com.tekkom.rpl.miro.citySpot.models.GambarCitySpot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awwabi on 02/01/17.
 */

public class CitySpotAdapter extends RecyclerView.Adapter<CitySpotAdapter.ViewHolder> {
    
    private List<CitySpot> citySpotList = new ArrayList<>();
    private Context context;
    private Listener listener;

    public CitySpotAdapter(List<CitySpot> citySpotList, Listener listener) {
        this.citySpotList.addAll(citySpotList);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate item layout
        View citySpotView = inflater.inflate(R.layout.item_cityspot, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(citySpotView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CitySpot citySpot = citySpotList.get(position);

        holder.nama.setText(citySpot.getNama());
        holder.deskripsi.setText(citySpot.getDeskripsi());
        holder.alamat.setText(citySpot.getAlamat());
        Glide.with(context)
                .load(citySpot.getGambar().get(0).getUrlGambar())
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showDesc(citySpot);
            }
        });

        holder.gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.slideShow((ArrayList<GambarCitySpot>) citySpot.getGambar());
            }
        });

        holder.wrapperAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showMaps(citySpot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citySpotList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, deskripsi, alamat, detail;
        private ImageView gambar;
        private LinearLayout wrapperAlamat;
        ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama);
            deskripsi = (TextView) itemView.findViewById(R.id.deskripsi);
            alamat = (TextView) itemView.findViewById(R.id.alamat);
            detail = (TextView) itemView.findViewById(R.id.seeDetail);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            wrapperAlamat = (LinearLayout) itemView.findViewById(R.id.wrapper_alamat);
        }
    }
    
    public interface Listener {
        void slideShow(ArrayList<GambarCitySpot> gambar);
        void showMaps(CitySpot citySpot);
        void showDesc(CitySpot citySpot);
    }
}
