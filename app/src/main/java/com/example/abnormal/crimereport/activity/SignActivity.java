package com.example.abnormal.crimereport.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.abnormal.crimereport.R;

import java.util.ArrayList;
import java.util.List;

public class SignActivity extends AppCompatActivity {

    private TabLayout tabLayoutSign;
    public static ViewPager viewPagerSign;
    private int[] tabIconSign = {
            R.drawable.ic_one,
            R.drawable.ic_two,
            R.drawable.ic_three
    };
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);



        viewPagerSign = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPagerSign);

        tabLayoutSign = (TabLayout) findViewById(R.id.tabSign);
        tabLayoutSign.setupWithViewPager(viewPagerSign);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPagerSign) {
        ViewPagerAdapter adaptersign = new ViewPagerAdapter(getSupportFragmentManager());
        adaptersign.addFragment(new SignFragOne(), "One");
        adaptersign.addFragment(new SignFragTwo(), "Tw0");
        adaptersign.addFragment(new SignFragThree(), "Three");
        viewPagerSign.setAdapter(adaptersign);
    }

    private void setupTabIcons() {
        tabLayoutSign.getTabAt(0).setIcon(tabIconSign[0]);
        tabLayoutSign.getTabAt(1).setIcon(tabIconSign[1]);
        tabLayoutSign.getTabAt(2).setIcon(tabIconSign[2]);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }
    }
}
