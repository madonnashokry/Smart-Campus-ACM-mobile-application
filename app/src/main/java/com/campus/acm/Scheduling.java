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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Scheduling extends AppCompatActivity {
   TextView subj;
    ArrayList<String> arrlist = new ArrayList<>();
    Dialog digg;
 Button adddd;
    private EditText editTextName;
    private EditText editTextType;
    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextRoomID;
    private ImageButton calendarButton;
    private String organizerId;

    String accessToken;
    private OkHttpClient client;
    private static final String EventsURL = "http://90.84.199.65:8000/event/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    EditText editTextOrganizerID;


EditText CourseIDd;
    private List<String> courseNames = new ArrayList<>();
    private Map<String, String> courseNameToIdMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scheduling);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
         accessToken = sharedPreferences.getString("access_token", "");
        editTextName = findViewById(R.id.editTextText4);
        editTextType = findViewById(R.id.editTextText);
        editTextDate = findViewById(R.id.editTextText2);
        editTextStartTime = findViewById(R.id.editTextText5);
        editTextEndTime = findViewById(R.id.editTextText6);
        editTextRoomID = findViewById(R.id.editTextText3);
        calendarButton = findViewById(R.id.sign_up_calender_btn);
        editTextOrganizerID = findViewById(R.id.orgnizer);
        CourseIDd = findViewById(R.id.courseID);
        Button addButton = findViewById(R.id.buttonnnnn);

        client = new OkHttpClient();

        ///spinner
       subj= findViewById(R.id.Text7);
       // arrlist = new ArrayList<>();
       //arrlist.add("algo");
        //arrlist.add("os");
        //arrlist.add("linux");
        //arrlist.add("nlp");
        //arrlist.add("embedded");
       /* subj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            digg=  new Dialog(Scheduling.this);
            digg.setContentView(R.layout.dialog_search_spinner);
            digg.getWindow().setLayout(650,1000);
            digg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            digg.show();
                EditText edit = digg.findViewById(R.id.editText);
                ListView listt= digg.findViewById(R.id.listview);
                ArrayAdapter<String> adapter = new  ArrayAdapter<>(Scheduling.this, android.R.layout.simple_list_item_1,arrlist);
                listt.setAdapter(adapter);

                edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                      adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

             listt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     subj.setText(adapter.getItem(position));
                     digg.dismiss();
                 }
             });
              

            }
        });

        */
        fetchUserDetails();
        fetchCourses();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_event();

            }
        });
    }


    private void fetchCourses() {
        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/courses/")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Scheduling.this, "Failed to fetch courses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONArray jsonArray = new JSONArray(responseBody);
                        arrlist.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String courseName = jsonObject.getString("name");
                            String courseId = jsonObject.getString("id");
                            courseNames.add(courseName);
                            courseNameToIdMap.put(courseName, courseId);
                        }
                        runOnUiThread(() -> {
                            setupDialog();
                            Toast.makeText(Scheduling.this, "Courses fetched successfully!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(Scheduling.this, "Failed to parse courses: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Scheduling.this, "Failed to fetch courses: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }




    private void setupDialog() {
        subj.setOnClickListener(v -> {
            digg = new Dialog(Scheduling.this);
            digg.setContentView(R.layout.dialog_search_spinner);
            digg.getWindow().setLayout(650, 1000);
            digg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            digg.show();

            EditText edit = digg.findViewById(R.id.editText);
            ListView listt = digg.findViewById(R.id.listview);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Scheduling.this, android.R.layout.simple_list_item_1, courseNames);
            listt.setAdapter(adapter);

            edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

            listt.setOnItemClickListener((parent, view, position, id) -> {
                String selectedCourseName = adapter.getItem(position);
                subj.setText(selectedCourseName);
                String selectedCourseId = courseNameToIdMap.get(selectedCourseName);
                CourseIDd.setText(selectedCourseId);
                digg.dismiss();
            });
        });
    }




    private void fetchUserDetails() {

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/info")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Scheduling.this, "Failed to fetch user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        // Update the EditText with the organizer ID
                        organizerId = jsonObject.getString("id");  // Store organizer ID in member variable
                        runOnUiThread(() -> {
                            editTextOrganizerID.setText(organizerId);
                            Toast.makeText(Scheduling.this, "ID is fetched!", Toast.LENGTH_SHORT).show();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Toast.makeText(Scheduling.this, "Failed to parse user details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Scheduling.this, "Failed to fetch user details: " + response.message(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }


    public void add_event(){
        String name = editTextName.getText().toString();
        String type = editTextType.getText().toString();
        String date = editTextDate.getText().toString();
        String startTime = editTextStartTime.getText().toString();
        String endTime = editTextEndTime.getText().toString();
        String courseId = editTextRoomID.getText().toString();
        String organizerId = this.organizerId;
        String roomId = editTextRoomID.getText().toString();


        Log.d("AddEvent", "Name: " + name);
        Log.d("AddEvent", "Type: " + type);
        Log.d("AddEvent", "Date: " + date);
        Log.d("AddEvent", "Start Time: " + startTime);
        Log.d("AddEvent", "End Time: " + endTime);
        Log.d("AddEvent", "Course ID: " + courseId);
        Log.d("AddEvent", "Room ID: " + roomId);


        if (name.isEmpty() || type.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || courseId.isEmpty() || roomId.isEmpty()) {
            Toast.makeText(Scheduling.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

            return;
        }



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("type", type);
            jsonObject.put("date", date);
            jsonObject.put("start_time", startTime);
            jsonObject.put("end_time", endTime);
            jsonObject.put("course_id", courseId);
            jsonObject.put("organizer_id", organizerId);
            jsonObject.put("room_id", roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url(EventsURL)
                .post(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Scheduling.this, "Request Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("AddEvent", "Request Failed: " + e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        try {
                            // Parse the response
                            String responseBody = response.body().string();
                            Log.d("AddEvent", "Response: " + responseBody);


                            JSONObject responseJson = new JSONObject(responseBody);
                            //String meetingId = responseJson.getString("id");
                            //Log.d("AddEvent", "Meeting ID: " + meetingId);


                            Intent intent = new Intent(Scheduling.this, Add_Attendee.class);
                            intent.putExtra("course_name", subj.getText().toString());
                            Toast.makeText(Scheduling.this, "Event Added Successfully! " , Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e("AddEvent", "Failed to parse response: " + e.getMessage());
                            Toast.makeText(Scheduling.this, "Failed to parse response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("AddEvent", "Request Failed: " + response.message());
                        Toast.makeText(Scheduling.this, "Request Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}



