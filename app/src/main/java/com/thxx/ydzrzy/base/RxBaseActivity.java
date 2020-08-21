package com.thxx.ydzrzy.base;

import android.os.Bundle;
import android.view.Window;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by saber on 2018/3/5
 * Activity基类
 */

public abstract class RxBaseActivity extends RxAppCompatActivity {
     private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局内容
        setContentView(getLayoutId());
        //设系统状态栏与APP保持一致
        // UIUtils.setNotificationColor(this,false, R.color.nav_head_image_background);
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //加载数据
        loadData();

    }


    /**
     * 设置布局layout
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    public abstract void initToolBar();

    /**
     * 加载数据
     */
    public void loadData() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}