package com.esprit.intro.miniprojet;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class secretaoreswipe extends AppCompatActivity {
    private ViewPager viewPager;
    private Fragmentsecadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secretaoreswipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.pager);
        adapter = new Fragmentsecadapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.decon: startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.deconexion, menu);

        return true;
    }
}
