package com.kite.cloudlearn.http;

import com.kite.cloudlearn.bean.book.BookBean;
import com.kite.cloudlearn.bean.book.BookDetailBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 10648 on 2017/5/18 0018.
 */

public interface HttpClient {

  class Builder {
    public static HttpClient getDoubanService() {
      return HttpUtils.getInstance().getDouBanServer(HttpClient.class);
    }
  }

  /**
   * 根据tag获取图书
   *
   * @param tag   搜索关键字
   * @param count 一次请求的数目 最多100
   */

  @GET("v2/book/search") Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

  @GET("v2/book/{id}")
  Observable<BookDetailBean> getBookDetail(@Path("id") String id);
}
