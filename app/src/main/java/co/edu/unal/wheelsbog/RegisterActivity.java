package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private RequestQueue queue;
    Button registerButton;
    EditText et_name,et_cel,et_email,et_password;
    String name,cel,email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        queue = Volley.newRequestQueue(this);
        registerButton=findViewById(R.id.registerButton);
        et_name=findViewById(R.id.nameRegister);
        et_email=findViewById(R.id.mailRegister);
        et_cel=findViewById(R.id.phoneRegister);
        et_password=findViewById(R.id.registerPass);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=et_name.getText().toString();
                cel=et_cel.getText().toString();
                email=et_email.getText().toString();
                password=et_password.getText().toString();
                PostUser(name,cel,email,password);
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


    }
    private void PostUser(String name,String cel,String email,String password){
        String url = "http://35.222.117.152:8080/api/user";

        JSONObject js = new JSONObject();
        try {
            js.put("name",name);
            js.put("email",email);
            js.put("password",password);
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
