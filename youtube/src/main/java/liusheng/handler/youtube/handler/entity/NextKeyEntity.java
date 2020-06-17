package liusheng.handler.youtube.handler.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 年: 2020  月: 03 日: 24 小时: 11 分钟: 34
 * 用户名: LiuSheng
 */

public class NextKeyEntity {
    private String continuation;
    private String clickTrackingParams;
    private String ctoken;
    private Map<String, String> params;

    public String getContinuation() {
        return continuation;
    }

    public void setContinuation(String continuation) {
        this.continuation = continuation;
    }

    public String getClickTrackingParams() {
        return clickTrackingParams;
    }

    public void setClickTrackingParams(String clickTrackingParams) {
        this.clickTrackingParams = clickTrackingParams;
    }

    public String getCtoken() {
        return ctoken;
    }

    public void setCtoken(String ctoken) {
        this.ctoken = ctoken;
    }

    @Override
    public String toString() {

        if (Objects.isNull(params)) {
            params = new HashMap<>();
            params.put("ctoken",ctoken);
            params.put("continuation",continuation);
            params.put("itct",clickTrackingParams);
        }
        return params.entrySet()
                .stream()
                .map(stringStringEntry -> {
                    return stringStringEntry.getKey() + "=" + stringStringEntry.getValue();
                })
                .collect(Collectors.joining("&"));
    }
}
