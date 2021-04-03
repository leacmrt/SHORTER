package com.example.testppe.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.BDD.DBHelper;
import com.example.testppe.R;
import com.example.testppe.SelectItem;
import com.example.testppe.Settings;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DBHelper mydb;
    ArrayList<String> tmp=new ArrayList<String>();
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        mydb=new DBHelper(HomeFragment.this.getContext());
        tmp = mydb.getAllMatch();
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String la =tmp.get(position);
                System.out.println("ps"+position);
                String nom = mydb.getNom(position+1);
                System.out.println("nopm"+nom);
                Toast.makeText(HomeFragment.this.getActivity(),"test",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(HomeFragment.this.getActivity(), SelectItem.class);
                intent1.putExtra("EXTRA_SESSION_ID", la);
                intent1.putExtra("numero", nom);
                startActivity(intent1);

            }
        });


        return root;
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            HomeFragment.this.getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            return true;
        }

        return false;
    }
}