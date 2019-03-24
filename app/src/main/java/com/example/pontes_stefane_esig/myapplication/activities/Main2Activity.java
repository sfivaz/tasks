package com.example.pontes_stefane_esig.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pontes_stefane_esig.myapplication.R;
import com.example.pontes_stefane_esig.myapplication.adapters.ListAdapter;
import com.example.pontes_stefane_esig.myapplication.adapters.Listener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements Listener
{

    RecyclerView rvTop;
    RecyclerView rvBottom;
    TextView tvEmptyListTop;
    TextView tvEmptyListBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rvTop = findViewById(R.id.rvTop);
        rvBottom = findViewById(R.id.rvBottom);
        tvEmptyListTop = findViewById(R.id.tvEmptyListTop);
        tvEmptyListBottom = findViewById(R.id.tvEmptyListBottom);

        initTopRecyclerView();
        initBottomRecyclerView();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    private void initTopRecyclerView() {
        rvTop.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        List<String> topList = new ArrayList<>();
        topList.add("A");
        topList.add("B");

        ListAdapter topListAdapter = new ListAdapter(topList, this);
        rvTop.setAdapter(topListAdapter);
        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
        rvTop.setOnDragListener(topListAdapter.getDragInstance());
    }

    private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL, false));

        List<String> bottomList = new ArrayList<>();
        bottomList.add("C");
        bottomList.add("D");

        ListAdapter bottomListAdapter = new ListAdapter(bottomList, this);
        rvBottom.setAdapter(bottomListAdapter);
        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}