package com.jam.mymvpdemo.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jam.mymvpdemo.BaseActivity;
import com.jam.mymvpdemo.R;
import com.jam.mymvpdemo.presenter.BasePresenter;
import com.jam.mymvpdemo.util.Injection;


public class MainActivity extends BaseActivity implements MainContract.MainContractView, View.OnClickListener {

    private MainContract.MainContractPresenter mainPresenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter addPresenter() {
        return mainPresenter = new MainPresenter(this, Injection.provideTasksRepository(), Injection.provideSchedulerProvider());
    }

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initData() {
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.setTabLayout(tabLayout);
    }

    @Override
    protected void bindEvent() {

    }

    @Override
    public void showSuccess(String info) {
        showToast(info);
    }

    @Override
    public void showError() {
        showToast("Error");
    }

    @Override
    public void onClick(View v) {
        mainPresenter.getInfo();
    }

    @Override
    public void setPresenter(MainContract.MainContractPresenter presenter) {

    }
}
