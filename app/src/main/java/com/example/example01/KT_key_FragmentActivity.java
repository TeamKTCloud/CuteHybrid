package com.example.example01;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class KT_key_FragmentActivity extends Fragment {

    AddActivity addActivity;
    Button kt_add_apply;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        addActivity = (AddActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        addActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.kt_key_fragment, container,false);

        kt_add_apply = (Button) rootview.findViewById(R.id.kt_add_apply);
        kt_add_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addActivity.onFragmentChange(0);
            }
        });
        return rootview;
    }
}

