package com.example.nisha;

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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class law_budget_frag extends Fragment {

    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private LineChart law_bud_bar;
    private ListView bud_list;
    private List<String> result1 = new ArrayList<>();
    private List<Float> result2 = new ArrayList<>();
    private List<Entry> entries = new ArrayList<>();
    private List<String> inst = new ArrayList<>();



    @Override
    @Nullable
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_law_budget, Container, false);
        law_bud_bar = view.findViewById(R.id.law_bud_bar);
        bud_list = view.findViewById(R.id.bud_list);
        instituteDB();
        return view;
    }

    private void instituteDB() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from law_names inner join law_budget on law_names.Law_ID_F=law_budget.Law_ID_B");

            while(rs.next()) {
                result1.add(rs.getString("L_Name"));
                result2.add(rs.getFloat("Law_budget"));
            }

            for (int i=0; i<result2.size(); i++){
                entries.add(new Entry(result2.get(i), i+1));
            }


            inst.add("Please Expand The Chart To See The Names Of Institutes");
            inst.add("Y-Axis Display The Average Fee Structure Of The Institutes");
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, inst);
            bud_list.setAdapter(arrayadapter);


            LineDataSet linedataset = new LineDataSet(entries, "Budget(Rupees)");

            linedataset.enableDashedLine(10f, 10f, 0f);
            linedataset.setColor(ContextCompat.getColor(requireNonNull(getContext()),R.color.darkblue));
            linedataset.setLineWidth(12f);
            linedataset.setCircleRadius(7f);
            linedataset.setDrawCircleHole(true);
            linedataset.setValueTextSize(15f);
            linedataset.setDrawCubic(true);
            linedataset.setDrawFilled(true);
            linedataset.setFillColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.orange));
            linedataset.setValueTextColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.darkpurple));
            linedataset.setCircleColor(ContextCompat.getColor(getContext(), R.color.darkgreen));

            LineData data = new LineData(result1,linedataset);
            law_bud_bar.setData(data);
            law_bud_bar.setDescription("Law Institutes");
            law_bud_bar.animateXY(2500,2500);


            law_bud_bar.invalidate();


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
