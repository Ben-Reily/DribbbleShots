package com.benreily.zzzonked;

import com.benreily.zzzonked.model.api.DribbbleShotsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.annotation.Config;

@RunWith(MockitoJUnitRunner.class)
@Config(constants = BuildConfig.class)
public class RetrofitTest {

    @Mock
    private DribbbleShotsService dribbbleShotsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void retrofitTest() {


    }


}
