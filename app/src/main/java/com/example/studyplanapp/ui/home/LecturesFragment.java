package com.example.studyplanapp.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studyplanapp.AddActivityLe;
import com.example.studyplanapp.CustomAdapterLE;
import com.example.studyplanapp.MyCoreDatabase;
import com.example.studyplanapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass..
 */
public class LecturesFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageView;
    TextView no_data;
    MyCoreDatabase myCoreDatabase;
    ArrayList<String> event_id, event_title, event_course,event_type,
            event_description, event_date, event_time;
    CustomAdapterLE customAdapterLE;
    public LecturesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lectures, container, false);
        recyclerView = view.findViewById(R.id.LectureRecyclerView);
        add_button = view.findViewById(R.id.addButtonLecture);
        empty_imageView = view.findViewById(R.id.empty_image);
        no_data = view.findViewById(R.id.NoData_text);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivityLe.class);
                startActivity(intent);
            }
        });
        myCoreDatabase = new MyCoreDatabase(getActivity());
        event_id = new ArrayList<>();
        event_type = new ArrayList<>();
        event_title = new ArrayList<>();
        event_course = new ArrayList<>();
        event_description = new ArrayList<>();
        event_date = new ArrayList<>();
        event_time = new ArrayList<>();

        storeDataInArrays();

        customAdapterLE = new CustomAdapterLE(getActivity(), getContext(),
                event_id, event_title, event_course, event_type, event_description, event_date, event_time);
        recyclerView.setAdapter(customAdapterLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            getFragmentManager().beginTransaction().detach(LecturesFragment.this).attach(LecturesFragment.this).commit();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    void storeDataInArrays(){
        int type = 4;
        Cursor cursor = myCoreDatabase.readAllData(type);
        if(cursor.getCount() == 0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_type.add(cursor.getString(1));
                event_title.add(cursor.getString(2));
                event_course.add(cursor.getString(3));
                event_description.add(cursor.getString(4));
                event_date.add(cursor.getString(5));
                event_time.add(cursor.getString(6));
            }
            empty_imageView.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

}