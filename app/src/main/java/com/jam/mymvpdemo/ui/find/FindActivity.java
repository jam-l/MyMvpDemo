package com.jam.mymvpdemo.ui.find;

import android.content.Intent;
import android.os.Bundle;

import com.jam.mymvpdemo.BaseActivity;
import com.jam.mymvpdemo.R;
import com.jam.mymvpdemo.interfaces.PageNames;
import com.jam.mymvpdemo.presenter.BasePresenter;
import com.jam.mymvpdemo.util.ActivityUtils;

public class FindActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_find;
    }

    @Override
    protected void parseArgumentsFromIntent(Intent argIntent) {
        super.parseArgumentsFromIntent(argIntent);
        Bundle extras = argIntent.getExtras();
        if(extras!=null){
            String video_id = extras.getString(PageNames.PageParameters.FIND_VIDEO_ID);
            showToast(video_id);
        }
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
