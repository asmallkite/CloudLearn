package com.kite.cloudlearn.http;

import com.kite.cloudlearn.bean.GankIoDataBean;
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
    public static HttpClient getGankIoServer() {
      return HttpUtils.getInstance().getGankIOServer(HttpClient.class);
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

  /**
   * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
   * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
   * 请求个数： 数字，大于0
   * 第几页：数字，大于0
   * eg: http://gank.io/api/data/Android/10/1
   */
  @GET("data/{type}/{per_page}/{page}")
  Observable<GankIoDataBean> getGankIoData(@Path("type") String id, @Path("page") int page,
                                           @Path("per_page") int per_page);

}
