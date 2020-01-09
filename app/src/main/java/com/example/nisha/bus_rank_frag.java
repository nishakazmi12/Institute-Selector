package com.example.nisha;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class bus_rank_frag extends Fragment {


    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private ListView rank_list;
    private BarChart bus_rank_bar;
    private List<String> result1 = new ArrayList<>();
    private List<Float> result2 = new ArrayList<>();
    private List<String> inst = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bus_rank, Container, false);
        rank_list = view.findViewById(R.id.rank_list);
        bus_rank_bar = view.findViewById(R.id.bus_rank_bar);
        bus_rank_bar.setNoDataText("No Database Connection");
        instituteDB();
        return view;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void instituteDB() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url, user, password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from bus_names inner join bus_rank on bus_names.Bus_ID_F=bus_rank.Bus_ID_R ");
//            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result1.add(rs.getString("B_Name"));
                result2.add(rs.getFloat("Rank_Bus"));
            }

            inst.add("Please Expand The Chart To See The Names Of Institutes");
            inst.add("Y-Axis Represent The Ratings In Stars Of The Institutions");
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, inst);
            rank_list.setAdapter(arrayadapter);


            ArrayList<BarEntry> entries;
            entries = new ArrayList<>();
            int i;
            for (i = 0; i < result2.size(); i++) {
                entries.add(new BarEntry(result2.get(i), i));
            }
            BarDataSet bardataset = new BarDataSet(entries, "Ratings(Stars)");
            bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
            BarData data = new BarData(result1, bardataset);

            Legend legend = bus_rank_bar.getLegend();
            legend.setEnabled(true);
            legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
            legend.setForm(Legend.LegendForm.LINE);


            bus_rank_bar.setData(data); // set the data and list of labels into chart
            bus_rank_bar.setDescription("Business Institutes");  // set the description
            bus_rank_bar.animateXY(3000, 3000);
            bus_rank_bar.setTouchEnabled(true);

            bus_rank_bar.invalidate();


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

/*

    private void instituteDB() {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con;
            con = DriverManager.getConnection(url, user, password);
//            StringBuilder result = new StringBuilder();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select B_Name, Rank_Bus from bus_rank where bus_names.Bus_ID_F==bus_rank.Bus_ID_R");
//            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result1.add(rs.getString(1));
                result2.add(rs.getFloat(2));
            }

            setdata();

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

    private void setdata() {
*/
/*

        ArrayList<String> name = new ArrayList<>(result1.size());
        name.addAll(result1);
*//*

         */
/*

        ArrayList<Float> listofrank = new ArrayList<>(result2.size());
        listofrank.addAll(result2);
*//*


        ArrayList<BarEntry> rank = new ArrayList<>();

        int count;
        count = result2.size();


        for (int i = 0; i < count; i++) {

            rank.add(new BarEntry(result2.get(i),i));

        }

        BarDataSet bardataset = new BarDataSet(rank, "Cells");
        BarData data = new BarData(result1, bardataset);
//        bus_rank_bar.setNoDataText("Test");
        bus_rank_bar.setData(data);
        bus_rank_bar.setDescription("Ranking Of Business Institutes");

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        bus_rank_bar.animateY(5000);
        bus_rank_bar.clear();
        bus_rank_bar.invalidate(); // refresh

//        bus_rank_bar.setFitsSystemWindows(true); // make the x-axis fit exactly all bars


    }

*/

    }

}
