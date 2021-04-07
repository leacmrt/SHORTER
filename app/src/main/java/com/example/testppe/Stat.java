package com.example.testppe;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.testppe.BDD.DBHelper;
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
import java.util.List;

public class Stat extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    DBHelper mabdd;
    DBHelper_Produit prodb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<String> nom = new ArrayList<>();
        ArrayList<String> info = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_stat, container, false);
        PieChart chart = root.findViewById(R.id.chart);
        mabdd = new DBHelper(Stat.this.getContext());
        prodb = new DBHelper_Produit(Stat.this.getContext());

        nom = mabdd.getnote();

        for (int o = 0; o < nom.size(); o++) {
            String note1 = prodb.getNote(nom.get(o));
            info.add(note1);
            System.out.println(note1);
        }

        PieChart pieChart = root.findViewById(R.id.chart);

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

        for(int u =0;u<info.size();u++)
        {
        yvalues.add(new PieEntry(10f, info.get(u)));
        }

        PieDataSet dataSet = new PieDataSet(yvalues, "");
        dataSet.setSliceSpace(3f);

        ArrayList<String> xVals = new ArrayList<>();
        for(int u =0;u<info.size();u++)
        {
            xVals.add(info.get(u));
        }



        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
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

        //pieChart.setOnChartValueSelectedListener(this);
        return root;
    }
}