package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VerViajesActivity extends AppCompatActivity {
    private RequestQueue queue;
    ListView listView;
    String mId;
    private List<String> trips= new ArrayList<String>();
    private String mIdTrip,mIdDriver,mDrivername,mArraival,mDeparture,mPassager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.pasajero_ofertaviaje);
        listView =  findViewById(R.id.listView1);
        Intent i = getIntent();
        mId = i.getStringExtra("mId");
        queue = Volley.newRequestQueue(this);
        ObtenerdatosVolley();


        super.onCreate(savedInstanceState);
    }

    private void ObtenerdatosVolley() {

        String url = "http://35.222.117.152:8080/api/trips";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for(int j=0;j<response.length();j++){
                    JSONObject mJsonObject = jsonArray.getJSONObject(j);
                    mIdTrip=mJsonObject.getString("id");
                    JSONObject driver=mJsonObject.getJSONObject("driver");
                    mDrivername=driver.getString("name");
                    mIdDriver=driver.getString("id");
                    mDeparture=mJsonObject.getString("departureTime");
                    mArraival=mJsonObject.getString("arrivalTime");
                    mPassager=mJsonObject.getString("passengerCapacity");
                    trips.add( "Viaje número:"+mIdTrip+"\n"+"Conductor: " + mDrivername + "\n" + "Salida: " + mDeparture + "\n" + "Llegada: " + mArraival + "\n" + "Cupos: " + mPassager);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.textviewlist, trips);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String[] cut=trips.get(i).split("[\\r\\n]+");
                            String[] cut1=cut[0].split(":");
                            Intent intent = new Intent(VerViajesActivity.this,PasajeroAceptarActivity.class);
                            intent.putExtra("mId",mId);
                            intent.putExtra("mIdTrip",cut1[1]);
                            startActivity(intent);
                        }
                    });
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, volleyError -> Toast.makeText(getApplicationContext(), "Usuario o contraseña Incorrecto", Toast.LENGTH_LONG).show());
        queue.add(myRequest);


    }
}
