package com.example.testppe.ui.conseil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;

import java.util.Calendar;
import java.util.Date;

public class Viande  extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viande);

        //bouton retour
        back =findViewById(R.id.buttonback);
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
