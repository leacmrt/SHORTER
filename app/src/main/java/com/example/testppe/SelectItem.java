package com.example.testppe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.testppe.R;
import com.example.testppe.SQL_Produit;

import java.util.ArrayList;

public class SelectItem extends AppCompatActivity {
    ArrayList<String> listtmp;
    ArrayList<Integer> listid;
    int position,id;

    private SQL_Produit BDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_item);}

}
