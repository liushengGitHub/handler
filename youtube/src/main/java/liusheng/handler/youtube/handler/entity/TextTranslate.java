package liusheng.handler.youtube.handler.entity;

/**
 * 2020年:  05 月:  04 日:  13小时:  36分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class TextTranslate {

    /**
     * baseUrl : https://www.youtube.com/api/timedtext?v=c2pYQ2PcVnE&xorp=true&hl=ko&ip=0.0.0.0&ipbits=0&expire=1588595489&sparams=ip,ipbits,expire,v,xorp&signature=7C2D33B22AA37B27F3E7813825A8767690037387.E214850B78517B966796C2126EA26094AD842F4A&key=yt8&lang=ja
     * name : 일본어
     * vssId : .ja
     * isTranslatable : true
     */

    private String baseUrl;
    private String name;
    private String vssId;
    private boolean isTranslatable;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVssId() {
        return vssId;
    }

    public void setVssId(String vssId) {
        this.vssId = vssId;
    }

    public boolean isIsTranslatable() {
        return isTranslatable;
    }

    public void setIsTranslatable(boolean isTranslatable) {
        this.isTranslatable = isTranslatable;
    }
}
