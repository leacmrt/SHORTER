package com.example.testppe;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.ui.dashboard.DashboardViewModel;

public class Avis extends Fragment {

    private Button add =null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_avis, container, false);
        add = root.findViewById(R.id.button3);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Avis.this.getContext(), "Avis ajouté", Toast.LENGTH_LONG).show();
            }
        });

        return root;

    }
    }
