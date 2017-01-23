package com.tekkom.rpl.miro.listMikrolet.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.tekkom.rpl.miro.R;
import com.tekkom.rpl.miro.listMikrolet.models.Mikrolet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by awwabi on 07/01/17.
 */

public class MikroletBottomsheetFragment extends BottomSheetFragment implements MikroletBottomsheetAdapter.Listener {
    private static final String TAG = "RantalListFragment";

    private RecyclerView bottomSheet;
    private Listener listener;

    private List<Mikrolet> data = new ArrayList<>();

    private MikroletBottomsheetAdapter dataAdapter;
    private LinearLayoutManager linearLayoutManager;

    public MikroletBottomsheetFragment() {
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<Mikrolet> data) {
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rv_bottomsheet_fragment, container, false);
        bottomSheet = (RecyclerView) v.findViewById(R.id.bottom_sheet);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        dataAdapter = new MikroletBottomsheetAdapter(this);
        dataAdapter.append(data);
        bottomSheet.setAdapter(dataAdapter);
        bottomSheet.setLayoutManager(linearLayoutManager);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void viewDetail(Mikrolet mikrolet) {
        listener.seeDetail(mikrolet);
    }

    public interface Listener {
        void seeDetail(Mikrolet mikrolet);
    }
}
