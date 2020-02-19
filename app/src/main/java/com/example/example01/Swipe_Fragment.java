package com.example.example01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

public class Swipe_Fragment extends Fragment {

    private myPagerAdapter _mpa;
    private ViewPager vp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.activity_swipe, container,false);
        vp = (ViewPager) rootview.findViewById(R.id.vp);

        Button btn_first = (Button)rootview.findViewById(R.id.btn_first);
        Button btn_second = (Button)rootview.findViewById(R.id.btn_second);

        this._mpa = new myPagerAdapter(getChildFragmentManager());
        vp.setAdapter(this._mpa);
        vp.setOffscreenPageLimit(2);
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
            _mpa.getItem(tag);
        }
    };
    public class myPagerAdapter extends FragmentPagerAdapter
    {
        public myPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {

            switch(position)
            {
                case 0:
                    return new DashFragment();
                case 1:
                    return new Swipe_Second_Fragment();
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