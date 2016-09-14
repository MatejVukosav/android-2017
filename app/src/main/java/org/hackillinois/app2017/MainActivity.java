package org.hackillinois.app2017;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.hackillinois.app2017.Announcements.AnnouncementListFragment;
import org.hackillinois.app2017.HackerHelp.HackerHelpFragment;
import org.hackillinois.app2017.Profile.ProfileFragment;
import org.hackillinois.app2017.Schedule.ScheduleFragment;
import org.hackillinois.app2017.Settings.PrefsActivity;
import org.hackillinois.app2017.Settings.PrefsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

// Hi Arnav
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String sharedPrefsName = "AppPrefs";
    private static final int REQUEST_CODE = 12;
    private static int lastSelected;

    private FragmentManager fragmentManager;
    private MenuItem menuItem;
    private MapFragment mMapFragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkPerms();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        mMapFragment = new MapFragment();

        lastSelected = 0;
        //Set default fragment to schedule fragment
        fragmentManager.beginTransaction()
                .replace(R.id.content_holder, new ScheduleFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        menuItem = item;
        if(menuItem.getItemId() == lastSelected){
            return true;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectDrawerItem(menuItem); // your fragment transactions go here
            }
        }, 350);
        drawer.closeDrawer(GravityCompat.START);
        return menuItem.getItemId() != R.id.nav_settings;
    }

    private void selectDrawerItem(MenuItem item){
        final int selectedID = item.getItemId();
        switch (selectedID){
            case R.id.nav_schedule:
                swapFragment(new ScheduleFragment());
                setTitle("Schedule");
                lastSelected = selectedID;
                break;
            case R.id.nav_announcements:
                swapFragment(new AnnouncementListFragment());
                setTitle("Announcements");
                lastSelected = selectedID;
                break;
            //TODO: Create and display a loading fragment so map can load in the background
            case R.id.nav_map:
                swapFragment(mMapFragment);
                setTitle("Map");
                lastSelected = selectedID;
                break;
            case R.id.nav_profile:
                swapFragment(new ProfileFragment());
                setTitle("Profile");
                lastSelected = selectedID;
                break;
            case R.id.nav_hacker_help:
                swapFragment(new HackerHelpFragment());
                setTitle("Hacker Help");
                lastSelected = selectedID;
                break;
            case R.id.nav_settings:
                Intent intent = new Intent(this, PrefsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void swapFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in,
                0,
                0,
                android.R.anim.fade_out);
        transaction.replace(R.id.content_holder, fragment).commit();
    }

    private void checkPerms(){
        String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};
        for(String permission:PERMISSIONS) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // TODO: Display popup to tell user that we need location for maps
                    this.finish();
                }
                return;
            }
        }
    }
}
