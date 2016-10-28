package com.jam.mymvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.jam.mymvpdemo.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qingning on 2016/10/25 19:20
 */
public abstract class BaseActivity extends FragmentActivity {
    /**
     * 日志标记TAG
     */
    public final String TAG = getClass().getSimpleName();

    private List<BasePresenter> mAllPresenters;

    /** * 获取layout的id，具体由子类实现
     * @return 布局id
     */
    protected abstract int getLayoutResId();


    /**
     * 获取子类BasePresenter，多数情况下一个activity只有一个BasePresenter时复写该方法
     */
    protected abstract BasePresenter addPresenter();

    /**
     * 获取BasePresenter，一个activity有可能有多个BasePresenter时需要复写该方法
     */
    protected List<BasePresenter> getPresenters() {
        List<BasePresenter> presenters = new ArrayList<>();
        presenters.add(addPresenter());
        return presenters;
    }

    protected void addPresenter(BasePresenter basePresenter){
        mAllPresenters.add(basePresenter);
    }

    private void addPresenters(){
        mAllPresenters = getPresenters();
    }

    /** * 从intent中解析数据，具体子类来实现
     * @param argIntent 参数
     */
    protected void parseArgumentsFromIntent(Intent argIntent){
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 绑定事件
     */
    protected abstract void bindEvent();

    /**
     *  查找id
     */
    protected <T> T $(int viewID) {
        return (T) findViewById(viewID);
    }

    /**  ----------------------- 生命周期回调方法区 start ----------------------- */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        if(getIntent() != null){
            parseArgumentsFromIntent(getIntent());
        }
        initView();
        initData();
        bindEvent();

        addPresenters();
        if(mAllPresenters!=null){
            for (BasePresenter presenter: mAllPresenters) {
                if(presenter != null){
                    presenter.onCreate();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //依次调用IPresenter的onResume方法
        if(mAllPresenters!=null){
            for (BasePresenter presenter: mAllPresenters) {
                if(presenter != null){
                    presenter.onResume();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAllPresenters!=null){
            for (BasePresenter presenter: mAllPresenters) {
                if(presenter != null){
                    presenter.onPause();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAllPresenters!=null){
            for (BasePresenter presenter: mAllPresenters) {
                if(presenter != null){
                    presenter.onStart();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAllPresenters!=null){
            for (BasePresenter presenter: mAllPresenters) {
                if(presenter != null){
                    presenter.onDestroy();
                }
            }
        }
    }
    /**  ----------------------- 生命周期回调方法区 end ----------------------- */

    /**
     * 显示Toast
     * @param msg 消息
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
