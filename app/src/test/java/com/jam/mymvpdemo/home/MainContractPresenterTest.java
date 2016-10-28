package com.jam.mymvpdemo.home;

import com.jam.mymvpdemo.data.MainRepository;
import com.jam.mymvpdemo.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 作者：qingning on 2016/10/26 11:00
 */
public class MainContractPresenterTest {
    private MainContract.MainContractView mock;
    private MainRepository                mock2;
    private MainPresenter                 mainPresenter;

    @Before
    public void setUp() throws Exception {
        mock = mock(MainContract.MainContractView.class);
        mock2 = mock(MainRepository.class);
        mainPresenter = new MainPresenter(mock, mock2, new ImmediateSchedulerProvider());
    }

    @Test
    public void getInfo() throws Exception {
        when(mock2.getDataInfo()).thenReturn(Observable.just("123"));
        mainPresenter.getInfo();
//        verify(mock).showError();
        verify(mock).showSuccess("123");
    }

}