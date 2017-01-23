package com.tekkom.rpl.miro.listMikrolet.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.listMikrolet.models.TipeMikrolet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awwabi on 02/01/17.
 */

public class MikroletAdapter extends RecyclerView.Adapter<MikroletAdapter.ViewHolder> {
    
    private List<TipeMikrolet> mikroletList = new ArrayList<>();
    private Context context;
    private Listener listener;

    public MikroletAdapter(List<TipeMikrolet> mikroletList, Listener listener) {
        if (mikroletList.size()!=0)
            this.mikroletList.addAll(mikroletList);
        this.listener = listener;
    }

    public void add(TipeMikrolet tipeMikrolet) {
        mikroletList.add(tipeMikrolet);
        notifyItemInserted(mikroletList.size()-1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate item layout
        View mikroletView = inflater.inflate(R.layout.item_mikrolet, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(mikroletView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TipeMikrolet mikrolet = mikroletList.get(position);

        holder.tipe.setText("Mikrolet "+mikrolet.getNamaTipe());
        holder.stasiun.setText(mikrolet.getTerminals().get(0).getNama() + " - " + mikrolet.getTerminals().get(1).getNama());
        holder.ongkos.setText("Rp " + mikrolet.getOngkosNaik());
        holder.total.setText(mikrolet.getUnits() + " unit");
        Glide.with(context)
                .load(mikrolet.getUrlGambar())
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.gambar);

        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showMaps(mikrolet);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mikroletList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tipe, stasiun, ongkos,total;
        private ImageView gambar;
        private RelativeLayout wrapper;
        ViewHolder(View itemView) {
            super(itemView);
            tipe = (TextView) itemView.findViewById(R.id.tipe);
            stasiun = (TextView) itemView.findViewById(R.id.stasiun);
            ongkos = (TextView) itemView.findViewById(R.id.ongkos);
            total = (TextView) itemView.findViewById(R.id.total);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            wrapper = (RelativeLayout) itemView.findViewById(R.id.wrapper);
        }
    }
    
    public interface Listener {
        void showMaps(TipeMikrolet mikrolet);
    }
}
