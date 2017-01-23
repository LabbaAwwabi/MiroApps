package com.tekkom.rpl.miro.citySpot.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.citySpot.models.CitySpot;

/**
 * Created by awwabi on 17/01/17.
 */

public class DeskripsiDialogFragment extends DialogFragment {
    private TextView deskripsi;
    private Button btn;
    private CitySpot citySpot;

    public DeskripsiDialogFragment() {
    }

    public static DeskripsiDialogFragment newInstance(CitySpot citySpot) {
        DeskripsiDialogFragment frag = new DeskripsiDialogFragment();
        frag.citySpot = citySpot;
        Bundle args = new Bundle();
        args.putString("title", citySpot.getNama());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_cityspot, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        deskripsi = (TextView) view.findViewById(R.id.deskripsi);
        btn = (Button) view.findViewById(R.id.backButton);

        deskripsi.setText(citySpot.getDeskripsi());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", citySpot.getNama());

        getDialog().setTitle(title);
    }
}
