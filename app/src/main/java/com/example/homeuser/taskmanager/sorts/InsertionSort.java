package com.example.homeuser.taskmanager.sorts;

import com.example.homeuser.taskmanager.models.Mechanizm;

import java.util.List;

public class InsertionSort<T extends Mechanizm> extends BaseSort<T> {
    @Override
    public List<T> sort(List<T> list) {
        T temp;
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0; j--) {
                if (list.get(j).getName().compareTo(list.get(j - 1).getName()) < 0) {
                    temp = list.get(j);
                    list.set(j, list.get(j - 1));
                    list.set(j - 1, temp);
                }
            }
        }
        return list;
    }
}
