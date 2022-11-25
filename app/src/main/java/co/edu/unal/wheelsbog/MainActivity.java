package co.edu.unal.wheelsbog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    Button registerButtonRegister,logButton;
    EditText mMail,mPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        queue = Volley.newRequestQueue(this);
        registerButtonRegister=findViewById(R.id.registerButtonRegister);
        logButton=findViewById(R.id.logButton);
        mMail=findViewById(R.id.mMail);
        mPass=findViewById(R.id.mPass);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObtenerdatosVolley(mMail.getText().toString());
            }
        });
        registerButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        }

        private void ObtenerdatosVolley(String email){

            String url = "http://35.222.117.152:8080/api/user/email/"+email;
            StringRequest myRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    String mpass="";
                    String mId="";

                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.has("password")) {
                        mpass = mJsonObject.getString("password");
                    }
                    else {
                        mpass ="";}
                    if(mpass.equals(mPass.getText().toString())) {
                        mId=mJsonObject.getString("id");
                        Intent i = new Intent(MainActivity.this,SaludoActivity.class);
                        i.putExtra("mId",mId);
                        startActivity(i);

                    }
                    else {Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña Incorrecto", Toast.LENGTH_LONG);
                        toast.show();}


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }, volleyError -> Toast.makeText(getApplicationContext(), "Usuario o contraseña Incorrecto", Toast.LENGTH_LONG).show());
            queue.add(myRequest);

        }
    }



