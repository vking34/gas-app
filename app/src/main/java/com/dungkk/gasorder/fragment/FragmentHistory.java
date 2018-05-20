package com.dungkk.gasorder.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dungkk.gasorder.R;
import com.dungkk.gasorder.passingObjects.User;
import com.dungkk.gasorder.signActivities.SignIn;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.dungkk.gasorder.passingObjects.Server;

public class FragmentHistory extends Fragment {


    private final static String URL = Server.getAddress() + "/orderHistory";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        JSONObject user = new JSONObject();
        JSONArray userArr = new JSONArray();
        try {
            user.put("username", User.getUsername());
            userArr.put(user);
            Log.e("History", "user: " + User.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, URL, userArr,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // do here


                            Log.e("History", response.getJSONObject(0).toString());

                        } catch (JSONException e) {

                            // create a alterDialog Builder
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                            alertDialogBuilder.setTitle("Empty List").setMessage("You haven't made any order...").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // create a alterDialog Builder
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                        alertDialogBuilder.setTitle("Connection failed").setMessage("Please check the internet connection again...").setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);

        return view;
    }
}
