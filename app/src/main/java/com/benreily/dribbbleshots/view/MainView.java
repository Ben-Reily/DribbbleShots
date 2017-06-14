package com.benreily.zzzonked.view;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;

import java.util.List;

public interface MainView {

    void displayShots(List<DribbbleShotModel> shotsList);

    void displayNoShots();

    void displayError();

    void displaySuccessMessage();

    void removeAllPreviousShots();

    void reattachScrollListener();

}
