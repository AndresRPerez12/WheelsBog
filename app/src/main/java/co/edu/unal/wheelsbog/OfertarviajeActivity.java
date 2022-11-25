package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OfertarviajeActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private RequestQueue queue;
    private String lat,lng;
    TextView namedriver,pacupo,h_encuentro,h_llegada;
    Button aceptviaje;
    EditText horallegada,horaSalida,cupos;
    Button createtrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor_ofertarviaje);
        Intent i =getIntent();
        String mId=i.getStringExtra("mId");
        queue = Volley.newRequestQueue(this);
        horallegada=findViewById(R.id.horallegada);
        horaSalida=findViewById(R.id.hour);
        cupos=findViewById(R.id.cupo);
        createtrip=findViewById(R.id.createtrip);


        createtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int cuposs=Integer.parseInt(cupos.getText().toString());
                    String salida=horaSalida.getText().toString();
                    String llegada=horallegada.getText().toString();
                    PostUser(mId,lat,lng,salida,llegada,cuposs);
                    Intent intent = new Intent(OfertarviajeActivity.this,ConductorViaje.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bogota = new LatLng(4.60971, -74.08175);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota,10));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                LatLng marker = new LatLng(latLng.latitude , latLng.longitude);
                lat=""+latLng.latitude;
                lng=""+latLng.longitude;
                mMap.addMarker(new MarkerOptions()
                        .position(marker)
                        .title("Punto de salida"));


            }
        });
    }
    private void PostUser(String mId,String lat,String lng,String salida,String llegada, int cupos) throws JSONException {
        String url = "http://35.222.117.152:8080/api/trip";

        JSONObject js = new JSONObject("{\"driver\":{\"id\":"+ mId +"},\"origin\":{\"latitude\": \"4.637930920170574\",\"longitude\": \"-74.08567695775555\"},\"destination\":{\"latitude\": "+lat+",\"longitude\": "+lng+"},\"departureTime\":\""+salida+"\",\"arrivalTime\":\""+llegada+"\",\"passengerCapacity\":"+cupos+ " ,\"passengers\":[]}");


        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d( "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);

    }

}
