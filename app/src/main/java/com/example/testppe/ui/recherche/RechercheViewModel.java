package com.example.testppe.ui.recherche;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RechercheViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RechercheViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cherchez le produit alimentaire de votre choix");
    }

    public LiveData<String> getText() {
        return mText;
    }
}