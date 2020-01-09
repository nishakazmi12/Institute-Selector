package com.example.nisha;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class arts_rank_frag extends Fragment {

    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private ListView rank_list;
    private BarChart arts_rank_bar;
    private List<String> result1 = new ArrayList<>();
    private List<Float> result2 = new ArrayList<>();
    private List<String> inst = new ArrayList<>();

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_arts_rank, Container, false);
        rank_list = view.findViewById(R.id.rank_list);
        arts_rank_bar = view.findViewById(R.id.arts_rank_bar);
        arts_rank_bar.setNoDataText("No Database Connection ");
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
            ResultSet rs = st.executeQuery("select * from arts_names inner join arts_rank on arts_names.Arts_ID_F=arts_rank.Arts_ID_R ");

            while (rs.next()) {
                result1.add(rs.getString("A_Name"));
                result2.add(rs.getFloat("Arts_Rank"));
            }

            inst.add("Please Expand The Chart To See The Names Of Institutes");
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, inst);
            rank_list.setAdapter(arrayadapter);


            ArrayList<BarEntry> entries;
            entries = new ArrayList<>();
            int i;
            for (i = 0; i < result2.size(); i++) {
                entries.add(new BarEntry(result2.get(i), i));
            }
            BarDataSet bardataset = new BarDataSet(entries, "Ratings(Stars)");
            bardataset.setColors(ColorTemplate.PASTEL_COLORS);
            BarData data = new BarData(result1, bardataset);

            Legend legend = arts_rank_bar.getLegend();
            legend.setEnabled(true);
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setForm(Legend.LegendForm.CIRCLE);

            arts_rank_bar.setData(data); // set the data and list of labels into chart
            arts_rank_bar.setDescription("Arts Institutes");  // set the description
            arts_rank_bar.animateXY(3000, 3000);
            arts_rank_bar.setTouchEnabled(true);

            arts_rank_bar.invalidate();


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
