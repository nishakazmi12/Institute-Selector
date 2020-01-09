package com.example.nisha;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class bus_name_frag extends Fragment {


    private static final String url = "jdbc:mysql://192.168.0.106:3307/institute";
    private static final String user = "jk";
    private static final String password = "jk";


    private ListView text_bus;


    @Override
    @Nullable
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup Container, @Nullable Bundle savedInstanceState) {

        LinearLayout lin = (LinearLayout) inflater.inflate(R.layout.activity_bus_name, Container, false);
        text_bus = lin.findViewById(R.id.text_bus);
        instituteDB();
        return lin;
    }

    private void instituteDB() {

        List<String> result = new ArrayList<>();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con;
            con = DriverManager.getConnection(url, user, password);
//            StringBuilder result = new StringBuilder();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from bus_names");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                result.add(rs.getString(2));

            }

            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, result);
            text_bus.setAdapter(arrayadapter);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            result.add(e.toString());
            final ArrayAdapter<String> arrayadapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_list_item_1, result);
            text_bus.setAdapter(arrayadapter);
        }
    }

}
