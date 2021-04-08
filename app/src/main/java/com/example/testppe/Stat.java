package com.example.testppe;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.BDD.DBHelper;
import com.example.testppe.BDD.DBHelper_Composition;
import com.example.testppe.BDD.DBHelper_Emballage;
import com.example.testppe.BDD.DBHelper_Materiaux;
import com.example.testppe.BDD.DBHelper_Produit;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.example.testppe.ui.notifications.NotificationsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//classe création + affichage des statistiques utilisateurs
public class Stat extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    DBHelper mabdd;
    DBHelper_Produit prodb;
    DBHelper_Emballage emdb;
    DBHelper_Materiaux madb;
    @SuppressLint("NewApi")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> nom = new ArrayList<>();
        ArrayList<String> info = new ArrayList<>();
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> info2 = new ArrayList<>();
        ArrayList<Float> test = new ArrayList<>();

        String [] la = {"A","B","C","D","E"};
        int [] jsp;
        View root = inflater.inflate(R.layout.fragment_stat, container, false);
        PieChart chart = root.findViewById(R.id.chart);

       //initialisation des BDD
        mabdd = new DBHelper(Stat.this.getContext());
        prodb = new DBHelper_Produit(Stat.this.getContext());
        emdb = new DBHelper_Emballage(Stat.this.getContext());
        madb = new DBHelper_Materiaux(Stat.this.getContext());

        nom = mabdd.getnote(); //recupération de toutes les nom
        id = mabdd.getAllMatch2(); //recupération de tous les ID
        //des recherches faires par un utilisateur

        for (int o = 0; o < nom.size(); o++)
        {

           String note1 = prodb.getNote(nom.get(o));//pour chaque item rechercher, on trouve la note
            info.add(note1);//on la store

        }



        for (int o = 0; o < id.size(); o++)
        {
            int note1 = emdb.getIdmateriaux(id.get(o));//pareil pour les matériaux
            String mat = madb.getMateriaux(note1);
            info2.add(mat);
            //System.out.println("l"+note1);
        }

        countFrequencies(info);
        PieChart pieChart = root.findViewById(R.id.chart); //notes
        PieChart pieChart1 = root.findViewById(R.id.chart1);//recyclage

        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(25, 5, 25, 0);


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(30f);

        pieChart.setEntryLabelColor(Color.BLACK);

        List<PieEntry> yvalues = new ArrayList<>();

        Set<String> st = new HashSet<String>(info);
        for (String s : st) //entrée des données pour les notes
        {   yvalues.add(new PieEntry(Collections.frequency(info, s),s));
            System.out.println(s + ": " + Collections.frequency(info, s));
        }


        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setSliceSpace(3f);

        ArrayList<String> xVals = new ArrayList<>(); //entrée écoscore
        for (String s : st)
        {
            xVals.add(s);
        }



        PieData data = new PieData(dataSet);//la même chose pour les types d'emballages/recyclages

        data.setValueFormatter(new PercentFormatter());
        //data.getYValueSum();
        // data.setValueFormatter(new DefaultValueFormatter(0));


        pieChart.setData(data);

        pieChart.setEntryLabelTextSize(13);

        int[] colors = {Color.RED, Color.rgb(255, 128, 0), Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
        dataSet.setColors(ColorTemplate.createColors(colors));

        // dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        // dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        // dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        Description d = new Description();
        d.setTextSize(16);
        d.setPosition(65, 50);

        d.setTextAlign(Paint.Align.LEFT);
        d.setText("Vos notes rentrées");
        pieChart.setDescription(d);


        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleRadius(30f);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        pieChart.animateXY(1500, 1500);

        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(1.2f);
        dataSet.setValueLinePart2Length(0.4f);



        pieChart1.setExtraOffsets(25, 5, 25, 0);


        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setHoleColor(Color.WHITE);

        Legend l1 = pieChart1.getLegend();
        l1.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l1.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l1.setOrientation(Legend.LegendOrientation.VERTICAL);
        l1.setDrawInside(true);
        l1.setXEntrySpace(7f);
        l1.setYEntrySpace(0f);
        l1.setYOffset(30f);

        pieChart1.setEntryLabelColor(Color.BLACK);

        List<PieEntry> yvalues1 = new ArrayList<>();



        Set<String> st1 = new HashSet<String>(info2);
        for (String s : st1)
        {   yvalues1.add(new PieEntry(Collections.frequency(info2, s),s));
            System.out.println(s + ": " + Collections.frequency(info2, s));
        }


        PieDataSet dataSet1 = new PieDataSet(yvalues1, "");
        dataSet.setSliceSpace(3f);

        ArrayList<String> xVals1 = new ArrayList<>();
        for (String s : st)
        {
            xVals1.add(s);
        }




        PieData data1 = new PieData(dataSet1);

        data1.setValueFormatter(new PercentFormatter());
        // data.setValueFormatter(new DefaultValueFormatter(0));

        pieChart1.setData(data1);

        pieChart1.setEntryLabelTextSize(13);

        int[] colors1 = {Color.RED, Color.rgb(255, 128, 0), Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
        dataSet1.setColors(ColorTemplate.createColors(colors1));


        Description d1 = new Description();
        d1.setTextSize(16);
        d1.setPosition(65, 50);

        d1.setTextAlign(Paint.Align.LEFT);
        d1.setText("Vos types d'emballages");
        pieChart1.setDescription(d1);


        pieChart1.setTransparentCircleRadius(30f);
        pieChart1.setHoleRadius(30f);

        data1.setValueTextSize(13f);
        data1.setValueTextColor(Color.DKGRAY);

        pieChart1.animateXY(1500, 1500);

        dataSet1.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet1.setValueLinePart1OffsetPercentage(80.f);
        dataSet1.setValueLinePart1Length(1.2f);
        dataSet1.setValueLinePart2Length(0.4f);

        //pieChart.setOnChartValueSelectedListener(this);
        return root;
    }
    public static void countFrequencies(ArrayList<String> list)
    {

        // hash set is created and elements of
        // arraylist are insertd into it
        Set<String> st = new HashSet<String>(list);
        for (String s : st)
            System.out.println(s + ": " + Collections.frequency(list, s));
    }

}