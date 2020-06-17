package liusheng.handler.youtube.service;

import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.IOException;

/**
 * 年: 2020  月: 05 日: 04 小时: 11 分钟: 10
 * 用户名: LiuSheng
 */

public interface YoutubeService  {
    //https://www.youtube.com/watch?v=X4Q_vTnLBBI

    @GET("/watch")
    Call<ResponseBody> page(@Query("v") String videoId);

    @GET("/channel/{channelId}/videos")
    Call<ResponseBody> url(@Path("channelId") String channelId);

  /*  public static void main(String[] args) throws IOException {
        YoutubeService youtubeService =
                YoutubeRetrofitUtils.retrofit().create(YoutubeService.class);

        String string = youtubeService.url("https://www.youtube.com/channel/UCGYmHiw4vrqEeEjzlocYJRg/videos")
                .execute().body().string();

        System.out.println(string);
    }*/
}
