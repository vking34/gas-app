package com.dungkk.gasorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.dungkk.gasorder.fragment.*;
import com.dungkk.gasorder.passingObjects.User;
import com.dungkk.gasorder.signActivities.SignIn;
import com.dungkk.gasorder.signActivities.SignUp;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private MenuItem nav_main;
    private MenuItem nav_profile;
    private MenuItem nav_history;
    private MenuItem nav_signin;
    private MenuItem nav_signup;
    private MenuItem nav_signout;

    private FragmentManager manager;
    private  FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        Log.e("MainAcitivy", "receiving location object...");

        Intent currentIntent = getIntent();

        String addr = currentIntent.getStringExtra("address");

        if(addr != null)
        {
            Double lat = currentIntent.getDoubleExtra("lat",21.0084982);
            Double lng = currentIntent.getDoubleExtra("lng", 105.8386711);
            String ward = currentIntent.getStringExtra("ward");
            Log.e("Passing", "address: "+ addr + ", lat: " + lat);
            FragmentOrder fragmentOrder = new FragmentOrder();
            Bundle bundle = new Bundle();
            bundle.putDouble("lat", lat);
            bundle.putDouble("lng", lng);
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

        Menu menu = navigationView.getMenu();

        nav_main = menu.findItem(R.id.nav_main);
        nav_profile = menu.findItem(R.id.nav_profile);
        nav_history = menu.findItem(R.id.nav_history);
        nav_signin = menu.findItem(R.id.nav_signin);
        nav_signup = menu.findItem(R.id.nav_signup);
        nav_signout = menu.findItem(R.id.nav_signout);

        if(User.getUsername() != null){
            nav_signin.setVisible(false);
            nav_signup.setVisible(false);
        }
        else {
            nav_profile.setVisible(false);
            nav_history.setVisible(false);
            nav_signout.setVisible(false);
        }

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

            case R.id.nav_signin:
                Intent intent = new Intent(this, SignIn.class);
                startActivity(intent);
                break;

            case R.id.nav_signup:
                Intent intent1 = new Intent(this, SignUp.class);
                startActivity(intent1);
                break;

            case R.id.nav_signout:
                User.setUsername(null);
                Toast.makeText(MainActivity.this, "Signed Out", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_feedback:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
