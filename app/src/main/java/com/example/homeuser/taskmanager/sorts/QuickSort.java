package com.example.homeuser.taskmanager.sorts;

import com.example.homeuser.taskmanager.models.Mechanizm;

import java.util.List;

public class QuickSort<T extends Mechanizm> extends BaseSort<T> {

    private List<T> list;

    @Override
    public List<T> sort(List<T> list) {
        this.list = list;
        int length = list.size();
        quickSort(0, length - 1);
        return this.list;
    }

    private void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        T pivot = list.get(lowerIndex + (higherIndex - lowerIndex) / 2);
        while (i <= j) {
            while (list.get(i).getName().compareTo(pivot.getName()) < 0) {
                i++;
            }
            while (list.get(j).getName().compareTo(pivot.getName()) > 0) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                i++;
                j--;
            }
        }
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    }

    private void exchangeNumbers(int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}