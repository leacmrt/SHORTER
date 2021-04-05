package com.example.testppe.ui.conseil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testppe.R;

import java.util.Calendar;
import java.util.Date;

public class Viande  extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viande);
    }
}
