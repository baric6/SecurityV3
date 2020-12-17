package com.baric.securityv3;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UploadActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager2 uploadPager;
    TabLayout uploadTabLayout;
    TabItem uploadSecurityTab, uploadProgrammingTab, uploadPdfTab;
    UploadAdapter uploadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        //for toolbar and menu
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        //pager
        uploadPager = findViewById(R.id.uploadViewPager);
        uploadTabLayout = findViewById(R.id.uploadTab);
        //fragments
        uploadSecurityTab = findViewById(R.id.uploadSecurityTab);
        uploadProgrammingTab = findViewById(R.id.uploadProgrammingTab);
        uploadPdfTab = findViewById(R.id.uploadPdfTab);

        //adding pager class Upload activity
        uploadAdapter = new UploadAdapter(this, uploadTabLayout.getTabCount());
        uploadPager.setAdapter(uploadAdapter);

        //switch through tabs
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(uploadTabLayout, uploadPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Upload Security");
                        break;
                    case 1:
                        tab.setText("Upload Programming");
                        break;
                    case 2:
                        tab.setText("Upload PDF");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();


    }

    //menu links
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.home) {
            Intent home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(home);
        }

        if (item.getItemId() == R.id.uploadMenu) {
            Toast.makeText(getApplicationContext(), "You are already on this page", Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.timeCalc) {
            Intent toCalc = new Intent(getApplicationContext(), Tcalc.class);
            startActivity(toCalc);
        }

        //milesCalc
        if (item.getItemId() == R.id.milesCalc) {
            Intent toMcalc = new Intent(getApplicationContext(), Mcalc.class);
            startActivity(toMcalc);
        }

        return false;
    }
}
