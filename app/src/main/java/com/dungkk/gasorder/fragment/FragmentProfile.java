package com.dungkk.gasorder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dungkk.gasorder.Login;
import com.dungkk.gasorder.MainActivity;
import com.dungkk.gasorder.R;
import com.dungkk.gasorder.User;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentProfile extends Fragment{

    TextView name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        name = (TextView) view.findViewById(R.id.fullname);

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("username", User.getUsername());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://192.168.1.2/profile";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            if(response.getBoolean("status")){
                                name.setText(response.getString("name"));
                            }
                            else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
        return view;
    }
}
