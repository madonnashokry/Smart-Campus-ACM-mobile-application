package com.campus.acm;

import android.app.Dialog;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Add_Attendee extends AppCompatActivity {
    TextView subj;
    TextView people;
    ArrayList<String> arrlist;
    ArrayList<String> arrpeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendee);

        subj = findViewById(R.id.txt2);
        arrlist = new ArrayList<>();
        arrlist.add("algo");
        arrlist.add("os");
        arrlist.add("linux");
        arrlist.add("nlp");
        arrlist.add("embedded");
        arrlist.add("DS");
        arrlist.add("vision");
        arrlist.add("animation");

        people = findViewById(R.id.txt4);
        arrpeople = new ArrayList<>();
        arrpeople.add("Madonna");
        arrpeople.add("Rewan");
        arrpeople.add("bassant");
        arrpeople.add("yomna");
        arrpeople.add("mariam");
        arrpeople.add("sally");

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
                textView.setText(dataList.get(position));
                digg.dismiss();
            }
        });
    }
}
