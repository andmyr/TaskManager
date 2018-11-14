package com.example.homeuser.taskmanager.sorts;

import com.example.homeuser.taskmanager.models.Mechanizm;

import java.util.List;

public class BubbleSort<T extends Mechanizm> extends BaseSort<T> {
    @Override
    public List<T> sort(List<T> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++)
            for (int j = 0; j < size - i - 1; j++)
                if (list.get(j).getName().compareTo(list.get(j + 1).getName()) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
        return list;
    }
}
