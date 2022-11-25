package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SaludoActivity extends AppCompatActivity
{
    private RequestQueue queue;
    Button conductor,pasajero;
    TextView saludo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.saludo);
        Intent i =getIntent();
        String mId=i.getStringExtra("mId");
        saludo=findViewById(R.id.saludo);
        conductor= findViewById(R.id.conductor);
        pasajero= findViewById(R.id.pasajero);
        queue = Volley.newRequestQueue(this);
        ObtenerdatosVolleyinicial(mId);
        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SaludoActivity.this,OfertarviajeActivity.class);
                i.putExtra("mId",mId);
                startActivity(i);
            }
        });
        pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SaludoActivity.this,VerViajesActivity.class);
                i.putExtra("mId",mId);
                startActivity(i);
            }
        });
        super.onCreate(savedInstanceState);
    }
    private void ObtenerdatosVolleyinicial(String mId){
        String url ="http://35.222.117.152:8080/api/user/"+mId ;
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {

                JSONObject mJsonObject = new JSONObject(response);
                saludo.setText("¡Hola! "+mJsonObject.getString("name"));
                }


             catch (JSONException e) {
                e.printStackTrace();
            }

        }, volleyError -> System.out.println(volleyError.getMessage()));
        queue.add(myRequest);
    }
}


