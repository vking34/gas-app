package com.dungkk.gasorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.dungkk.gasorder.passingObjects.location;
import android.widget.ImageButton;
import com.dungkk.gasorder.fragment.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager manager;
    private  FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Log.e("MainAcitivy", "receiving location object...");
//        Bundle bundle = getIntent().getExtras();
//
//        if(bundle != null) {
//
//            location loc = (location) bundle.getParcelable("location");
//            if(loc != null)
//            {
//                FragmentOrder fragmentOrder = new FragmentOrder();
//                Bundle bundle1 = new Bundle();
//                bundle.putParcelable("com.dungkk.gasorder.passingObjects.location", loc);
//                fragmentOrder.setArguments(bundle1);
//                replaceFragment(fragmentOrder);
//
//            }
//            else {
//                replaceFragment(new FragmentMain());
//            }
//        }
//        else {
//            replaceFragment(new FragmentMain());
//        }

        Intent currentIntent = getIntent();
        Double lat = currentIntent.getDoubleExtra("lat",21.0084982);
        Double lng = currentIntent.getDoubleExtra("lng", 105.8386711);
        String addr = currentIntent.getStringExtra("address");
        String ward = currentIntent.getStringExtra("ward");

        if(addr != null)
        {
            Log.e("Passing", "address: "+ addr + "lat: " + lat);
            FragmentOrder fragmentOrder = new FragmentOrder();
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", 21.0084982);
            bundle.putDouble("lng", 105.8386711);
            bundle.putString("address", addr);
            bundle.putString("ward", ward);
            fragmentOrder.setArguments(bundle);
            replaceFragment(fragmentOrder);
        }
        else {
            replaceFragment(new FragmentMain());
        }

    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void replaceFragment(Fragment fragment){
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){

            case R.id.nav_main:
                replaceFragment(new FragmentMain());
                break;
            case R.id.nav_profile:
                replaceFragment(new FragmentProfile());
                break;
            case R.id.nav_history:
                replaceFragment(new FragmentHistory());
                break;
            case R.id.nav_login:

//                replaceFragment(new FragmentLogin());

                Intent intent = new Intent(this, signin.class);
                startActivity(intent);
                
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_feedback:
                break;
        }


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
