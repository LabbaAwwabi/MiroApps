package com.tekkom.rpl.miro.track.presenters;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.tekkom.rpl.miro.listMikrolet.models.Mikrolet;
import com.tekkom.rpl.miro.listMikrolet.models.Terminal;
import com.tekkom.rpl.miro.listMikrolet.models.TipeMikrolet;
import com.tekkom.rpl.miro.track.utils.Constant;
import com.tekkom.rpl.miro.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by awwabi on 19/01/17.
 */

public class MapsTrackPresenter {
    private List<Mikrolet> mikroletList = new ArrayList<>();
    private Context context;
    private Listener listener;
    private Mikrolet mikroletTerdekat;
    private Terminal terminal1, terminal2;

    public MapsTrackPresenter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getAllMikroletsLocation(final LatLng tujuan) {
        listener.showDialog();
        Log.i("debug", "getAllMikroletsLocation: url " + Constant.GET_ALL_MIKROLET);
        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_ALL_MIKROLET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("debug", "onResponse: " + response.toString());
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Mikrolet mikrolet = new Mikrolet();

                                mikrolet.setId(jsonObject.getInt(Constant.ID));
                                mikrolet.setPlat_nomor(jsonObject.getString(Constant.PLAT_NOMOR));

                                TipeMikrolet tipe = new TipeMikrolet();
                                tipe.setId(jsonObject.getInt(Constant.TIPE));
                                mikrolet.setTipe(tipe);

                                LatLng latLng = new LatLng(jsonObject.getDouble(Constant.LATITUDE), jsonObject.getDouble(Constant.LONGITUDE));
                                mikrolet.setLokasiSekarang(latLng);

                                Log.i("debug", "onResponse: " + mikrolet.toString());

                                mikroletList.add(mikrolet);
                            }
                            getTipeMikroletTerdekat(tujuan);
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
                Log.i("debug", "onErrorResponse: " + error.getMessage() + ", response " + error.getCause());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                listener.hideDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void getRute(int idTipe) {
        Log.i("debug", "getRute: url " + Constant.GET_TERMINAL+idTipe);
        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_TERMINAL+idTipe,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Terminal terminal = new Terminal();

                                terminal.setId(jsonObject.getInt(Constant.ID));
                                terminal.setNama(jsonObject.getString(Constant.NAMA));
                                terminal.setAlamat(jsonObject.getString(Constant.ALAMAT));
                                terminal.setLokasi(new LatLng(jsonObject.getDouble(Constant.LATITUDE), jsonObject.getDouble(Constant.LONGITUDE)));

                                if (i==0)
                                    terminal1 = terminal;
                                else
                                    terminal2 = terminal;
                            }
                            listener.setData(mikroletTerdekat, terminal1, terminal2);
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

    private void getTipeMikroletTerdekat(LatLng tujuan) {
        double minDistance = 0;
        int idTipe = 1;

        for (Mikrolet mikrolet : mikroletList) {
            //Getting both the coordinates
            LatLng from = mikrolet.getLokasiSekarang();
            LatLng to = tujuan;

            //Calculating the distance in meters
            Double distance = SphericalUtil.computeDistanceBetween(from, to);

            if (minDistance==0 || minDistance>distance) {
                minDistance = distance;
                idTipe = mikrolet.getTipe().getId();
                mikroletTerdekat = mikrolet;
            }
        }

        getMikroletPalingDekat(tujuan);
    }

    private void getMikroletPalingDekat(LatLng tujuan) {
        double minDistance = 0;
        int idTipe = 1;

        for (Mikrolet mikrolet : mikroletList) {
            //Getting both the coordinates
            LatLng from = mikrolet.getLokasiSekarang();
            LatLng to = tujuan;

            //Calculating the distance in meters
            Double distance = SphericalUtil.computeDistanceBetween(from, to);

            if (minDistance==0 || minDistance>distance) {
                minDistance = distance;
                idTipe = mikrolet.getTipe().getId();
                mikroletTerdekat = mikrolet;
            }
        }

        getRute(idTipe);
    }

    public interface Listener {
        void setData(Mikrolet mikroletTerdekat, Terminal terminal1, Terminal terminal2);
        void showDialog();
        void hideDialog();
    }
}
