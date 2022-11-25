package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConductorViaje extends FragmentActivity implements OnMapReadyCallback {
    private RequestQueue queue;
    String mId,mIdTrip,mdrivername,mcupos,mhoraencuetro,mhorallegada;
    ListView listView2;
    double i_lat,i_lng,l_lat,l_lng;
    private GoogleMap mMap;
    TextView namedriver,pacupo,h_encuentro,h_llegada;
    Button refresh;
    private List<String> passa= new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor_viaje);
        Intent i = getIntent();
        queue = Volley.newRequestQueue(this);
        namedriver=findViewById(R.id.namedriver);
        pacupo=findViewById(R.id.pacupo);
        h_encuentro=findViewById(R.id.h_encuentro);
        h_llegada=findViewById(R.id.h_llegada);
        refresh=findViewById(R.id.refresh);
        listView2 = findViewById(R.id.listView2);
        Obtenerdatosviaje();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(this);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerdatosVolley(mIdTrip);
            }
        });

    }
    private void Obtenerdatosviaje() {

        String url = "http://35.222.117.152:8080/api/trips";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int j=0;j<response.length();j++){
                    JSONObject mJsonObject = jsonArray.getJSONObject(j);
                    mIdTrip=mJsonObject.getString("id");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, volleyError -> Toast.makeText(getApplicationContext(), "Usuario o contraseña Incorrecto", Toast.LENGTH_LONG).show());
        queue.add(myRequest);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObtenerdatosVolley(mIdTrip);

    }
    private void ObtenerdatosVolley(String mIdTrip) {

        String url = "http://35.222.117.152:8080/api/trip/"+mIdTrip;
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject mJsonObject = new JSONObject(response);
                JSONObject driver=mJsonObject.getJSONObject("driver");
                mdrivername=driver.getString("name");
                namedriver.setText(mdrivername);
                JSONObject origin=mJsonObject.getJSONObject("origin");
                l_lat=Double.parseDouble(origin.getString("latitude"));
                l_lng=Double.parseDouble(origin.getString("longitude"));
                JSONObject destination=mJsonObject.getJSONObject("destination");
                i_lat=Double.parseDouble(destination.getString("latitude"));
                i_lng=Double.parseDouble(destination.getString("longitude"));
                mhoraencuetro=mJsonObject.getString("departureTime");
                h_encuentro.setText(mhoraencuetro);
                mhorallegada= mJsonObject.getString("arrivalTime");
                h_llegada.setText(mhorallegada);
                mcupos=mJsonObject.getString("passengerCapacity");
                pacupo.setText(mcupos);
                LatLng destino = new LatLng(l_lat, l_lng);
                LatLng llegada = new LatLng(i_lat,i_lng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(llegada));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(llegada,8));
                mMap.addMarker(new MarkerOptions()
                        .position(destino)
                        .title("Punto de llegada"));
                mMap.addMarker(new MarkerOptions()
                        .position(llegada)
                        .title("Punto de salida"));

                JSONArray passagers=mJsonObject.getJSONArray("passengers");
                for(int j=0;j<passagers.length();j++){

                    JSONObject passager= passagers.getJSONObject(j);
                    passa.add(passager.getString("name"));
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.textviewlist, passa);
                    listView2.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, volleyError -> Toast.makeText(getApplicationContext(), "Actualiza tu viaje :)", Toast.LENGTH_LONG).show());
        queue.add(myRequest);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

    }
}
