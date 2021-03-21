package com.example.testppe.ui.recherche;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testppe.BDD.DBHelper;
import com.example.testppe.ExampleAdapter;
import com.example.testppe.MainActivity;
import com.example.testppe.R;
import com.example.testppe.SQL_Produit;
import com.example.testppe.SelectItem;
import com.example.testppe.ui.login.Register;
import com.example.testppe.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class Recherche extends Fragment {

    private RechercheViewModel rechercheview;
    private DBHelper mydb ;
    private List<String> listal;
    private SQL_Produit BDD;
    private RecyclerView recyclerView;
    private ExampleAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rechercheview =
                new ViewModelProvider(this).get(RechercheViewModel.class);
        View root = inflater.inflate(R.layout.fragment_recherche, container, false);
        //final TextView textView = root.findViewById(R.id.text_recherche);
        listal= new ArrayList<String>();
        BDD = new SQL_Produit();

        recyclerView = root.findViewById(R.id.recycler_view);

        setHasOptionsMenu(true);
        Thread background = new Thread(new Runnable() {

            public void run() {
                listal.add("Carotte");
                listal.add("Eau");
                listal.add("Gateau");
                Recherche.this.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        setUpRecyclerView();}});

            }});
        background.start();

        for(int i=0;i<listal.size();i++)
        {System.out.println("l"+listal.get(i));}

       /* SearchView entree =(SearchView) root.findViewById(R.id.searchView);
        entree.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


        @Override
                                              public boolean onQueryTextChange (String newText){

                                            listal.addAll(listal);

                                            final List<String> filteredModelList = filter(listal, newText);

                                            adapter.setFilter(filteredModelList);
                                            return true;
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
        });*/
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if (searchView != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Recherche.this.getContext(), "click",Toast.LENGTH_LONG).show();

                }
            });
        }

        SearchView finalSearchView = searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.getFilter().filter(null);
                finalSearchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                Toast.makeText(Recherche.this.getContext(), newText, Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }

    private void setUpRecyclerView() {

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Recherche.this.getContext());
        adapter = new ExampleAdapter(listal,Recherche.this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }





}