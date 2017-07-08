package com.kite.cloudlearn.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.kite.cloudlearn.R;
import com.kite.cloudlearn.adapter.WelfareAdapter;
import com.kite.cloudlearn.app.Constants;
import com.kite.cloudlearn.base.BaseFragment;
import com.kite.cloudlearn.bean.GankIoDataBean;
import com.kite.cloudlearn.databinding.FragmentWelfareBinding;
import com.kite.cloudlearn.http.HttpUtils;
import com.kite.cloudlearn.model.GankOtherModel;
import com.kite.cloudlearn.net.ACache;
import com.kite.cloudlearn.net.RequestImpl;
import com.kite.cloudlearn.view.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zheng on 2017/7/2.
 */

public class WelfareFragment extends BaseFragment<FragmentWelfareBinding> {

    private boolean isPrepared = false;
    private boolean isFirst = true;
    private GankIoDataBean meiziBean;
    private GankOtherModel mModel;
    private int mPage = 1;
    ArrayList<String> imgList = new ArrayList<>();

    private ACache aCache;
    private WelfareAdapter mWelfareAdapter;

    @Override
    public int setContent() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new GankOtherModel();
        aCache = ACache.get(getContext());
        mWelfareAdapter = new WelfareAdapter();
        bindingView.xrvWelfare.setPullRefreshEnabled(false);
        bindingView.xrvWelfare.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });

        isPrepared = true;
    }

    @Override
    protected void loadData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        if (meiziBean != null && meiziBean.getResults() != null
                && meiziBean.getResults().size() > 0) {

        } else {
            loadWelfareData();
        }
    }

    private void loadWelfareData() {
        mModel.setData("福利", mPage, HttpUtils.per_page_more);
        mModel.getGankIoData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                showContentView();
                GankIoDataBean gankIoDataBean = (GankIoDataBean) object;
                if (mPage == 1) {
                    if (gankIoDataBean != null && gankIoDataBean.getResults() != null
                            && gankIoDataBean.getResults().size() > 0) {
                        imgList.clear();
                        for (int i = 0; i < gankIoDataBean.getResults().size(); i++) {
                            imgList.add(gankIoDataBean.getResults().get(i).getUrl());
                        }
                        setAdapter(gankIoDataBean);
                        aCache.remove(Constants.GANK_MEIZI);
                        aCache.put(Constants.GANK_MEIZI, gankIoDataBean, 30000);

                    }
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addDisposable(Disposable disposable) {

            }
        });
    }

    public void setAdapter(GankIoDataBean adapter) {
        mWelfareAdapter.addAll(adapter.getResults());
        bindingView.xrvWelfare.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        bindingView.xrvWelfare.setAdapter(mWelfareAdapter);
        mWelfareAdapter.notifyDataSetChanged();
        isFirst = false;
    }
}
