package com.example.lyostudio.finmath;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Aaron on 02/06/16.
 */

public class Capital extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capital_drawer);

        final EditText init_sum;
        final EditText percent;
        final EditText fin_sum;

        final Button definition;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton calculate = (FloatingActionButton) findViewById(R.id.calculate);

        init_sum = (EditText) findViewById(R.id.init_sum);
        percent=(EditText) findViewById(R.id.percent);
        fin_sum=(EditText) findViewById(R.id.fin_sum);

        assert calculate != null;
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt = 0;
                int poz = 0;
                if(init_sum.getText().toString().isEmpty()) {
                    cnt = cnt + 1;
                    poz = 1;
                }
                if(percent.getText().toString().isEmpty()) {
                    cnt = cnt + 1;
                    poz = 2;
                }
                if (fin_sum.getText().toString().isEmpty()) {
                    cnt = cnt + 1;
                    poz = 3;
                }
                if(cnt>1){
                    Snackbar.make(view, "Too few arguments", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(cnt==0){
                    Snackbar.make(view, "Nothing to be calculated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    if (poz == 3) {
                        float initsum = Float.parseFloat(init_sum.getText().toString());
                        float perc = Float.parseFloat(percent.getText().toString());
                        float sumfin = initsum * (1 + (perc / 100));
                        fin_sum.setText(Float.toString(sumfin));
                    } else if (poz == 2) {
                        float initsum = Float.parseFloat(init_sum.getText().toString());
                        float sumfin = Float.parseFloat(fin_sum.getText().toString());
                        float perc = (sumfin / initsum - 1) * 100;
                        percent.setText(Float.toString(perc));
                    } else if (poz == 1) {
                        float perc = Float.parseFloat(percent.getText().toString());
                        float sumfin = Float.parseFloat(fin_sum.getText().toString());
                        float initsum = sumfin / (1 + (perc / 100));
                        init_sum.setText(Float.toString(initsum));
                    }
                }
            }
        });
        definition = (Button) findViewById(R.id.def_cap);

        definition.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),definitionCapital.class);
                startActivity(intent);
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.simple_interest) {
            Intent sim_inst= new Intent(getApplicationContext(),SimpleInstallement.class);
            startActivity(sim_inst);
        } else if (id == R.id.comp_interest) {
            Intent comp_inst= new Intent(getApplicationContext(),ComplexInstallment.class);
            startActivity(comp_inst);
        } else if (id == R.id.VAT) {
            Intent vat = new Intent(getApplicationContext(),VAT.class);
            startActivity(vat);
        } else if (id == R.id.capital){
            Intent cap = new Intent(getApplicationContext(),Capital.class);
            startActivity(cap);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "VAT Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lyostudio.finmath/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "VAT Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lyostudio.finmath/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
