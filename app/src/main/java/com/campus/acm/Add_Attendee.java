package com.campus.acm;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Add_Attendee extends AppCompatActivity {
    TextView subj;
    TextView people;
    ArrayList<String> arrlist;
    ArrayList<String> arrpeople;
    private OkHttpClient client = new OkHttpClient();
    private String accessToken;
    private static final String BASE_URL = "http://90.84.199.65:8000/course/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendee);

        subj = findViewById(R.id.txt2);
        people = findViewById(R.id.txt4);
        arrlist = new ArrayList<>();
        arrpeople = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        Intent intent = getIntent();
        String courseName = intent.getStringExtra("course_name");
        if (courseName != null) {
            subj.setText(courseName);
            fetchStudents(courseName);
        }



        subj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchSpinner(subj, arrlist, R.layout.dialog_search_spinner);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchSpinner(people, arrpeople, R.layout.dialog_search_spinner2);
            }
        });
    }
/*
    private void fetchCourses() {
        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(Add_Attendee.this, "Failed to fetch courses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject courseObject = jsonArray.getJSONObject(i);
                            String courseName = courseObject.getString("name");
                            arrlist.add(courseName);
                        }
                        runOnUiThread(() -> {
                            Toast.makeText(Add_Attendee.this, "Courses fetched successfully!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(Add_Attendee.this, "Failed to parse courses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Add_Attendee.this, "Failed to fetch courses: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
*/
    private void fetchStudents(String courseName) {
        String url = BASE_URL + courseName + "/students";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Add_Attendee.this, "Failed to fetch students: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        arrpeople.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String studentName = jsonObject.getString("name");
                            arrpeople.add(studentName);
                        }
                        runOnUiThread(() -> {
                            Toast.makeText(Add_Attendee.this, "Students fetched successfully!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(Add_Attendee.this, "Failed to parse students: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Add_Attendee.this, "Failed to fetch students: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void showSearchSpinner(final TextView textView, final ArrayList<String> dataList, int layoutResId) {
        final Dialog digg = new Dialog(Add_Attendee.this);
        digg.setContentView(layoutResId);
        digg.getWindow().setLayout(650, 1000);
        digg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        digg.show();

        EditText edit = digg.findViewById(R.id.editText);
        ListView listt = digg.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_Attendee.this, android.R.layout.simple_list_item_1, dataList);
        listt.setAdapter(adapter);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(adapter.getItem(position));
                digg.dismiss();
            }
        });
    }
}
