package com.tekkom.rpl.miro.views;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.models.Menu;

import java.util.List;

/**
 * Created by awwabi on 19/12/16.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<Menu> menuList;
    private Listener listener;
    private Context context;

    public MenuAdapter(List<Menu> menuList, Listener listener) {
        this.menuList = menuList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate item layout
        View menuView = inflater.inflate(R.layout.item_menu, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(menuView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = menuList.get(position);

        holder.title.setText(menu.getTitle());
        Glide.with(context).load(menu.getGambar()).into(holder.gambar);

        holder.title.setTextColor(menu.getWarna());
        holder.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.pilihMenu(menu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gambar;
        RelativeLayout wrapper;
        CardView card;
        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            wrapper = (RelativeLayout) itemView.findViewById(R.id.wrapper);
            card = (CardView) itemView.findViewById(R.id.card_wrapper);
        }
    }

    public interface Listener {
        void pilihMenu(Menu menu);
    }
}
