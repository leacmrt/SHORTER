package com.example.testppe.ui.notifications;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.Places.Places;
import com.example.testppe.nearbyplaces.MainActivity;
import com.example.testppe.ui.conseil.Budget;
import com.example.testppe.ui.conseil.Fruit;
import com.example.testppe.ui.conseil.Legumes;
import com.example.testppe.R;
import com.example.testppe.ui.conseil.Produit_Laitier;
import com.example.testppe.ui.conseil.Viande;

import java.util.Calendar;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    Button legume;
    Button fruit;
    Button pro_lait;
    Button viande;
    Button restaurant;
    Button budget;

    Date currentTime = Calendar.getInstance().getTime();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        //boutton
        legume = root.findViewById(R.id.button11);
        fruit = root.findViewById(R.id.button10);
        pro_lait = root.findViewById(R.id.button9);
        viande = root.findViewById(R.id.button8);
        restaurant = root.findViewById(R.id.button7);
        budget = root.findViewById(R.id.buttonbudget);


        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               //textView.setText(s);
            }
        });

        //ouverture des pages pour les conseils
        legume.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getActivity(), Legumes.class);
                startActivity(intent);
            }
        });

        budget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(NotificationsFragment.this.getActivity(), Budget.class);
                 startActivity(intent1);
            }
        });


        restaurant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getActivity(), Fruit.class);
                startActivity(intent);
            }
        });

        pro_lait.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getActivity(), Produit_Laitier.class);
                startActivity(intent);
            }
        });

        viande.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(NotificationsFragment.this.getActivity(), Viande.class);
                startActivity(intent);
            }
        });


        return root;
    }
}