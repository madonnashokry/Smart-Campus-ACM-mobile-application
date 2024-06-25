package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Attendence_tracking extends AppCompatActivity {

    ImageButton notfcbtn;
    ImageButton backbtn;
    EditText search;
    Button logfle;
    private Spinner spinner;
    String accessToken;

    List<String> courseNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_tracking);

        backbtn = findViewById(R.id.backkk);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        spinner = findViewById(R.id.dropdown);
        fetchStudentCourses();
    }

    private void fetchStudentCourses() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/student/courses")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(Attendence_tracking.this, "Failed to fetch courses", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject course = jsonArray.getJSONObject(i);
                            courseNames.add(course.getString("course_name"));
                        }

                        runOnUiThread(() -> {
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(Attendence_tracking.this,
                                    android.R.layout.simple_spinner_item, courseNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);

                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String selectedCourse = courseNames.get(position);
                                    fetchCourseEvents(selectedCourse);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // Do nothing
                                }
                            });
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(Attendence_tracking.this, "Failed to parse courses", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(Attendence_tracking.this, "Failed to fetch courses", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void fetchCourseEvents(String courseName) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://90.84.199.65:8000/student/" + courseName + "/events";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(Attendence_tracking.this, "Failed to fetch course events", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        List<JSONObject> courseEvents = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            courseEvents.add(jsonArray.getJSONObject(i));
                        }

                        // Log course events for debugging
                        for (JSONObject event : courseEvents) {
                            System.out.println("Course Event: " + event.toString());
                        }

                        fetchStudentAttendanceRecords(courseEvents);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(Attendence_tracking.this, "Failed to parse course events", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(Attendence_tracking.this, "Failed to fetch course events", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void fetchStudentAttendanceRecords(List<JSONObject> courseEvents) {
        OkHttpClient client = new OkHttpClient();

        String url = "http://90.84.199.65:8000/user/events";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(Attendence_tracking.this, "Failed to fetch attendance records", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        List<JSONObject> attendanceRecords = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            attendanceRecords.add(jsonArray.getJSONObject(i));
                        }

                        // Log attendance records for debugging
                        for (JSONObject record : attendanceRecords) {
                            System.out.println("Attendance Record: " + record.toString());
                        }

                        calculateAttendancePercentage(courseEvents, attendanceRecords);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(Attendence_tracking.this, "Failed to parse attendance records", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(Attendence_tracking.this, "Failed to fetch attendance records", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void calculateAttendancePercentage(List<JSONObject> courseEvents, List<JSONObject> attendanceRecords) {
        int totalEvents = courseEvents.size();
        int attendedEvents = 0;

        for (JSONObject event : courseEvents) {
            try {
                int eventId = event.getInt("event_id");
                for (JSONObject record : attendanceRecords) {
                    if (record.getInt("event_id") == eventId && record.getInt("attended") == 1) {
                        attendedEvents++;
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        float attendancePercentage = totalEvents > 0 ? ((float) attendedEvents / totalEvents) * 100 : 0;

        runOnUiThread(() -> updatePieChart(attendancePercentage));
    }

    private void updatePieChart(float attendancePercentage) {
        PieChart pie = findViewById(R.id.chart);
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(attendancePercentage, "Attendance"));
        data.add(new PieEntry(100 - attendancePercentage, "Absence")); // Add absence percentage
        PieDataSet pieDataSet = new PieDataSet(data, "");
        pieDataSet.setColors(new int[]{getResources().getColor(R.color.green), getResources().getColor(R.color.grey)}); // Set colors for attendance and absence
        PieData pieData = new PieData(pieDataSet);
        pie.setData(pieData);
        pie.setCenterText("Attendance\n" + String.format("%.2f", attendancePercentage) + "%"); // Set the text as Attendance with percentage
        pie.getDescription().setEnabled(false);
        pie.animateY(1000);
        pie.invalidate();
    }
}
