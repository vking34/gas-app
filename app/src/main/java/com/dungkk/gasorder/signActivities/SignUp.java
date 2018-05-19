package com.dungkk.gasorder.signActivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.dungkk.gasorder.MainActivity;
import com.dungkk.gasorder.R;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    // static vars
    private final static String URL = "http://192.168.1.2/signup";

    // Widgets
    private EditText et_firstName;
    private EditText et_lastName;
    private EditText et_username;
    private EditText et_password;
    private EditText et_password2;
    private EditText et_phoneNumber;
    private EditText et_email;
    private Button btn_signup;
    private TextView btn_backHome;

    private int checkCount = 0;

    private checkSignUpValidation check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_firstName = (EditText) findViewById(R.id.et_firstName);
        et_lastName = (EditText) findViewById(R.id.et_lastName);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        et_phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_backHome = (TextView) findViewById(R.id.btn_backHome);

        btn_signup.setEnabled(false);

        // Check validation
//        check = new checkSignUpValidation(et_firstName, et_lastName, et_username, et_password, et_password2, et_phoneNumber, et_email, btn_signup);
//        check.start();

        btn_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
//                check.stop();
                startActivity(intent);
            }
        });


        et_firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_signup.setEnabled(true);
                } else {
                    btn_signup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_password2.getText().toString().trim().equals(et_password.getText().toString())) {

                    // create a alterDialog Builder
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUp.this);
                    String mess = "Please try again.";

                    alertDialogBuilder.setTitle("Passwords does not match").setMessage(mess).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();
                    btn_signup.setEnabled(false);
                }
                else {
                    JSONObject user = new JSONObject();

                    try{
                        user.put("firstName", et_firstName.getText().toString());
                        user.put("lastName", et_lastName.getText().toString());
                        user.put("username", et_username.getText().toString());
                        user.put("password", et_firstName.getText().toString());
                        user.put("phoneNumber", et_phoneNumber.getText().toString());
                        user.put("email", et_email.getText().toString());
                    }
                    catch (JSONException e)
                    {
                        Log.e("Signup JSONObject", "error");
                    }

                    final RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, user,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        // create a alterDialog Builder
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUp.this);

                                        AlertDialog alertDialog;

                                        if(response.getBoolean("status")){
                                            String mess = "Please login to continue...";
                                            alertDialogBuilder.setTitle("Signup Up Successfully").setMessage(mess).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });


                                        }
                                        else {
                                            StringBuffer errorMess = new StringBuffer();
                                            switch (response.getInt("errorCode")){
                                                case 1:
                                                    errorMess.append("The username exists.");
                                                    break;
                                                case 2:
                                                    errorMess.append("Phone number exists");
                                                    break;
                                                case 3:
                                                    errorMess.append("Email exists");
                                                    break;
                                            }

                                            alertDialogBuilder.setTitle("Error").setMessage(errorMess).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    btn_signup.setEnabled(false);
                                                }
                                            });
                                        }

                                        alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // create a alterDialog Builder
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUp.this);
                                    alertDialogBuilder.setTitle("Connection failed").setMessage("Please check the internet connection again").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                }
                            }
                    );

                    requestQueue.add(jsonObjectRequest);
                }
            }
        });
    }
}
