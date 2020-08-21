package com.thxx.ydzrzy.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class RxLazyFragment extends RxFragment {


    private FragmentActivity mActivity;
    private View mParentView;
    protected boolean mIsPrepared;
    //标志位 fragment是否可见
    protected boolean mIsVisible;

    public abstract
    @LayoutRes
    int getLayoutResId();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(getLayoutResId(), container, false);
        mActivity = getSupportActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finishCreateView(savedInstanceState);
    }

    /**
     * 初始化views
     *
     * @param savedInstanceState 状态
     */
    protected abstract void finishCreateView(Bundle savedInstanceState);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
    }

    public FragmentActivity getSupportActivity() {
        return getActivity();
    }

    /**
     * Fragment数据的懒加载
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * fragment隐藏
     */
    protected void onInvisible() {
    }

    /**
     * fragment显示时才加载数据
     */
    protected void onVisible() {
    }

    /**
     * fragment懒加载方法
     */
    protected void lazyLoad() {
    }


    /**
     * 加载数据
     */
    protected void loadData() {
    }

    /**
     * 显示进度条
     */
    protected void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    protected void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    protected void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    protected void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    protected void finishTask() {
    }


    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int id) {
        return (T) mParentView.findViewById(id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }
}
