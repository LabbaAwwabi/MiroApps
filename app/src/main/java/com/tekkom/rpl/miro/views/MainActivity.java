package com.tekkom.rpl.miro.views;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.citySpot.views.CitySpotActivity;
import com.tekkom.rpl.miro.listMikrolet.views.ListMikroletActivity;
import com.tekkom.rpl.miro.models.Menu;
import com.tekkom.rpl.miro.track.views.TrackActivity;
import com.tekkom.rpl.miro.utils.Constant;
import com.tekkom.rpl.miro.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuAdapter.Listener {
    private String TAG = "MainActivity";
    private RecyclerView rvMenu;
    private List<Menu> listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvMenu = (RecyclerView) findViewById(R.id.rvMenu);

        buatMenu();

        MenuAdapter menuAdapter = new MenuAdapter(listMenu, this);

        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));
        rvMenu.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);
    }

    private void buatMenu() {
        //TODO: ubah desc
        listMenu = new ArrayList<>();
        listMenu.add(new Menu(Constant.MENU_TRACK, "desc", this.getResources().getIdentifier("ic_track", "drawable", this.getPackageName()), getResources().getColor(R.color.green_dark)));
        listMenu.add(new Menu(Constant.MENU_LIST, "desc", this.getResources().getIdentifier("ic_list", "drawable", this.getPackageName()), getResources().getColor(R.color.purple_dark)));
        listMenu.add(new Menu(Constant.MENU_SPOT, "desc", this.getResources().getIdentifier("ic_city", "drawable", this.getPackageName()), getResources().getColor(R.color.blue_dark)));
        listMenu.add(new Menu(Constant.MENU_ABOUT, "desc", this.getResources().getIdentifier("ic_about", "drawable", this.getPackageName()), getResources().getColor(R.color.red_dark)));
    }

    @Override
    public void pilihMenu(Menu menu) {
        Intent intent;
        if (menu.getTitle().equalsIgnoreCase(Constant.MENU_TRACK)) {
            intent = new Intent(this, TrackActivity.class);
        } else if (menu.getTitle().equalsIgnoreCase(Constant.MENU_LIST)) {
            intent = new Intent(this, ListMikroletActivity.class);
        } else if (menu.getTitle().equalsIgnoreCase(Constant.MENU_SPOT)) {
            intent = new Intent(this, CitySpotActivity.class);
        } else {
            intent = new Intent(this, AboutActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}