package com.example.example01;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    Dash_Navi_Activity dashActivity;

    private Button buttonLogout;
    private Button main_add;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        dashActivity = (Dash_Navi_Activity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        dashActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container,false);

        buttonLogout = (Button) rootview.findViewById(R.id.buttonLogout);
        main_add = (Button)rootview.findViewById(R.id.main_add);

        buttonLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dashActivity.onFragmentChange(0);
            }
        });

        main_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dashActivity.onFragmentChange(1);
            }
        });
        return rootview;
    }
}
