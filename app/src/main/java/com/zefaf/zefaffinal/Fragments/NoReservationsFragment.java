package com.zefaf.zefaffinal.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zefaf.zefaffinal.ActivityMap;
import com.zefaf.zefaffinal.R;

import androidx.fragment.app.Fragment;

public class NoReservationsFragment extends Fragment {

    private Button button3;

    public NoReservationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_no_reservations, container, false);
        button3 = v.findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityMap.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
