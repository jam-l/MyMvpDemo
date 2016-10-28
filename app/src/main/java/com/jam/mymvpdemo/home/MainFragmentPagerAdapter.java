package com.jam.mymvpdemo.home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam.mymvpdemo.R;

/**
 * 作者：qingning on 2016/10/25 20:19
 */

class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int COUNT = 5;
    private final Context context;
    private String[] titles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5"};

    MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private View getTabView(int position) {
        View      view   = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv_tab = (ImageView) view.findViewById(R.id.iv_tab);
        TextView  tv_tab = (TextView) view.findViewById(R.id.tv_tab);
        iv_tab.setImageResource(R.mipmap.ic_launcher);
        tv_tab.setText(titles[position]);
        return view;
    }

    void setTabLayout(TabLayout tabLayout){
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //设置自定义的标题
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }
    }
}
