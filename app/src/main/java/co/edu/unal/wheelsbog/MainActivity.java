package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    Button registerButtonRegister;
    Usuario users = new Usuario();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        queue = Volley.newRequestQueue(this);
        registerButtonRegister=findViewById(R.id.registerButtonRegister);

        registerButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        }

        private void ObtenerdatosVolley(){

            String url = "http://35.222.117.152:8080/api/users";
            JsonArrayRequest myRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {
                        for(int i=0;i<response.length();i++) {
                            JSONObject mJsonObject = response.getJSONObject(i);
                            String mId = mJsonObject.getString("email");
                         }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            /** StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONObject myJsonObject = new JSONObject(response);
                    text1.setText(myJsonObject.getString("latitude"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, volleyError -> text1.setText(volleyError.getMessage())); **/
            queue.add(myRequest);
        }


        private void PostUser(){
            String url = "http://35.222.117.152:8080/api/user";

            JSONObject js = new JSONObject();
            try {
                js.put("name","anything");
                js.put("email","gma@gmail.com");
                js.put("password","pass");
            } catch (JSONException e) {
                e.printStackTrace();
            }

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



