package com.teamnightcoders.doorstep.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teamnightcoders.doorstep.user.fragments.MyCartFragment;
import com.teamnightcoders.doorstep.user.fragments.MyHomeFragment;
import com.teamnightcoders.doorstep.user.fragments.MyOrderFragment;
import com.teamnightcoders.doorstep.user.fragments.MyProfileFragment;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        init();
        initInterface();
    }

    private void init() {
        viewPager = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.bottom_nav);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MyHomeFragment());
        viewPagerAdapter.addFragment(new MyCartFragment());
        viewPagerAdapter.addFragment(new MyOrderFragment());
        viewPagerAdapter.addFragment(new MyProfileFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initInterface() {
        viewPager.addOnPageChangeListener(pageChangeListener);
        navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigationView.setSelectedItemId(R.id.nav_Home);
                    break;
                case 1:
                    navigationView.setSelectedItemId(R.id.nav_Cart);
                    break;
                case 2:
                    navigationView.setSelectedItemId(R.id.nav_Orders);
                    break;
                case 3:
                    navigationView.setSelectedItemId(R.id.nav_Profile);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_Home:
                    viewPager.setCurrentItem(0, true);
                    return true;
                case R.id.nav_Cart:
                    viewPager.setCurrentItem(1, true);
                    return true;
                case R.id.nav_Orders:
                    viewPager.setCurrentItem(2, true);
                    return true;
                case R.id.nav_Profile:
                    viewPager.setCurrentItem(3, true);
                    return true;
            }
            return false;
        }
    };

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }


}