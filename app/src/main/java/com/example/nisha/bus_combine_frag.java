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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class bus_combine_frag extends Fragment {

    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private List<String> result1 = new ArrayList<>();
    private List<Float> result2 = new ArrayList<>();
    private List<Float> result3 = new ArrayList<>();
    private List<Entry> entriesline1 = new ArrayList<>();
    private List<Entry> entriesline2 = new ArrayList<>();
    private List<String> inst = new ArrayList<>();
    private ArrayList<ILineDataSet> Lineset = new ArrayList<>();
    private ListView com_list;
    private LineChart bus_combine;


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bus_combine, Container, false);
        bus_combine = view.findViewById(R.id.bus_combine);
        com_list = view.findViewById(R.id.com_list);
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
            ResultSet rs = st.executeQuery("select * from bus_names inner join bus_rank inner join bus_budget" +
                    " on bus_names.Bus_ID_F=bus_rank.Bus_ID_R and bus_names.Bus_ID_F=bus_budget.Bus_ID_B ");

            while (rs.next()) {
                result1.add(rs.getString("B_Name"));
                result2.add(rs.getFloat("Rank_Bus"));
                result3.add(rs.getFloat("Bus_budget"));
            }

            for (int i = 0; i < result2.size(); i++) {
                entriesline1.add(new BarEntry(result2.get(i), i));
            }
            for (int i = 0; i < result3.size(); i++) {
                entriesline2.add(new Entry(result3.get(i), i  ));
            }


            inst.add("Please Expand The Chart To See The Names Of Institutes");
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, inst);
            com_list.setAdapter(arrayadapter);



            LineDataSet linedataset1 = new LineDataSet(entriesline1, "Ranking(Stars)");
            linedataset1.setColors(ColorTemplate.JOYFUL_COLORS);
            linedataset1.setLineWidth(7f);
            linedataset1.setValueTextSize(15f);
            linedataset1.enableDashedLine(7f, 5f, 0f);
            linedataset1.setValueTextColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.colorAccent));

            LineDataSet linedataset2 = new LineDataSet(entriesline2, "Budget(Rupees)");
            linedataset2.setColors(ColorTemplate.COLORFUL_COLORS);
            linedataset2.setLineWidth(7f);
            linedataset2.setValueTextSize(15f);
            linedataset2.setCircleRadius(5f);
            linedataset2.setCircleColor(ContextCompat.getColor(getContext(), R.color.newblue));
            linedataset2.setValueTextColor(ContextCompat.getColor(requireNonNull(getContext()), R.color.colorAccent));

            Lineset.add(linedataset1);
            Lineset.add(linedataset2);

            LineData data = new LineData(result1, Lineset);
            bus_combine.setData(data);
            bus_combine.animateXY(3000, 3000);
            bus_combine.setDescription("Graphical Analysis");
            bus_combine.invalidate();


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