package com.example.voltagang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.voltagang.Fragment.AddSportFragment;
import com.example.voltagang.Fragment.AnalyseFragment;
import com.example.voltagang.Fragment.ProfilFragment;
import com.example.voltagang.Fragment.SettingsFragment;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    private SpaceNavigationView navigationFragment;
    private FrameLayout containerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationFragment = findViewById(R.id.navigationFragment);
        containerFragment = findViewById(R.id.containerFragment);

        navigationFragment.initWithSaveInstanceState(savedInstanceState);
        navigationFragment.addSpaceItem(new SpaceItem("", R.drawable.ic_chart_black));
        navigationFragment.addSpaceItem(new SpaceItem("", R.drawable.ic_add_black));
        navigationFragment.addSpaceItem(new SpaceItem("", R.drawable.ic_person_black));
        navigationFragment.addSpaceItem(new SpaceItem("", R.drawable.ic_settings_black));

        loadFragment(new AnalyseFragment());

        navigationFragment.setSpaceOnClickListener(new SpaceOnClickListener() {
            Fragment fragment = null;
            @Override
            public void onCentreButtonClick() {
                navigationFragment.setCentreButtonSelectable(true);
            }
            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        fragment = new AnalyseFragment();
                        loadFragment(fragment);
                        break;
                    case 1:
                        fragment = new AddSportFragment();
                        loadFragment(fragment);
                        break;
                    case 2:
                        fragment = new ProfilFragment();
                        loadFragment(fragment);
                        break;
                    case 3:
                        fragment = new SettingsFragment();
                        loadFragment(fragment);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });




    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
