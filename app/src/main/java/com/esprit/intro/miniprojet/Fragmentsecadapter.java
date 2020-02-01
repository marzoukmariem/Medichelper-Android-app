package com.esprit.intro.miniprojet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Fragmentsecadapter extends FragmentStatePagerAdapter {
    public Fragmentsecadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        secretaite1 accueilDocteur1=new secretaite1();
        SecretaireRendezvous accueilDocteur2=new SecretaireRendezvous();

        Bundle bundle= new Bundle();
        position = position +1;
        if (position==1){
            bundle.putString("message","Hello from Page "+ position);
            accueilDocteur1.setArguments(bundle);
            return accueilDocteur1;}

        else {
            accueilDocteur2.setArguments(bundle);
            return accueilDocteur2;}
    }

    @Override
    public int getCount() {
        return 4;
    }

}
