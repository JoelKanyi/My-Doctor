package com.example.mydoctor.onboarding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigationview.R;


public class ScreenFive extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_screenfive, container, false);

        Button start5 = view.findViewById(R.id.start5);
        ViewPager2 viewPager2 = getActivity().findViewById(R.id.myViewPager);

        start5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(5);
            }
        });
        return view;
    }


}