package se.ESNBTH.esnbth.Activities;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.facebook.AppEventsLogger;

import java.util.ArrayList;
import java.util.Calendar;

import se.ESNBTH.esnbth.Fragments.Fragment_AboutESN;
import se.ESNBTH.esnbth.Fragments.Fragment_DetailTimetable;
import se.ESNBTH.esnbth.Fragments.Fragment_Home;
import se.ESNBTH.esnbth.Fragments.Fragment_Karlskrona;
import se.ESNBTH.esnbth.Fragments.Fragment_SingleEvent;
import se.ESNBTH.esnbth.Fragments.Fragment_Timetables;
import se.ESNBTH.esnbth.Fragments.Fragment_news;
import se.ESNBTH.esnbth.NavigationDrawer.NavDrawerItem;
import se.ESNBTH.esnbth.NavigationDrawer.NavDrawerListAdapter;
import se.ESNBTH.esnbth.R;
import se.ESNBTH.esnbth.RequestHelper.AppConst;
import se.ESNBTH.esnbth.RequestHelper.UpdateService;


public class MainLayActivity extends ActionBarActivity {

    //TODO: ADD FACEBOOK LOGIN BUTTON TO NAVIGATION DRAWER. IT WILL BE FOR LOG OUT.


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    public Fragment fragment;
    public static int previousFragment = 0;

    SharedPreferences preferences;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        /*
        * Creates a new Intent to start the RSSPullService
        * IntentService. Passes a URI in the
        * Intent's "data" field.
        */



        preferences = getSharedPreferences(AppConst.PREFERENCE_KEY, MODE_PRIVATE);



        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, new Intent(getApplicationContext(), UpdateService.class), 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,AlarmManager.INTERVAL_HOUR,AlarmManager.INTERVAL_HALF_DAY,pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),30000, pendingIntent);

        if(preferences.getBoolean(AppConst.FIRST_KEY,false)) {
            startService(new Intent(getApplicationContext(), UpdateService.class));

           SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(AppConst.FIRST_KEY,true);
            editor.commit();
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        for(int h = 0; h < 6; h++) {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[h], navMenuIcons.getResourceId(h, -1)));
        }

        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.actionbarhome) {
            fragment = new Fragment_Home();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            previousFragment = 0;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
	 */

    // NOT SURE IF WE NEED THIS OR NOT

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.about).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        fragment = null;
        switch (position) {
            case 0:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_Home();
                break;
            case 1:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_news();
                break;

            case 2:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_SingleEvent();
                break;

            case 3:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_Karlskrona();
                break;

            case 4:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_Timetables();
                break;

            case 5:
                //UPDATE LIST VIEW
                Fragment_Timetables.selection = 0;
                fragment = new Fragment_AboutESN();
                break;


            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();


            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        if(previousFragment == 0){
            mDrawerList.setItemChecked(3, true);
            mDrawerList.setSelection(3);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().popBackStack();
        }
        else if(previousFragment == 1){
            mDrawerList.setItemChecked(4, true);
            mDrawerList.setSelection(4);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().popBackStack();
        }

        if (mDrawerLayout.isDrawerOpen(mDrawerList) ||
                mDrawerLayout.isDrawerVisible(mDrawerList)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {

        if(previousFragment == 0){
            fragment = new Fragment_Home();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            mDrawerList.setItemChecked(0, true);
            mDrawerList.setSelection(0);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().popBackStack();
        }

        else if(previousFragment == 1){


            fragment = new Fragment_Karlskrona();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            previousFragment = 0;

            mDrawerList.setItemChecked(3, true);
            mDrawerList.setSelection(3);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().popBackStack();
        }

        else if(previousFragment == 2){
            //UPDATE LIST VIEW
            Fragment_Timetables.selection = Fragment_DetailTimetable.listPos;

            fragment = new Fragment_Timetables();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            previousFragment = 1;

            mDrawerList.setItemChecked(4, true);
            mDrawerList.setSelection(4);
            mDrawerLayout.closeDrawer(mDrawerList);
            getFragmentManager().popBackStack();
        }
    }



}
