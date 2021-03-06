package com.f2tdevproj.farmtotable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.User_Name);

        SharedPreferences sharedPreferences = getSharedPreferences("Default_User", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("User_Name", "null");
        Toast.makeText(this, "Hello " + user_name, Toast.LENGTH_SHORT).show();
        navUsername.setText(user_name);
        navigationView.setNavigationItemSelectedListener(this);

        ProduceCatalog_Frag produceCatalog_frag = new ProduceCatalog_Frag();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.RelativeLayout_for_Fragment, produceCatalog_frag).commit();
        getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();

        if (id == R.id.nav_ProductCatalog) {
            // Handle the camera action
            ProduceCatalog_Frag produceCatalog_frag = new ProduceCatalog_Frag();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.RelativeLayout_for_Fragment, produceCatalog_frag).commit();
            getSupportActionBar().setTitle("Home");
        }  if (id == R.id.nav_ShoppingCart) {
            // Handle the camera action
            ShoppingCart_Frag ShoppingCart_Frag = new ShoppingCart_Frag();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.RelativeLayout_for_Fragment, ShoppingCart_Frag).commit();
            getSupportActionBar().setTitle("Cart");

        }else if (id == R.id.nav_signout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
