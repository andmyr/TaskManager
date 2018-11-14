package com.example.homeuser.taskmanager.models;

import com.example.homeuser.taskmanager.sorts.BaseSort;

import java.util.List;

public class Task {
    private BaseSort sortType;
    private List<? extends Mechanizm> mechanismList;

    public Task(BaseSort sortType, List<? extends Mechanizm> mechanismList) {
        this.sortType = sortType;
        this.mechanismList = mechanismList;
    }

    public BaseSort getSortType() {
        return sortType;
    }

    public void setSortType(BaseSort sortType) {
        this.sortType = sortType;
    }

    public List<? extends Mechanizm> getMechanismList() {
        return mechanismList;
    }

}
