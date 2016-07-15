package mapscom.kenny.maps;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback{

    public FirstMapFragment mFirstMapFragment;
    public final String URL = "http://jsonplaceholder.typicode.com/users";
    public TextView txt ;
    public JSONObject geo;
    public static GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // call to class asingtask GetDataAsync
        new GetDataAsync().execute();

        //creamos una nueva instancia del fragmento que contiene el mapa
        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor , mFirstMapFragment)
                .commit();

        //le seteamos el mapa al fragmente
        mFirstMapFragment.getMapAsync(this);
    }


    //metodo donde se definen los atributos del map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLong latLong = new LatLong();
        Log.i("TAG", String.valueOf(latLong.getLat()));

        //obtenemos longitud y latitud
        double lat = latLong.getLat();
        double lon = latLong.getLon();
        String name = latLong.getTitle();


        LatLng position = new LatLng(lat ,lon);
            googleMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title("title: " + lat + lon));

        //creamos las opciones de camara
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(position)
                .zoom(1)
                .build();

        //seteamos la camara al mapa
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    //clase asincrona
    private class GetDataAsync extends AsyncTask<String, Void, String> {


        public GetDataAsync() {

        }

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {
            try {
                //creamos random y llamamos al webservice
                Random random = new Random();
                int position = random.nextInt(10 - 0 + 1);
                getData(URL, position);

                return "";
            } catch (Exception e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }


        @Override
        protected void onPostExecute(String result) {
            //refreshMap();
        }


        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    //webservice
    public void getData(String url, final int position) throws JSONException {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(getApplicationContext(), "Responce sucessfull", Toast.LENGTH_LONG).show();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(position);
                            JSONObject adrees = jsonObject.getJSONObject("address");
                            String name = jsonObject.getString("name");
                            geo = adrees.getJSONObject("geo");
                            //Toast.makeText(getApplicationContext(), geo.toString(), Toast.LENGTH_LONG).show();
                            double lat=geo.getDouble("lat");
                            double lon=geo.getDouble("lng");


                            Toast.makeText(getApplicationContext(), lat + lon+ name, Toast.LENGTH_LONG).show();
                            //guardamos los datos de la respuesta
                            LatLong latlon = new LatLong(lat, lon, name);
                            latlon.setLat(lat);
                            latlon.setLon(lon);
                            latlon.setTitle(name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TAG", "Error: " + error.getMessage());

            }
        }
        );
        requestQueue.add(req);

    }


}
