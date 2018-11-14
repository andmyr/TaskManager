package com.example.homeuser.taskmanager.services;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.homeuser.taskmanager.interfaces.Interfaces;
import com.example.homeuser.taskmanager.models.Mechanizm;
import com.example.homeuser.taskmanager.models.Task;

import java.util.ArrayList;
import java.util.List;

import static com.example.homeuser.taskmanager.constants.Constants.LOG_TAG;

public class TaskManagerService extends Service {
    private final IBinder binder = new LocalBinder();
    private final List<Task> taskList = new ArrayList<>();
    private int nextTaskNo = 0;
    private Interfaces.EndFirstCallbackInterface firstEndTaskCallbackInterface;
    private Interfaces.EndSecondCallbackInterface secondEndTaskCallbackInterface;
    private Interfaces.ReturnSortResultInterface resultInterface;
    private boolean firstTaskDone = false;
    private boolean secondTaskDone = false;

    public TaskManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public TaskManagerService getServiceInstance() {
            return TaskManagerService.this;
        }
    }

    public void setTaskList(List<Task> taskList, Interfaces.ReturnSortResultInterface resultInterface) {
        this.taskList.addAll(taskList);
        this.resultInterface = resultInterface;
        startWork();
    }

    private void startWork() {
        if (nextTaskNo == 0) {
            firstEndTaskCallbackInterface = new Interfaces.EndFirstCallbackInterface() {
                @Override
                public void taskDone(List<? extends Mechanizm> list) {
                    Log.i(LOG_TAG, "First task done");
                    firstTaskDone = true;
                    resultInterface.returnSortResult(list, nextTaskNo);
                    nextTask();
                }
            };

            secondEndTaskCallbackInterface = new Interfaces.EndSecondCallbackInterface() {
                @Override
                public void taskDone(List<? extends Mechanizm> list) {
                    Log.i(LOG_TAG, "Second task done");
                    secondTaskDone = true;
                    resultInterface.returnSortResult(list, nextTaskNo + 1);
                    nextTask();
                }
            };
            startAsyncTasks();
        }
        //если работа уже запущена, ничего не делаем
    }

    private void startAsyncTasks() {
        Log.i(LOG_TAG, "Starting next tasks");
        SortAsyncTask firstSortAsyncTask = new SortAsyncTask(firstEndTaskCallbackInterface);
        firstSortAsyncTask.execute(taskList.get(nextTaskNo));
        if (taskList.size() > nextTaskNo + 1) {
            SortAsyncTask secondSortAsyncTask = new SortAsyncTask(secondEndTaskCallbackInterface);
            secondSortAsyncTask.execute(taskList.get(nextTaskNo + 1));
        }
    }

    private void nextTask() {
        if ((firstTaskDone && secondTaskDone)) {
            nextTaskNo += 2;
            firstTaskDone = false;
            secondTaskDone = false;
            if (nextTaskNo < taskList.size()) {
                startAsyncTasks();
            } else {
                Log.i(LOG_TAG, "All tasks done");
                taskList.clear();
                nextTaskNo = 0;
            }
        }
    }

    private static class SortAsyncTask extends AsyncTask<Task, Void, List<? extends Mechanizm>> {
        private Interfaces.EndTaskCallbackInterface callbackInterface;

        SortAsyncTask(Interfaces.EndTaskCallbackInterface callbackInterface) {
            this.callbackInterface = callbackInterface;
        }

        @Override
        protected void onPostExecute(List<? extends Mechanizm> sortedList) {
            super.onPostExecute(sortedList);
            callbackInterface.taskDone(sortedList);
        }

        @Override
        protected List<? extends Mechanizm> doInBackground(Task... tasks) {
            return tasks[0].getSortType().sort(tasks[0].getMechanismList());
        }
    }
}