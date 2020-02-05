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


public class AWS_key_FragmentActivity extends Fragment {

    AddActivity addActivity;
    Button aws_add_apply;
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

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.aws_key_fragment,container,false);

        aws_add_apply = (Button) rootview.findViewById(R.id.aws_add_apply);
        aws_add_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addActivity.onFragmentChange(1);
            }
        });
        return rootview;

    }
}

