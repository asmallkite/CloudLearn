package com.kite.cloudlearn.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kite.cloudlearn.R;
import com.kite.cloudlearn.bean.book.BooksBean;
import com.kite.cloudlearn.databinding.FooterItemBookBinding;
import com.kite.cloudlearn.databinding.HeaderItemBookBinding;
import com.kite.cloudlearn.databinding.ItemBookBinding;
import com.kite.cloudlearn.ui.book.child.BookDetailActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10648 on 2017/6/8 0008.
 */

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int TYPE_FOOTER_BOOK = 0;
  public static final int TYPE_HEADER_BOOK = 1;
  public static final int TYPE_CONTENT_BOOK = 2;

  private int status = 4;
  public static final int LOAD_MORE = 3;
  public static final int LOAD_PULL_TO = 4;
  public static final int LOAD_NONE = 5;
  private static final int LOAD_END = 6;


  private Activity mContext;
  private List<BooksBean> mList;

  public BookAdapter(Context context) {
    mContext = (Activity) context;
    mList = new ArrayList<>();
  }

  @Override public int getItemViewType(int position) {
    if (position == 0) {
      return TYPE_HEADER_BOOK;
    } else if (position + 1 == getItemCount()) {
      return TYPE_FOOTER_BOOK;
    } else {
      return TYPE_CONTENT_BOOK;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case TYPE_HEADER_BOOK :
        HeaderItemBookBinding mBindHeader = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()),R.layout.header_item_book, parent, false);
        return new HeaderViewHolder(mBindHeader.getRoot());
      case TYPE_FOOTER_BOOK :
        FooterItemBookBinding mBindFooter = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()), R.layout.footer_item_book, parent, false
        );
        return new FooterViewHolder(mBindFooter.getRoot());
      default:
        ItemBookBinding mBindBook = DataBindingUtil.inflate(
            LayoutInflater.from(parent.getContext()), R.layout.item_book, parent, false
        );
        return new BookViewHolder(mBindBook.getRoot());
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof HeaderViewHolder) {
      //暂时没有做处理
    } else if (holder instanceof FooterViewHolder) {
      ((FooterViewHolder)holder).bindItem();
    } else if (holder instanceof BookViewHolder) {
      if (mList != null && mList.size() > 0) {
        ((BookViewHolder)holder).bindItem(mList.get(position - 1));
      }
    }
  }

  @Override public int getItemCount() {
    return mList.size() + 2;
  }

  /**
   * 处理 GridLayoutManager 添加头尾布局占满屏幕宽的情况
   */
  @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
    if (manager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override public int getSpanSize(int position) {
          if (position == 0 || position == getItemCount() - 1) {
            return gridLayoutManager.getSpanCount();
          }
          return 1;
        }
      });
    }
  }


  public void updateLoadStatus(int status) {
    this.status = status;
    notifyDataSetChanged();
  }

  private class HeaderViewHolder extends RecyclerView.ViewHolder {
    public HeaderViewHolder(View itemView) {
      super(itemView);
    }
  }

  private class FooterViewHolder extends RecyclerView.ViewHolder {

    FooterItemBookBinding mBindFooter;

    public FooterViewHolder(View itemView) {
      super(itemView);
      mBindFooter = DataBindingUtil.getBinding(itemView);
    }

    private void bindItem() {
      switch (status) {
        case LOAD_MORE:
          mBindFooter.progressFooter.setVisibility(View.VISIBLE);
          mBindFooter.tvLoadPrompt.setText("正在加载...");
          itemView.setVisibility(View.VISIBLE);
          break;
        case LOAD_PULL_TO:
          mBindFooter.progressFooter.setVisibility(View.VISIBLE);
          mBindFooter.tvLoadPrompt.setText("上拉加载更多");
          itemView.setVisibility(View.VISIBLE);
          break;
        case LOAD_NONE:
          mBindFooter.progressFooter.setVisibility(View.GONE);
          mBindFooter.tvLoadPrompt.setText("没有更多内容了");
          itemView.setVisibility(View.VISIBLE);
          break;
        case LOAD_END:
          itemView.setVisibility(View.GONE);
          break;
        default:
          break;
      }
    }
  }

  private class BookViewHolder extends RecyclerView.ViewHolder {
    ItemBookBinding mBindBook;

    public BookViewHolder(View itemView) {
      super(itemView);
      mBindBook = DataBindingUtil.getBinding(itemView);
    }

    private void bindItem(final BooksBean book) {
      mBindBook.setBean(book);
      mBindBook.executePendingBindings();

      mBindBook.llItemTop.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          BookDetailActivity.start(mContext, book, mBindBook.itTopPhoto);
        }
      });
    }
  }



  public List<BooksBean> getList() {
    return mList;
  }

  public void setList(List<BooksBean> list) {
    mList.clear();
    mList = list;
  }

  public void addAll(List<BooksBean> list) {
    mList.addAll(list);
  }

  public int getStatus() {
    return status;
  }
}
