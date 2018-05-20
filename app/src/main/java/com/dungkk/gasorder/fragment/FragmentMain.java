package com.dungkk.gasorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dungkk.gasorder.passingObjects.location;
import android.widget.ImageButton;
import com.dungkk.gasorder.R;


public class FragmentMain  extends Fragment{

    private FragmentTransaction transaction;
    private ImageButton ibtn_order;
    private ImageButton ibtn_products;
    private ImageButton ibtn_tips;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ibtn_order = (ImageButton) view.findViewById(R.id.ibtn_order);
        ibtn_products = (ImageButton) view.findViewById(R.id.ibtn_products);
        ibtn_tips = (ImageButton) view.findViewById(R.id.ibtn_tips);

        ibtn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentOrder());
            }
        });

        ibtn_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentProducts());
            }
        });


        ibtn_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentTips());
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment).commit();
    }
}
