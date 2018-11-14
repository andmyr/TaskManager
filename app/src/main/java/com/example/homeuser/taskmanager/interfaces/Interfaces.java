package com.example.homeuser.taskmanager.interfaces;

import com.example.homeuser.taskmanager.models.Mechanizm;

import java.util.List;

public class Interfaces {

    public interface EndTaskCallbackInterface {
        void taskDone(List<? extends Mechanizm> list);
    }

    public interface EndFirstCallbackInterface extends EndTaskCallbackInterface {
        void taskDone(List<? extends Mechanizm> list);
    }

    public interface EndSecondCallbackInterface extends EndTaskCallbackInterface {
        void taskDone(List<? extends Mechanizm> list);
    }

    public interface ReturnSortResultInterface {
        void returnSortResult(List<? extends Mechanizm> list, int taskNo); //по номеру задания мы будем знать какой
        // список нужно обновить
    }
}
