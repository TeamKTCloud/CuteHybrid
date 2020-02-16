package com.example.example01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class Swipe_Fragment extends Fragment {

    private ViewPager vp;
    private DashFragment df;
    private Swipe_Second_Fragment ssf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.activity_swipe, container,false);

        vp = (ViewPager)rootview.findViewById(R.id.vp);

        Button btn_first = (Button)rootview.findViewById(R.id.btn_first);
        Button btn_second = (Button)rootview.findViewById(R.id.btn_second);

        df = new DashFragment();
        ssf = new Swipe_Second_Fragment();

        vp.setAdapter(new Swipe_Fragment.pagerAdapter(getFragmentManager()));
        vp.setCurrentItem(0);

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);

        return rootview;
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            vp.setCurrentItem(tag);
        }
    };
    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {

            switch(position)
            {
                case 0:
                    return df;
                case 1:
                    return ssf;
                default:
                    return null;
            }
        }

        @Override
        public int getCount()
        {
            return 2;
        }
    }
}
