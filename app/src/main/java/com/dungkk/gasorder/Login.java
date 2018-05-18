package com.dungkk.gasorder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText user;
    EditText pass;
    Button login, signup;
    TextView guest;
    private static final int REQUEST_CODE = 0x11;
    private String json = Environment.getExternalStorageDirectory() + File.separator + "Gas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermision();
        user = (EditText) findViewById(R.id.firstname);
        pass = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.bt_login);
        guest = (TextView) findViewById(R.id.guest);
        signup = (Button) findViewById(R.id.bt_signup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        guest.setOnClickListener(this);

    };

    private void  wirteJson(String fileName, String body){
        File file = new File(json);
        if (!file.exists()){
            file.mkdirs();
        }
        File f = new File(file, fileName);
        try {
            FileWriter writer = new FileWriter(f, true);
            writer.write(body);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestPermision(){
        String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                File file = new File(json);
                if (!file.exists()){
                    file.mkdirs();
                }
            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                final String username = user.getText().toString();
                String password = pass.getText().toString();

//                String s = "{\"login\":[{\"username\":\""+username+"\", \"password\":\""+password+"\"}]}";
//                wirteJson("login.json", s);

                JSONObject userJson = new JSONObject();
                try {
                    userJson.put("username", username);
                    userJson.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://192.168.1.2/login";
                RequestQueue requestQueue = Volley.newRequestQueue(this);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userJson,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();
                                try {
                                    if(response.getBoolean("status")){

                                        User.setUsername(username);
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else {
                                        Toast.makeText(Login.this, "Error user or password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        }
                );

                requestQueue.add(jsonObjectRequest);
                break;

            case R.id.bt_signup:
                Intent intent0 = new Intent(this, SignUp.class);
                startActivity(intent0);
                finish();
                break;

            case R.id.guest:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
