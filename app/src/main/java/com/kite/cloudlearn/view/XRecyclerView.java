package com.kite.cloudlearn.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Zheng on 2017/7/2.
 */

public class XRecyclerView extends RecyclerView {

    private LoadingListener mLoadingListener;
    private boolean pullRefreshEnabled = true;

    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        this.pullRefreshEnabled = pullRefreshEnabled;
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        mLoadingListener = loadingListener;
    }

    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if (pullRefreshEnabled) {
            //TODO:
        }
    }

    /**
     * 相当于加一个空白头布局：
     * 只有一个目的：为了滚动条显示在最顶端
     * 因为默认加了刷新头布局，不处理滚动条会下移。
     * 和 setPullRefreshEnabled(false) 一块儿使用
     * 使用下拉头时，此方法不应被使用！
     */
    public void clearHeader() {

    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();

    }
}
