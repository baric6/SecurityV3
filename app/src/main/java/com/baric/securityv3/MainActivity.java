package com.baric.securityv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager2 pager;
    TabLayout mTabLayout;
    TabItem securityTab, programmingTab, pdfTab;
    PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout2);
        securityTab = findViewById(R.id.securityTab);
        programmingTab = findViewById(R.id.ProgrammingTab);
        pdfTab = findViewById(R.id.pdfTab);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(this, mTabLayout.getTabCount());
        pager.setAdapter(adapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Security");
                        break;
                    case 1:
                        tab.setText("Programming");
                        break;
                    case 2:
                        tab.setText("PDF");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                pager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        //pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.home) {
            Toast.makeText(getApplicationContext(), "You are already on this page", Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.uploadMenu) {
            Intent toUpload = new Intent(this, UploadActivity.class);
            startActivity(toUpload);
        }


        if (item.getItemId() == R.id.timeCalc) {
            Intent toCalc = new Intent(this, Tcalc.class);
            startActivity(toCalc);
        }

        //milesCalc
        if (item.getItemId() == R.id.milesCalc) {
            Intent toMcalc = new Intent(this, Mcalc.class);
            startActivity(toMcalc);
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        
    }
}