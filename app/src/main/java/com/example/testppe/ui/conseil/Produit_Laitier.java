package com.example.testppe.ui.conseil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;

import java.util.Calendar;
import java.util.Date;

public class Produit_Laitier  extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lait);
        /*Les alternatives au lait

            Selon un bilan écologique mandaté par le WWF,
            la production d’un litre de lait entier émet 1,63 kg d’équivalent CO2 dans l’atmosphère.
            La consommation de boissons végétales permet de réduire d’un tiers,
            voire de moitié notre empreinte écologique. Ainsi,
            le lait de soja possède un bilan écologique de 0,7 kg de CO2,
            le lait de riz de 0,94 kg de CO2,
             le lait d’amande de 0,8 kg de CO2,
            le lait d’avoine de 0,76 kg de CO2,
            le lait d’épeautre de 0,81 kg de CO2 et
            le lait de lupin de 0,84 kg de CO2.

         */
    }
}
