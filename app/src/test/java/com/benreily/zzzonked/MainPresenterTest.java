package com.benreily.zzzonked;

import com.benreily.zzzonked.model.pojo.DribbbleShot;
import com.benreily.zzzonked.repositories.DatabaseRepository;
import com.benreily.zzzonked.repositories.NetworkRepository;
import com.benreily.zzzonked.view.MainView;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.*;

public class MainPresenterTest {


    @Test
    public void should_pass_shots_to_view() {

        // given
        MainView view = new MockView();
        DatabaseRepository shotsRepository = new DbRepoMock();
        NetworkRepository networkRepository = new NetRepoMock();

        // when
        MainPresenter presenter = new MainPresenter(view, shotsRepository, networkRepository);
        presenter.loadShotsFromDb();

        // then
        assertEquals(true, ((MockView)view).displayWithShotsCalled);
    }


    @Test
    public void should_handle_no_shots() {

        MainView view = new MockView();
        DatabaseRepository shotsRepository = new DbRepoMock();
        NetworkRepository networkRepository = new NetRepoMock();

        MainPresenter presenter = new MainPresenter(view, shotsRepository, networkRepository);
        presenter.loadShotsFromDb();

        assertEquals(true, ((MockView) view).displayWithoutShotsCalled);

    }


    private class MockView implements MainView {

        boolean displayWithShotsCalled;
        boolean displayWithoutShotsCalled;

        @Override
        public void displayShots(Observable<List<DribbbleShot>> shotsList) {
            if (shotsList != null) {
                displayWithShotsCalled = true;
            }
        }

        @Override
        public void displayNoShots() {
            displayWithoutShotsCalled = true;
        }
    }

    private class DbRepoMock implements DatabaseRepository {

        @Override
        public Observable<List<DribbbleShot>> loadShotsFromDb() {
            return Observable.just(new ArrayList<DribbbleShot>());
        }

        @Override
        public void saveShotsToDb() {

        }
    }

    private class NetRepoMock implements NetworkRepository {

        @Override
        public Observable<List<DribbbleShot>> fetchShots(int page) {
            return Observable.just(new ArrayList<DribbbleShot>());
        }

        @Override
        public Observable<List<DribbbleShot>> fetchShots(int page, int perPage, String list, String sort) {
            return Observable.just(new ArrayList<DribbbleShot>());
        }

        @Override
        public Observable<DribbbleShot> getShot(long shotId) {
            return Observable.just(new DribbbleShot());
        }
    }

}
