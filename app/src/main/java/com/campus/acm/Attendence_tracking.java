package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Attendence_tracking extends AppCompatActivity {

    //private EditText courseNameInput;
    //private Button fetchAttendanceButton;
    private PieChart pieChart;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_tracking);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        //   courseNameInput = findViewById(R.id.course_name_input);
        //  fetchAttendanceButton = findViewById(R.id.fetch_attendance_button);
        pieChart = findViewById(R.id.chart);
        fetchAttendanceData();
    }

    private void fetchAttendanceData() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/events")
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    runOnUiThread(() -> parseAttendanceData(responseData));
                }
            }
        });
    }

    private void parseAttendanceData(String responseData) {
        try {
            JSONArray eventsArray = new JSONArray(responseData);
            int totalEvents = eventsArray.length();
            int attendedEvents = 0;

            for (int i = 0; i < totalEvents; i++) {
                JSONObject event = eventsArray.getJSONObject(i);
                if (event.getInt("attended") == 1) {
                    attendedEvents++;
                }
            }

            float attendancePercentage = (float) attendedEvents / totalEvents * 100;
            updatePieChart(attendancePercentage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePieChart(float attendancePercentage) {
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(attendancePercentage, "Attendance"));
        data.add(new PieEntry(100 - attendancePercentage, "Absence"));

        PieDataSet pieDataSet = new PieDataSet(data, "");
        pieDataSet.setColors(getResources().getColor(R.color.green), getResources().getColor(R.color.grey));

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setCenterText("Attendance\n" + String.format("%.2f", attendancePercentage) + "%");
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate(); }

}
