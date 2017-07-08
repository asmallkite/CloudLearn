package com.kite.cloudlearn.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kite.cloudlearn.R;
import com.kite.cloudlearn.base.baseadapter.BaseRecyclerViewAdapter;
import com.kite.cloudlearn.base.baseadapter.BaseRecyclerViewHolder;
import com.kite.cloudlearn.bean.GankIoDataBean;
import com.kite.cloudlearn.databinding.ItemWelfareBinding;
import com.kite.cloudlearn.utils.DensityUtil;

/**
 * Created by Zheng on 2017/7/5.
 */

public class WelfareAdapter extends BaseRecyclerViewAdapter<GankIoDataBean.ResultBean> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(parent, R.layout.item_welfare);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<GankIoDataBean.ResultBean, ItemWelfareBinding> {


        ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final GankIoDataBean.ResultBean resultsBean, final int position) {
            /**
             * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
             * 如果这样使用，则每个item的左右边距是不一样的，
             * 这样item不能复用，所以下拉刷新成功后显示会闪一下
             * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
             */
            if (position % 2 == 0) {
                DensityUtil.setViewMargin(itemView, false, 12, 6, 12, 0);
            } else {
                DensityUtil.setViewMargin(itemView, false, 6, 12, 12, 0);
            }
            binding.setBean(resultsBean);
            // 仿抖动
            binding.executePendingBindings();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(resultsBean, position);
                    }
                }
            });
        }
    }
}
