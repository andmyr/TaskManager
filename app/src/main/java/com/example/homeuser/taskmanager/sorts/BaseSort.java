package com.example.homeuser.taskmanager.sorts;

import java.util.List;

public abstract class BaseSort<T> {
    public abstract List<T> sort(List<T> list);

   /* protected <T> void swap(List<T> values, int first, int second) {
        T temp = values.get(first);
        values.set(first, values.get(second));
        values.set(second, temp);
    }*/
}
