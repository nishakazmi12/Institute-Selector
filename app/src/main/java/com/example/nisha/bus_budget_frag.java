package com.example.nisha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class bus_budget_frag extends Fragment {


    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private LineChart bus_bud_bar;
    private ListView bud_list;
    private List<String> result1 = new ArrayList<>();
    private List<Float> result2 = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();
    private List<String> inst = new ArrayList<>();



    @Override
    @Nullable
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_bus_budget, Container, false);
        bus_bud_bar = view.findViewById(R.id.bus_bud_bar);
        bud_list = view.findViewById(R.id.bud_list);
        instituteDB();
        return view;
    }

    @SuppressLint("ResourceType")
    private void instituteDB() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from bus_names inner join bus_budget on bus_names.Bus_ID_F=bus_budget.Bus_ID_B");

            while(rs.next()) {
                result1.add(rs.getString("B_Name"));
                result2.add(rs.getFloat("Bus_budget"));
            }

            for (int i=0; i<result2.size(); i++){
                entries.add(new Entry(result2.get(i), i+1));
            }


            inst.add("Please Expand The Chart To See The Names Of Institutes");
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, inst);
            bud_list.setAdapter(arrayadapter);


            LineDataSet linedataset = new LineDataSet(entries, "Budget(Rupees)");
            linedataset.enableDashedLine(10f, 5f, 0f);
            linedataset.setColor(ContextCompat.getColor(requireNonNull(getContext()),R.color.orange));
            linedataset.setLineWidth(12f);
            linedataset.setCircleRadius(7f);
            linedataset.setDrawCircleHole(true);
            linedataset.setValueTextSize(15f);
            linedataset.setDrawFilled(true);
            linedataset.setFillColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.colorPrimary));
            linedataset.setValueTextColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.colorAccent));
            linedataset.setCircleColor(ContextCompat.getColor(getContext(), R.color.yellow));

            LineData data = new LineData(result1,linedataset);
            bus_bud_bar.setData(data);
            bus_bud_bar.setDescription("Business Institutes");
            bus_bud_bar.animateXY(2500,2500);


            Legend legend = bus_bud_bar.getLegend();
            legend.setEnabled(true);
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setForm(Legend.LegendForm.LINE);


            bus_bud_bar.invalidate();


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
