package com.tekkom.rpl.miro.listMikrolet.presenters;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;
import com.tekkom.rpl.miro.listMikrolet.models.Mikrolet;
import com.tekkom.rpl.miro.listMikrolet.models.Supir;
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

public class MapsMikroletPresenter {
    Context context;
    Listener listener;
    List<Mikrolet> mikroletList = new ArrayList<>();

    public MapsMikroletPresenter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void getMikrolets(final int idTipe, final String urlGambar) {
        listener.showDialog();

        JsonArrayRequest req = new JsonArrayRequest(Constant.GET_MIKROLET+idTipe,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Mikrolet mikrolet = new Mikrolet();

                                mikrolet.setId(jsonObject.getInt(Constant.ID));
                                mikrolet.setPlat_nomor(jsonObject.getString(Constant.PLAT_NOMOR));

                                TipeMikrolet tipe = new TipeMikrolet();
                                tipe.setId(idTipe);
                                tipe.setUrlGambar(urlGambar);
                                tipe.setNamaTipe(jsonObject.getString(Constant.TIPE));
                                mikrolet.setTipe(tipe);

                                Supir supir = new Supir();
                                supir.setNama(jsonObject.getString(Constant.SUPIR));
                                mikrolet.setSupir(supir);

                                LatLng latLng = new LatLng(jsonObject.getDouble(Constant.LATITUDE), jsonObject.getDouble(Constant.LONGITUDE));
                                mikrolet.setLokasiSekarang(latLng);

                                mikroletList.add(mikrolet);
                            }
                            listener.setData((ArrayList<Mikrolet>) mikroletList);
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
        void setData(ArrayList<Mikrolet> mikroletArrayList);
        void showDialog();
        void hideDialog();
    }
}
