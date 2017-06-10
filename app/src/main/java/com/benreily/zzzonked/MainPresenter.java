package com.benreily.zzzonked;

import com.benreily.zzzonked.repositories.DatabaseRepository;
import com.benreily.zzzonked.repositories.DatabaseRepository.EmptyDbException;
import com.benreily.zzzonked.repositories.NetworkRepository;
import com.benreily.zzzonked.view.MainView;

public class MainPresenter {

    private MainView view;
    private DatabaseRepository databaseRepo;
    private NetworkRepository networkRepo;

    public MainPresenter(MainView view, DatabaseRepository databaseRepo, NetworkRepository networkRepo) {
        this.view = view;
        this.databaseRepo = databaseRepo;
        this.networkRepo = networkRepo;
    }

    public void loadShotsFromDb() {
        try {
            view.displayShots(databaseRepo.loadShotsFromDb());
        } catch (EmptyDbException | Exception ex) {
            ex.printStackTrace();
        } finally {
            view.displayNoShots();
        }
    }

    public void loadShotsFromNetwork(int page) {
        view.displayShots(networkRepo.fetchShots(page));
    }

    public void loadShotsFromNetwork(int page, int perPage, String list, String sort) {
        view.displayShots(networkRepo.fetchShots(page, perPage, list, sort));
    }

}
