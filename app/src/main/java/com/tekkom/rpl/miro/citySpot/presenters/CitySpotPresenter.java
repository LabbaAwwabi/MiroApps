package com.tekkom.rpl.miro.citySpot.presenters;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.citySpot.models.CitySpot;
import com.tekkom.rpl.miro.citySpot.models.GambarCitySpot;
import com.tekkom.rpl.miro.citySpot.utils.Constant;
import com.tekkom.rpl.miro.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by awwabi on 02/01/17.
 */

public class CitySpotPresenter {
    private List<CitySpot> citySpotList = new ArrayList<>();
    private Listener listener;
    private Context context;

    public CitySpotPresenter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getCitySpotData() {
        listener.showDialog();

        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_CITYSPOT,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        // Parsing json array response
                        // loop through each json object
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = (JSONObject) response.get(i);
                            CitySpot citySpot = new CitySpot();

                            citySpot.setId(jsonObject.getInt(Constant.ID));
                            citySpot.setNama(jsonObject.getString(Constant.NAMA));
                            citySpot.setDeskripsi(jsonObject.getString(Constant.DESKRIPSI));
                            citySpot.setAlamat(jsonObject.getString(Constant.ALAMAT));
                            citySpot.setLokasi(new LatLng(jsonObject.getDouble(Constant.LATITUDE), jsonObject.getDouble(Constant.LONGITUDE)));

                            getCitySpotGallery(citySpot, (i == response.length() - 1));
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

    private void getCitySpotGallery(final CitySpot citySpot, final boolean last) {
        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_GALLERY + citySpot.getId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            ArrayList<GambarCitySpot> gambarCitySpotArrayList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                GambarCitySpot gambarCitySpot = new GambarCitySpot();
                                JSONObject jsonObject = (JSONObject) response.get(i);

                                gambarCitySpot.setUrlGambar(jsonObject.getString(Constant.URL_GAMBAR));
                                gambarCitySpot.setKeterangan(jsonObject.getString(Constant.KETERANGAN));
                                gambarCitySpotArrayList.add(gambarCitySpot);
                            }
                            CitySpot newCitySpot = new CitySpot(citySpot.getId(), citySpot.getNama(), gambarCitySpotArrayList,
                                    citySpot.getDeskripsi(), citySpot.getAlamat(), citySpot.getLokasi());
                            citySpotList.add(newCitySpot);
                            if (last)
                                listener.setRvData((ArrayList<CitySpot>) citySpotList);
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
        void setRvData(ArrayList<CitySpot> citySpots);
        void showDialog();
        void hideDialog();
    }
}
