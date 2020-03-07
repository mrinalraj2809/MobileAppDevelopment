package com.example.loginact;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AsksandHaves extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_asks,container,false);

        View rootView = inflater.inflate(R.layout.fragment_asks, container, false);
        startActivity(new Intent(getActivity(),Main2.class));
        //return inflater.inflate(R.layout.fragment_asks,container,false);
        return rootView;
    }
}
