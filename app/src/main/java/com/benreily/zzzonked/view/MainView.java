package com.benreily.zzzonked.view;

import com.benreily.zzzonked.model.pojo.DribbbleShot;

import java.util.List;

import io.reactivex.Observable;

public interface MainView {
    void displayShots(Observable<List<DribbbleShot>> shotsList);

    void displayNoShots();

}
