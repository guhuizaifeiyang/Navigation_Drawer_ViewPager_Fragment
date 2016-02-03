package com.tinno.test;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.tinno.test.com.tinno.test.fragment.Fragment1;
import com.tinno.test.com.tinno.test.fragment.Fragment2;
import com.tinno.test.com.tinno.test.fragment.Fragment3;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentPagerAdapter mAdapterViewPager;
    private ViewPager mViewPager;
    private PagerSlidingTabStrip mTabStrip;
    private static String[] TAB_TITLE = {"Library","Playlists","Playing Queue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Associate the ViewPager with a new instance of our adapter
        mViewPager = (ViewPager) findViewById(R.id.vpPager);
        mAdapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapterViewPager);

        // Give the PagerSlidingTabStrip the ViewPager
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        mTabStrip.setViewPager(mViewPager);


        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our navigation view
        mNavigationView = (NavigationView)findViewById(R.id.nvView);

        // Add navigation icons
        setupNavigationIcons(mNavigationView);

        // Tie the DrawerLayout and Toolbar together
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,
                R.string.drawer_close);

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(mDrawerToggle);

        // Set up drawer item
        setupDrawerContent(mNavigationView);

        // Set up view pager listener
        setupViewPagerListener();

    }

    public void setupViewPagerListener(){
        // (Optional) If you use an OnPageChangeListener with your view pager you should set it in the widget rather than on the pager directly.
        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTitle(TAB_TITLE[position]);
                mNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupNavigationIcons(NavigationView mNavigationView) {

        mNavigationView.getMenu().findItem(R.id.nav_library).setIcon(R.drawable.library_music);
        mNavigationView.getMenu().findItem(R.id.nav_playlists).setIcon(R.drawable.playlist_play);
        mNavigationView.getMenu().findItem(R.id.nav_queue).setIcon(R.drawable.music_note);
        mNavigationView.getMenu().findItem(R.id.nav_nowplaying).setIcon(R.drawable.bookmark_music);
        mNavigationView.getMenu().findItem(R.id.nav_settings).setIcon(R.drawable.settings);
        mNavigationView.getMenu().findItem(R.id.nav_help).setIcon(R.drawable.help_circle);
        mNavigationView.getMenu().findItem(R.id.nav_about).setIcon(R.drawable.information);

    }

    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_library:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_playlists:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_queue:
                mViewPager.setCurrentItem(2);
                break;
            default:
               return;
        }

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            // Create a new fragment and specify the planet to show based on
            // position
            Fragment fragment = null;

            Class fragmentClass;
            switch(position) {
                case 0:
                    fragmentClass = Fragment1.class;
                    break;
                case 1:
                    fragmentClass = Fragment2.class;
                    break;
                case 2:
                    fragmentClass = Fragment3.class;
                    break;
                default:
                    return null;
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLE[position];
        }

    }

}
