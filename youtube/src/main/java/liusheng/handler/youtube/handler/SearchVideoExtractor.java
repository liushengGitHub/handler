package liusheng.handler.youtube.handler;

import liusheng.handler.http.utils.PatternUtils;
import liusheng.handler.youtube.AbstractChannelVideoExtractor;
import liusheng.handler.youtube.handler.entity.NextKeyEntity;
import liusheng.handler.youtube.handler.entity.SearchEntity;
import liusheng.handler.youtube.utils.YoutubeRetrofitUtils;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static liusheng.handler.http.utils.PatternUtils.searchKV;


/**
 * 年: 2020  月: 03 日: 23 小时: 17 分钟: 40
 * 用户名: LiuSheng
 */

public class SearchVideoExtractor extends AbstractChannelVideoExtractor<SearchEntity> {
    public static final String YOUTUBE_SEARCH_URL = "https://www.youtube.com/youtubei/v1/search";
    //https://www.youtube.com/results?search_query=hello
    private String searchWord = "";
    private String session_token = "";

    private static String step1Str = "";
    private static String step2Str = "";
    private static String step3Str = "";


    static {
        try {
            step1Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("searchVideo/step1.js"), "UTF-8");
            step2Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("searchVideo/step2.js"), "UTF-8");
            step3Str = IOUtils.toString(ChannelVideoExtractor
                    .class.getClassLoader().getResourceAsStream("searchVideo/step3.js"), "UTF-8");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String apiKey = "";

    public SearchVideoExtractor(String url) {
        super(url);
        int i = url.indexOf("?");

        if (i == -1) {
            throw new RuntimeException();
        }

        String queryString = url.substring(i + 1);

        String[] paramKv = queryString.split("=", 2);

        if (paramKv.length != 2) {
            throw new RuntimeException();
        }
        searchWord = paramKv[1];

    }

    public static void main(String[] args) throws Exception {
        SearchVideoExtractor searchVideoExtractor = new SearchVideoExtractor("https://www.youtube.com/results?search_query=%E4%B8%87%E6%9C%89%E5%BC%95%E5%8A%9B");

        System.out.println(searchVideoExtractor.init());
        System.out.println(searchVideoExtractor.get(1));
        System.out.println(searchVideoExtractor.get(2));
    }

    @Override
    protected Response getHttpResponseInNext() throws IOException {

        OkHttpClient client = YoutubeRetrofitUtils.getOkHttpClient();

        //new BasicNameValuePair("session_token", session_token)
        String str = "?key=" +  apiKey ;

        Request.Builder builder = new Request.Builder().post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"),
                gson.toJson(nextKeyEntity)))
                .url(YOUTUBE_SEARCH_URL + str);


        return client.newCall(builder.build()).execute();
    }

    @Override
    protected String getInitScript(Document document) {
        String info = document.select("script")
                .stream().map(Element::html)
                .filter(s -> s.contains("\"INNERTUBE_CONTEXT\""))
                .findFirst().orElse("");
        String script = super.getInitScript(document);
        return info + ";" + script;
    }

    @Override
    protected void handleNext(String next) {
        if (StringUtils.isBlank(next.trim())) {
            nextKeyEntity = null;
        } else {
            String eventId = searchKV(content, "EVENT_ID");
            SearchEntity entity  = gson.fromJson(next, SearchEntity.class);
            entity.getContext().setClientScreenNonce(eventId);
            if (nextKeyEntity != null) {
                nextKeyEntity.setContinuation(entity.getContinuation());
                nextKeyEntity.getContext().getClickTracking().setClickTrackingParams(entity.getContext().getClickTracking().getClickTrackingParams());
            }else {
                nextKeyEntity = entity;
            }

        }
    }

    @Override
    protected void initPost() {
        apiKey = searchKV(content, "INNERTUBE_API_KEY");
    }

    @Override
    protected void nextPost() {
        super.nextPost();
    }

    @Override
    protected String getStep2Str() {
        return step2Str;
    }

    @Override
    protected String getStep1Str() {
        return step1Str;
    }

    @Override
    protected String getStep3Str() {
        return step3Str;
    }
}
