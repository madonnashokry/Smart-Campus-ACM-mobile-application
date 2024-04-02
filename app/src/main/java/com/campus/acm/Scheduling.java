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

public class Scheduling extends AppCompatActivity {
   TextView subj;
   ArrayList<String> arrlist ;
    Dialog digg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scheduling);


       subj= findViewById(R.id.Text7);
       arrlist = new ArrayList<>();
       arrlist.add("algo");
        arrlist.add("os");
        arrlist.add("linux");
        arrlist.add("nlp");
        arrlist.add("embedded");
        subj.setOnClickListener(new View.OnClickListener() {
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
    }
}