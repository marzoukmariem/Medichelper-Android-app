package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import it.sephiroth.android.library.picasso.Picasso;

public class RDV_PatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv__patient);

        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.Open,R.string.Close);
        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView =  navigationView.inflateHeaderView(R.layout.header);
        TextView tv = (TextView)navView.findViewById(R.id.headerText);
        CircleImageView imuser=(CircleImageView)navView.findViewById(R.id.userconnecte);
        tv.setText(ActivityLogin.patientConnecté.getNom());
        Picasso.with(navView.getContext()).load("http://"+ActivityLogin.ip+"/medichelper/"+ActivityLogin.patientConnecté.getUrlImage()).into(imuser);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.historyRdv: {
                startActivity(new Intent(RDV_PatientActivity.this, MesRendezVous_patientActivity.class));
                break;
            }
            case R.id.logout: {
                startActivity(new Intent(RDV_PatientActivity.this, ActivityLogin.class));
                break;
            }
            case R.id.parametres: {
                startActivity(new Intent(RDV_PatientActivity.this, parametreprofil.class));
                break;
            }
        }
        //close navigation drawer

        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
