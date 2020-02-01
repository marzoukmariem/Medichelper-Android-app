package com.esprit.intro.miniprojet;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

public class SwipeMenuDocteur extends AppCompatActivity {

    private ViewPager viewPager;
    private FragmentCollectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_docteur);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.pager);
        adapter = new FragmentCollectionAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item3: startActivity(new Intent(getApplicationContext(), Ajoutsecretairedoc.class));
            return true;
            case R.id.item4: startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
                return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuajoutsec, menu);

        return true;
    }
}
