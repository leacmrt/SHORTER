package com.example.testppe.ui.recherche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.DBHelper;
import com.example.testppe.R;
import com.example.testppe.ui.notifications.NotificationsViewModel;

public class Recherche extends Fragment {

    private RechercheViewModel rechercheview;
    private DBHelper mydb ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rechercheview =
                new ViewModelProvider(this).get(RechercheViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recherche, container, false);
        final TextView textView = root.findViewById(R.id.text_recherche);

        SearchView entree =(SearchView) root.findViewById(R.id.searchView);
        entree.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
                                              public boolean onQueryTextChange (String newText){
                                              // your text view here
                                              // textView.setText(newText);
                                              return true;
                                          }

                                              @Override
                                              public boolean onQueryTextSubmit (String query){
                                              //textView.setText(query);
                                              CharSequence te = entree.getQuery();
                                              System.out.println(te);
                                              mydb.insertrecherche(te.toString());
                                              return true;
                                          }



                                      });
        mydb = new DBHelper(Recherche.this.getContext());
        rechercheview.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}