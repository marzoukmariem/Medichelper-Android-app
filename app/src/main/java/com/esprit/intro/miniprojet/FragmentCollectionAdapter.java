package com.esprit.intro.miniprojet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentCollectionAdapter extends FragmentStatePagerAdapter {
    public FragmentCollectionAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        AccueilDocteur1 accueilDocteur1=new AccueilDocteur1();
        AccueilDocteur2 accueilDocteur2=new AccueilDocteur2();
        AccueilDocteur3 accueilDocteur3=new AccueilDocteur3();
        AccueilDocteur4 accueilDocteur4=new AccueilDocteur4();
        Bundle bundle= new Bundle();
        position = position +1;
        if (position==1){
            bundle.putString("message","Hello from Page "+ position);
            accueilDocteur1.setArguments(bundle);
            return accueilDocteur1;}
        else if (position==2){
            accueilDocteur2.setArguments(bundle);
            return accueilDocteur2;}
        else if (position==3){
            accueilDocteur3.setArguments(bundle);
            return accueilDocteur3;}
            else {
                accueilDocteur4.setArguments(bundle);
                return accueilDocteur4;}
    }

    @Override
    public int getCount() {
        return 4;
    }

}
