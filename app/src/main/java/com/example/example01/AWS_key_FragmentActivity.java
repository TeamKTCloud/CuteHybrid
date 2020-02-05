package com.example.example01;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class AWS_key_FragmentActivity extends Fragment {

    // fragment_main.xml 파일과 인플레이션으로 연결해주는것을 메모리 객체화를 시켜주어야한다
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aws_key_fragment, container, false);
    }
}

