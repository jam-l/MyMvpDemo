package com.jam.mymvpdemo.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jam.mymvpdemo.BaseFragment;
import com.jam.mymvpdemo.R;
import com.jam.mymvpdemo.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance(int arg1) {
        Bundle args = new Bundle();
        args.putInt("arg1", arg1);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected BasePresenter addPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindEvent() {

    }

}
