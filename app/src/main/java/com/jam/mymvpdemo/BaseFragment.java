package com.jam.mymvpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jam.mymvpdemo.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qingning on 2016/10/25 19:20
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 日志标记TAG
     */
    public final String TAG = getClass().getSimpleName();

    private List<BasePresenter> mAllPresenters;

    /**
     * 根布局
     */
    private View inflate;

    /** * 获取layout的id，具体由子类实现
     * @return 布局id
     */
    protected abstract int getLayoutResId();


    /**
     * 获取子类BasePresenter，多数情况下一个fragment只有一个BasePresenter时复写该方法
     */
    protected abstract BasePresenter addPresenter();

    /**
     * 获取BasePresenter，一个fragment有可能有多个BasePresenter时需要复写该方法
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
     * @param arguments 参数
     */
    protected void parseArguments(Bundle arguments){
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
        return (T) inflate.findViewById(viewID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(getLayoutResId(), container, false);
        if(getArguments() != null){
            parseArguments(getArguments());
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
        return inflate;
    }

    /**  ----------------------- 生命周期回调方法区 start ----------------------- */

    @Override
    public void onResume() {
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
    public void onPause() {
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
    public void onStart() {
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
    public void onDestroyView() {
        super.onDestroyView();
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
