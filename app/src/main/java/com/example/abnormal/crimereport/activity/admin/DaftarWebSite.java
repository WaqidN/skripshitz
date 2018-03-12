package com.example.abnormal.crimereport.activity.admin;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abnormal.crimereport.R;

import java.util.ArrayList;
import java.util.List;

public class DaftarWebSite extends Fragment  {

    private TabLayout tabLayoutDF;
    public static ViewPager viewPagerDF;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_web,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Daftar Website");
        viewPagerDF = (ViewPager) getActivity().findViewById(R.id.viewpager);
        setupViewPager(viewPagerDF);

        tabLayoutDF = (TabLayout) getActivity().findViewById(R.id.tabDaftarWeb);
        tabLayoutDF.setupWithViewPager(viewPagerDF);


    }

    private void setupViewPager(ViewPager viewPagerSign) {
        ViewPagerAdapter adaptersign = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adaptersign.addFragment(new DFdisimpan(), "Disimpan");
        adaptersign.addFragment(new DFdiperiksa(), "Diperiksa");
        adaptersign.addFragment(new DFpenipu(), "Penipu");
        viewPagerSign.setAdapter(adaptersign);
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

            return mFragmentTitleList.get(position);
        }
    }
}
