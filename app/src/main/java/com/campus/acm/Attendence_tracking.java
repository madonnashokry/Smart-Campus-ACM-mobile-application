package com.campus.acm;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

//import recyclerview.Notfciations;

public class Attendence_tracking extends AppCompatActivity {


    ImageButton notfcbtn ;
    ImageButton backbtn ;
    EditText search;
    Button logfle ;
   // ListView list;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notfcbtn = findViewById(R.id.notfybtn);
        backbtn = findViewById(R.id.backkk);
        // search = findViewById(R.id.serch) ;
        logfle = findViewById(R.id.logee);
        PieChart pie = findViewById(R.id.chart) ;
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(80,"Attendence") );
        PieDataSet pieDataSet = new PieDataSet(data,"Attendence") ;
       // pieDataSet.setColor);
        PieData pieData = new PieData(pieDataSet);
        pie.setData(pieData);
        pie.getDescription().setEnabled(false);
        pie.animateY(1000);
        pie.invalidate();


                /*

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Madonna", 10000));


        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
         */
        backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Attendence_tracking.this, upcoming_meetings.class));
            }
        });

        logfle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Attendence_tracking.this, Logfile.class));
            }
        });

    }
    }
       /*
        notfcbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Attendence_tracking.this, Notfciations.class));
            }
        });


    }
*/




