package com.campus.acm;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private ArrayAdapter<String> peopleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendee);

        subj = findViewById(R.id.txt2);
        people = findViewById(R.id.txt4);
        arrlist = new ArrayList<>();
        arrpeople = new ArrayList<>();
        Button addd= findViewById(R.id.addddd);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        // Initialize peopleAdapter with the arrpeople list
        peopleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrpeople);

        Intent intent = getIntent();
        String courseName = intent.getStringExtra("course_name");
        if (courseName != null) {
            subj.setText(courseName);
            fetchStudents(courseName);
        }
        addd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(Add_Attendee.this, "student added sussefully " , Toast.LENGTH_SHORT).show();
                                    }
                                });
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
                            if (jsonObject.has("first_name") && jsonObject.has("last_name")) {
                                String studentName = jsonObject.getString("first_name") + " " +
                                        (jsonObject.has("middle_name") ? jsonObject.getString("middle_name") + " " : "") +
                                        jsonObject.getString("last_name");
                                arrpeople.add(studentName);
                            } else {
                                Log.d("Add_Attendee", "Missing required name fields in: " + jsonObject.toString());
                            }
                        }
                        runOnUiThread(() -> {
                            peopleAdapter.notifyDataSetChanged();
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
        final Dialog dialog = new Dialog(Add_Attendee.this);
        dialog.setContentView(layoutResId);
        dialog.getWindow().setLayout(650, 1000);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        EditText edit = dialog.findViewById(R.id.editText);
        ListView listView = dialog.findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_Attendee.this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        if (textView == people) {
            peopleAdapter = adapter;
        }

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(adapter.getItem(position));
                dialog.dismiss();
            }
        });
    }
}
