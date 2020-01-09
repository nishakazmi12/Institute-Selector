package com.example.nisha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class all_freg extends Fragment {

    @Override
    @Nullable
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_mainpage, Container, false);
    }
}
