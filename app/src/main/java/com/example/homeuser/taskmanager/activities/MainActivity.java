package com.example.homeuser.taskmanager.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.homeuser.taskmanager.R;
import com.example.homeuser.taskmanager.interfaces.Interfaces;
import com.example.homeuser.taskmanager.loaders.DataLoader;
import com.example.homeuser.taskmanager.models.Data;
import com.example.homeuser.taskmanager.models.Mechanizm;
import com.example.homeuser.taskmanager.models.MechanizmsItemsList;
import com.example.homeuser.taskmanager.models.Task;
import com.example.homeuser.taskmanager.recyclerview.RvSection;
import com.example.homeuser.taskmanager.services.TaskManagerService;
import com.example.homeuser.taskmanager.sorts.BubbleSort;
import com.example.homeuser.taskmanager.sorts.InsertionSort;
import com.example.homeuser.taskmanager.sorts.QuickSort;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TaskManagerService taskManagerService;
    private RecyclerView recyclerView;
    private Data data;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private boolean isBoundService;
    private Interfaces.ReturnSortResultInterface resultInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCallbacks();
        initViews();
        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isBoundService) {
            doBindTaskManagerService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        doUnBindTaskManagerService();
    }

    private void loadData() {
        DataLoader dataLoader = new DataLoader(getAssets(), new DataLoader.GetJsonFromAssetsCallback() {
            @Override
            public void getResult(Data result) {
                data = result;
                initRecyclerView();
            }
        });
        dataLoader.execute();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button btnSort = findViewById((R.id.btnSort));
        btnSort.setOnClickListener(this);
        Button btnReset = findViewById((R.id.btnReset));
        btnReset.setOnClickListener(this);
    }

    private void doBindTaskManagerService() {
        bindService(new Intent(MainActivity.this, TaskManagerService.class),
                serviceConnection,
                Context.BIND_AUTO_CREATE);
    }

    private void doUnBindTaskManagerService() {
        if (isBoundService) {
            unbindService(serviceConnection);
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            taskManagerService = ((TaskManagerService.LocalBinder) service).getServiceInstance();
            //taskManagerService.registerClient(MainActivity.this);
            isBoundService = true;
            //log("playerServiceConnection   " + playerService);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            taskManagerService = null;
            isBoundService = false;

        }
    };

    private void initRecyclerView() {
        sectionAdapter = new SectionedRecyclerViewAdapter();
        for (MechanizmsItemsList item : data.getMechanizms().getMechanizmsItemsLists()) {
            RvSection section = new RvSection(item.getName(), item.getMechanizmList());
            sectionAdapter.addSection(section);
        }
        recyclerView.setAdapter(sectionAdapter);
    }

    private void initCallbacks() {
        resultInterface = new Interfaces.ReturnSortResultInterface() {
            @Override
            public void returnSortResult(List<? extends Mechanizm> list, int taskNo) {
                data.getMechanizms().getMechanizmsItemsLists().get(taskNo).setMechanizmList((List<Mechanizm>) list);
                sectionAdapter.notifyDataSetChanged();
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                loadData();
                break;
            case R.id.btnSort:
                if (data != null
                        && data.getMechanizms() != null
                        && data.getMechanizms().getMechanizmsItemsLists() != null) {
                    List<Task> taskList = new ArrayList<>();
                    for (MechanizmsItemsList item : data.getMechanizms().getMechanizmsItemsLists()) {
                        //Тут можно менять типы сортировок
                        //taskList.add(new Task(new BubbleSort(), item.getMechanizmList()));
                        //taskList.add(new Task(new QuickSort(), item.getMechanizmList()));
                        taskList.add(new Task(new InsertionSort(), item.getMechanizmList()));
                    }
                    taskManagerService.setTaskList(taskList, resultInterface);
                }
                break;
        }
    }
}
