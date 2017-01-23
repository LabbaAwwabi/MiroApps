package com.tekkom.rpl.miro.listMikrolet.presenters;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.listMikrolet.models.Terminal;
import com.tekkom.rpl.miro.listMikrolet.models.TipeMikrolet;
import com.tekkom.rpl.miro.listMikrolet.utils.Constant;
import com.tekkom.rpl.miro.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by awwabi on 16/01/17.
 */

public class ListMikroletPresenter {
    private List<TipeMikrolet> tipeMikroletList = new ArrayList<>();
    private Context context;
    private Listener listener;

    public ListMikroletPresenter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void loadData() {
        listener.showDialog();

        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_LIST_TIPEMIKROLET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                TipeMikrolet tipeMikrolet = new TipeMikrolet();

                                tipeMikrolet.setId(jsonObject.getInt(Constant.ID));
                                tipeMikrolet.setNamaTipe(jsonObject.getString(Constant.NAMA));
                                tipeMikrolet.setUrlGambar(jsonObject.getString(Constant.URL_GAMBAR));
                                tipeMikrolet.setOngkosNaik(jsonObject.getDouble(Constant.ONGKOS));
                                tipeMikrolet.setUnits(jsonObject.getInt(Constant.UNITS));

                                getTerminal(tipeMikrolet, jsonObject.getInt(Constant.TERMINAL1), jsonObject.getInt(Constant.TERMINAL2), (i == response.length() - 1));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                listener.hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void getTerminal(final TipeMikrolet tipeMikrolet, int t1, int t2, final boolean last) {
        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_TERMINAL+t1+"&t2="+t2,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            ArrayList<Terminal> terminals = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Terminal terminal = new Terminal();

                                terminal.setId(jsonObject.getInt(Constant.ID));
                                terminal.setNama(jsonObject.getString(Constant.NAMA));
                                terminal.setAlamat(jsonObject.getString(Constant.ALAMAT));
                                terminal.setLokasi(new LatLng(jsonObject.getDouble(Constant.LATITUDE), jsonObject.getDouble(Constant.LONGITUDE)));

                                terminals.add(terminal);
                            }
                            TipeMikrolet newTipeMikrolet = new TipeMikrolet(tipeMikrolet.getId(), tipeMikrolet.getNamaTipe(),
                                    tipeMikrolet.getUrlGambar(), terminals, tipeMikrolet.getOngkosNaik(), tipeMikrolet.getUnits());
                            //tipeMikroletList.add(newTipeMikrolet);
                            listener.addData(newTipeMikrolet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        listener.hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                listener.hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    public interface Listener {
        void addData(TipeMikrolet tipeMikrolet);
        void showDialog();
        void hideDialog();
    }
}
