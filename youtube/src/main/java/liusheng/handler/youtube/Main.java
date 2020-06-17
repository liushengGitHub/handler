package liusheng.handler.youtube;

import liusheng.handler.youtube.interceptor.HeaderInterceptor;
import liusheng.handler.youtube.service.YoutubeService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * 2020年:  05 月:  04 日:  11小时:  02分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://www.youtube.com")
                .build();

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        Call<ResponseBody> bodyCall =
                youtubeService.page("X4Q_vTnLBBI");


        bodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
