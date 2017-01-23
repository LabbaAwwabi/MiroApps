package com.tekkom.rpl.miro.track.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.track.models.Lokasi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awwabi on 19/01/17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Lokasi> lokasiList = new ArrayList<>();
    private Context context;
    private Listener listener;

    public SearchAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(ArrayList<Lokasi> lokasi) {
        this.lokasiList = lokasi;
        notifyDataSetChanged();
    }

    public void clear() {
        this.lokasiList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate item layout
        View searchView = inflater.inflate(R.layout.item_search, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(searchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Lokasi lokasi = lokasiList.get(position);

        holder.alamat.setText(lokasi.getAlamat());
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.showTrack(lokasi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lokasiList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout wrapper;
        private TextView alamat;

        ViewHolder(View itemView) {
            super(itemView);
            wrapper = (LinearLayout) itemView.findViewById(R.id.wrapper);
            alamat = (TextView) itemView.findViewById(R.id.alamat);
        }
    }

    public interface Listener {
        void showTrack(Lokasi lokasi);
    }
}
