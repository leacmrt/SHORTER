package com.example.testppe.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.DBHelper;
import com.example.testppe.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DBHelper mydb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        mydb=new DBHelper(HomeFragment.this.getContext());

       ArrayList tmp = mydb.getAllMatch();
        ListView lv = (ListView) root.findViewById(R.id.list);

        String List1 []= {"farine avoine","petits lus","avocats (brésil)","tomate","carotte","lait"};
        ArrayAdapter adapter1 = new ArrayAdapter<>(HomeFragment.this.getActivity(), R.layout.listview, R.id.textView, tmp);
        lv.setAdapter(adapter1);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                lv.setAdapter(adapter1);

            }
        });


        return root;
    }
}