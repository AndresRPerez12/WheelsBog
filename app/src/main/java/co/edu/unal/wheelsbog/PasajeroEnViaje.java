package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

public class PasajeroEnViaje extends FragmentActivity implements OnMapReadyCallback {
    private RequestQueue queue;
    String mId,mIdTrip,mdrivername,mcupos,mhoraencuetro,mhorallegada;
    double i_lat,i_lng,l_lat,l_lng;
    private GoogleMap mMap;
    TextView namedriver,h_encuentro,h_llegada;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pasajero_descripcionviaje);
        Intent i = getIntent();
        mId = i.getStringExtra("mId");
        mIdTrip = i.getStringExtra("mIdTrip");
        queue = Volley.newRequestQueue(this);
        namedriver=findViewById(R.id.namedriver);
        h_encuentro=findViewById(R.id.h_encuentro);
        h_llegada=findViewById(R.id.h_llegada);
        ObtenerdatosVolley(mIdTrip);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(this);

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
                LatLng destino = new LatLng(l_lat, l_lng);
                LatLng llegada = new LatLng(i_lat,i_lng);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(llegada));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(llegada,25));
                mMap.addMarker(new MarkerOptions()
                        .position(destino)
                        .title("Punto de llegada"));
                mMap.addMarker(new MarkerOptions()
                        .position(llegada)
                        .title("Punto de salida"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, volleyError -> Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show());
        queue.add(myRequest);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

    }

}
