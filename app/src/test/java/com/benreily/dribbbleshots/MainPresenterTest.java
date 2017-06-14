package com.benreily.zzzonked;

import com.benreily.zzzonked.model.pojo.DribbbleShotModel;
import com.benreily.zzzonked.repositories.DatabaseRepository;
import com.benreily.zzzonked.repositories.NetworkRepository;
import com.benreily.zzzonked.view.MainView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    DatabaseRepository databaseRepository;

    @Mock
    NetworkRepository networkRepository;

    @Mock
    MainView view;

    private MainPresenter presenter;

    private final List<DribbbleShotModel> MUCH_SHOTS = Arrays.asList(new DribbbleShotModel(), new DribbbleShotModel(), new DribbbleShotModel());
    private final List<DribbbleShotModel> SO_EMPTY = Collections.emptyList();

    @Before
    public void setup() throws Exception {
        presenter = new MainPresenter(view, databaseRepository, networkRepository);
    }

    @Test
    public void shouldPassShotsFromDbToView() {
        when(databaseRepository.loadShotsFromDb()).thenReturn(Single.just(MUCH_SHOTS));

        presenter.loadShotsFromRealm();

        verify(view).displayShots(MUCH_SHOTS);
    }
}
