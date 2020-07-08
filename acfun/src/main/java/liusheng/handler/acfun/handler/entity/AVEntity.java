package liusheng.handler.acfun.handler.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 2020年:  06 月:  17 日:  22小时:  43分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class AVEntity {

    /**
     * currentVideoId : 13271875
     * isLike : false
     * pctr : 0.02
     * commentCountRealValue : 67
     * bananaCountShow : 2016
     * stowCount : 501
     * stowCountShow : 501
     * giftPeachCountShow : 66
     * channel : {"parentId":123,"parentName":"舞蹈·偶像","name":"宅舞","id":134}
     * description : 摄影/后期：子枫这次跟粥粥一起跳了这支宅舞哈哈哈文艺复兴
     * likeCount : 233
     * title : 【海豹粥×喵桃】love potion 点击收获两只猫女仆
     * hasHotComment : false
     * shareCountShow : 11
     * result : 0
     * shareCount : 11
     * picShareUrl : https://m.acfun.cn/v/?ac=16184862&shareType=pic
     * originalDeclare : 1
     * videoList : [{"priority":0,"visibleType":1,"title":"LP","danmakuCount":31,"sourceStatus":3,"durationMillis":209211,"sizeType":1,"danmakuCountShow":"31","uploadTime":1592119345853,"fileName":"LP","id":"13271875"}]
     * danmakuCount : 31
     * isThrowBanana : false
     * bananaCount : 2016
     * viewCount : 11210
     * currentVideoInfo : {"priority":0,"ksPlayJson":"{\"version\":\"1.0\",\"type\":\"hls\",\"adaptationSet\":{\"representation\":[{\"bandwidth\":6881600,\"codecs\":\"avc1.640028,mp4a.40.2\",\"width\":1920,\"height\":1080,\"frameRate\":25,\"qualityType\":\"1080p+\",\"qualityLabel\":\"1080P+\",\"url\":\"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.m3u8?pkey=AAKkR99H0khHWhnDr03sy5a7-lvKFYn28moEcq2DUjrH4rSs44XZF06gvONkAiEFQQIFHu30BJ-3y9iKQH9TbjYe9fWM5OpgQpFgFtPjHUlqhbpPo-_AD00Gd9VfnttpC_7Z8W_4-HB7fbydtVLKmcMNEkdKWbL_A9ly1cnnzhW_spPnqwmDuUuJBtZxaTSY_hNeP5VUh53ThUUKoASGO7x9cgE3B6Lgrn_AVpDSr-eaqcT5yDtPpNsBs965wnczSaw&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\",\"backupUrl\":[\"https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.m3u8?pkey=AAKkR99H0khHWhnDr03sy5a7-lvKFYn28moEcq2DUjrH4uPjbGF1mUYat7RMItsNBg7hjDdwf9mOTnA6vkqzb9VIxCwlVPNX1d1FDMfKy-D-PnynKUrSW5oLgQha7SOQQXXVuGTPs4q7KrRAZBgx5VVP5mHBR1JXjsEVypmLFnLDNQNB2gnFm29SQQvwQlbm-HuOTVmfd0QCrtZOPzC9fI0MRSEb7cWCTu6jWNmGd-I1ov5Oh1FkLsxtVWH8cuAL4-Q&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\"],\"m3u8Slice\":\"#EXTM3U\\n#EXT-X-VERSION:4\\n#EXT-X-TARGETDURATION:5\\n#EXT-X-MEDIA-SEQUENCE:0\\n#EXTINF:5.0,\\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.00000.ts?pkey=AAInQuqKAAklPUwp1yXCy8rQAvW313_x-K0HuEMh9-D_DtA8-kx-IqPfXTWQVkmtlkBHJAQSOnz6etejehWvADm8PoR7f_b2MEVR4_zQT3UVDNdv46lQXIZCHpNEvzqjvMQCf-m9pYTfZgIDbs5cj3rCepTyhlaJw7dCOWMH6bk6Yo7T65ovpno4CYt_152YEUADYdyWsqdX8yFtytw5flPeSvP4vDTa3xggBijKxQ5qhwjzb6tYZsUUXM52Y0FOe20&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\\n\",\"duration\":209.16},{\"bandwidth\":3581600,\"codecs\":\"avc1.640028,mp4a.40.2\",\"width\":1920,\"height\":1080,\"frameRate\":25,\"qualityType\":\"1080p\",\"qualityLabel\":\"1080P\",\"url\":\"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.m3u8?pkey=AALouV_UGLcvM0EPyC5ErDs50k5Mu-9L0swfMXZ22gRxFMl7GI-VgyxR0c9P0K_Jy1OqyfyyyUthXTWoz4YX7J2EOuMo-LVxrBiXa_RkaPIbmb6anupegLyaQqmCsz5vEK2y8YSMxTERk2jSoxSFwlMVa0GGmZcix6XGg2dAAlm5PABzn3aItRdmfvxNsmzcSlZ3gCNvlRsQBtRBE3lt57tJdRp5HIx6TpuIg_AhaylZwof5YQxiPq9eljxLuzwdyW4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\",\"backupUrl\":[\"https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.m3u8?pkey=AALouV_UGLcvM0EPyC5ErDs50k5Mu-9L0swfMXZ22gRxFF6WBNBiIiYmnDu7PxqTxX-DZHaf7mj4-pUO4f8re-LnEfbMasb5vbk5wPW4W-tjLhicTkhdUqZ6ut75wo5XjQRXWUxxB_G1YB35hyiOQuPY7Ng6Nd8wGiU3lF0RiEH93SvpYE-afeaw1BY_OtUQo_f9BkCovGahSUGiW9rGpAlN_x2RXOJaDEURNAMbxmYzId2PYrnqoG2POT9Clay7Twk&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\"],\"m3u8Slice\":\"#EXTM3U\\n#EXT-X-VERSION:4\\n#EXT-X-TARGETDURATION:5\\n#EXT-X-MEDIA-SEQUENCE:0\\n#EXTINF:5.0,\\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.00000.ts?pkey=AAJ47w34rJ0ovYUVUoqHwdDnDU2zvV8r7FLNDDHCzikgB4dNEOC45V8vipks9vZTuwj_tmtFsLGCQFe_6UqmOJOleGLmaXN_sCfr2--SjyTM7rn0HNlp6_TSJHgU--vdSa83tk87Z0BOx0_vbQnD2EMEr88oIC_aW9vnNiXO9yEPzeLZ_v3h7R6Me5CWOPVjAnqPB0GI9lpUORBE1Cr4W1wR6x1a42Vp6L5BampPrwuxEsJrv_nNUC50Fb1ssPwHaj0&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\\n\",\"duration\":209.16},{\"bandwidth\":2340800,\"codecs\":\"avc1.64001f,mp4a.40.2\",\"width\":1280,\"height\":720,\"frameRate\":25,\"qualityType\":\"720p\",\"qualityLabel\":\"超清\",\"url\":\"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.m3u8?pkey=AAKgdQPPZEUdS_XUWCIapogJ2WeV88ZS3vs83hrTQ6FtnUvYCkUwCC02lYY76SNMHHiN3GeCwJGfgYk78UxHkBsMj0WHw4WNQqBmIQptMw28d1rETRDETl4jCHvqqzSD_-AxGluGpTTM9Wgqf8BjbwRUMB-iM598k_OL1XOt6noW9_FiFF3VFtqaeAWhZxJTB0RaozSXig-_IQ4mfFkierjXjBMWy92O9gXzCa2cZOO6yQrgM3FM_D410Pv0BIgPAXk&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\",\"backupUrl\":[\"https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.m3u8?pkey=AAKgdQPPZEUdS_XUWCIapogJ2WeV88ZS3vs83hrTQ6FtnQ0vt1rZ-hyyT_p6SD1UwAtcvczUFaAqO3EiY8VnUvG3ZK02vNY0E9qTH6yEaHT2sucwHGNJkY7pbSHQyG9wi9vPQFiHIDaMgkDlDxD6i4zas_7ZsHrlFUZHxR8FZfmvQN0VQpYnjwqScvK06mYrmcbN9h8QRukLJNSQJ8kCr_1u2QDqY2dmEeMcD_0RNP_BXnupaStGK8LjO4lHYAKeEcM&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\"],\"m3u8Slice\":\"#EXTM3U\\n#EXT-X-VERSION:4\\n#EXT-X-TARGETDURATION:5\\n#EXT-X-MEDIA-SEQUENCE:0\\n#EXTINF:5.0,\\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.00000.ts?pkey=AAKG9SY9Hr_DIo6-bbTFsPINedv89Nf54R-Qg6vLORLduLldolVva3c2bfFn-6qQ9vCWbo4ovwj3LV-fvvaEpl0RDgU2PnsMdqNUEse7lzUy7duyFjyf1tEkDPk8Qk-b4jwdYVnrHoCF_irK1EBqcI98ECQA_XBEfCZZRESUr4JjV1c_a53h1e1OnZJSssTbAIjIO8yKKK3wWsDwwDiNI9A1JnLFLSl38sb_5zmqiOaR4FOBs2JyWay6ymVTMw1NtxI&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\\n\",\"duration\":209.16,\"defaultSelect\":true},{\"bandwidth\":1060400,\"codecs\":\"avc1.64001f,mp4a.40.2\",\"width\":960,\"height\":540,\"frameRate\":25,\"qualityType\":\"540p\",\"qualityLabel\":\"高清\",\"url\":\"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.m3u8?pkey=AAJiBzh4HOVnqpECnXY1STRsH58p7Bm_OHe58c_7cYT15DLEqTEDGenXJcfW1uHA2epOcM5jB7vpYng2IfoJ9H32fxruP4mhSUyIklpuxfk5vdF7k0tO1gzUsE0QrP60kuaBOdMJBExth67UcKUw5H9ZX7B9pHsJIsm0ZzmBf4vbtkZXti_Ceaj1OZ07FCDuadANbtIgNfi8Rrq5UJT2UvLn-raMuMPZbVQkE0n8Uy67sQt2oDS3EKb7Mksql4c9rYI&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\",\"backupUrl\":[\"https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.m3u8?pkey=AAJiBzh4HOVnqpECnXY1STRsH58p7Bm_OHe58c_7cYT15PtpfaJuP0HiuHyYJuiAXtRTabjX-O8MvwOxjsnjFvt4LF4qxcJoN-hsmzi3pqI8RIq3HiPnyyJV3WpGTpHH6GI1WtR_uIT7RFRFwJ_SPThRpv4FUS42KQpbD4sxYQArG3h_EGCJB03eXb4aBjouoXpzuHZDZehFqTam8_102Bfg-lozaqvsWbfM94oprfibAm944wzdoZETynbks48vGW8&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\"],\"m3u8Slice\":\"#EXTM3U\\n#EXT-X-VERSION:4\\n#EXT-X-TARGETDURATION:5\\n#EXT-X-MEDIA-SEQUENCE:0\\n#EXTINF:5.0,\\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.00000.ts?pkey=AAJt3Xt49PbOZHy43HkH8u6JaRw9_K57q--zK2RDEkEL8rxo3LKybi0NWQXamJfMy-zDp-FYEljDbUChR0Cf1xxHNKktpGGUptDK2K0Z-6IE-vLGta8Z4QY2e-mu9TAgo1dY_ByrXUM0LogF9GnskQANGLM-QWfxgSu9nXhw8UJjWWxLEz5tkfvCrJo1-evtJgocsuglcg4Tq_rbGGnzK7oPm2j_LDCKBJzEas8U_awNZePnM8sEtAxIXFLHVcHIrW4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\\n\",\"duration\":209.16},{\"bandwidth\":620400,\"codecs\":\"avc1.64001e,mp4a.40.2\",\"width\":640,\"height\":360,\"frameRate\":25,\"qualityType\":\"360p\",\"qualityLabel\":\"标清\",\"url\":\"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.m3u8?pkey=AAJDMMZ-KSQrtF2UTMT4qaLQGnnJ5xfCYKE-YqHbOXhYtnYaw5fwIuZJdNi96ClYLLhiSOYxI8QnXRMwL_EydpwMygi0cu7Ekr_cHu13kBnoVKEhupIdIGzoCC4d3JgomQM-J8Sh0nScVCYeoCr8uL9sodED7Ckl2KB_EX-rqmDcoHapUG511Fl_K2awcZY4oEhgBxiyE6qtEbApF9SJrdGcb9f2wWcZ6TC_kxocoI7omBZ_lLQQOSeIZ5C_rui0hRE&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\",\"backupUrl\":[\"https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.m3u8?pkey=AAJDMMZ-KSQrtF2UTMT4qaLQGnnJ5xfCYKE-YqHbOXhYtsG1vFFsvP3wE-meVvvlHF0-h1V4eaeimYSaYxbOZg041w7ulMjAcdNaeRb9TUC_QpkXyb-Eepo_VvFd4cnOqNmbUH66hbamIgxlyK9y5AmoUhubi2EKxd6bT2KvofuMzzpOWq7nLiA4V92rEidDxTvtajN-vtqJyta-ajjaLqeLwipsLCvhCHviPc6hCur19YBH3HmzCoSSf4m9OENXWh4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\"],\"m3u8Slice\":\"#EXTM3U\\n#EXT-X-VERSION:4\\n#EXT-X-TARGETDURATION:5\\n#EXT-X-MEDIA-SEQUENCE:0\\n#EXTINF:5.0,\\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.00000.ts?pkey=AAKKn_-G3bSTl-0nKCfEi2CJMoK6pcc6asIMeNISZFY4wp572SFMpPKYxY8-fFw-ONAA5hPrPqhbdOkdXjygZ1r8mFA19O5abOkh3ja4h-g_7l2h9wxc1NQJJQt_uIfheFT1oKwx0zGeJ5-vpXHmmbvFEHZB-EXkG-sPklIrbdGQvilUGBtljZV-sFeEV4XUebjAKWx5WxRzco9z8xGUsou0wpC6_Ko9KjJqUoIfk0UmvvoRMA6z0GFyd6WDgQg-_3Y&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\\n\",\"duration\":209.16}]}}","visibleType":1,"title":"LP","danmakuCount":31,"sourceStatus":3,"durationMillis":209211,"sizeType":1,"danmakuCountShow":"31","uploadTime":1592119345853,"fileName":"LP","id":"13271875"}
     * coverCdnUrls : [{"url":"https://tx-free-imgs.acfun.cn/o_1eaor9h8n1qtj1ban9kr1k0371t0.jpeg?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":true},{"url":"https://imgs.aixifan.com/o_1eaor9h8n1qtj1ban9kr1k0371t0.jpeg?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":false}]
     * likeCountShow : 233
     * giftPeachCount : 66
     * viewCountShow : 1.1万
     * dougaId : 16184862
     * isRewardSupportted : true
     * commentCountShow : 67
     * durationMillis : 209211
     * commentCountTenThousandShow : 67
     * host-name : hb2-acfun-kcs318.aliyun
     * commentCount : 67
     * coverUrl : https://tx-free-imgs.acfun.cn/o_1eaor9h8n1qtj1ban9kr1k0371t0.jpeg?imageMogr2/format/webp/quality/75!/ignore-error/1
     * tagList : [{"name":"翻跳","id":"124"},{"name":"舞蹈","id":"204"},{"name":"宅舞","id":"117"},{"name":"lovepotion","id":"206054"}]
     * createTimeMillis : 1592136000000
     * danmakuCountShow : 31
     * createTime : 2020-6-14
     * superUbb : true
     * shareUrl : https://m.acfun.cn/v/?ac=16184862
     * user : {"followingCount":"3","isFollowing":false,"gender":0,"sexTrend":-1,"contributeCount":"6","headUrl":"https://tx-free-imgs.acfun.cn/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1","fanCount":"1721","headCdnUrls":[{"url":"https://tx-free-imgs.acfun.cn/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":true},{"url":"https://imgs.aixifan.com/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":false}],"isJoinUpCollege":true,"followingCountValue":3,"fanCountValue":1721,"contributeCountValue":6,"nameColor":0,"avatarFrame":396,"name":"网瘾少女喵桃桃","signature":"谢谢a站的大家！","id":"6704682","nameStyle":""}
     * status : 2
     * isFavorite : false
     * mkey : AAHewK3eIAAyMjAwMzE0ODgCARAAMEP1uwHbFLl%2BUAAAAC3nBbwhi6Rih5HxXF2lRMQrd6kkYPiitVBshm0RlT%2FBGDRW76zVsWqrJec%2BhuqIoeptjpSuLHhBC7Wu0J23xPjyF3E%2FAMoo0XB1g1IYlppy
     * priority : 0
     */

    private int currentVideoId;
    private boolean isLike;
    private double pctr;
    private int commentCountRealValue;
    private String bananaCountShow;
    private int stowCount;
    private String stowCountShow;
    private String giftPeachCountShow;
    private ChannelBean channel;
    private String description;
    private int likeCount;
    private String title;
    private boolean hasHotComment;
    private String shareCountShow;
    private int result;
    private int shareCount;
    private String picShareUrl;
    private int originalDeclare;
    private int danmakuCount;
    private boolean isThrowBanana;
    private int bananaCount;
    private int viewCount;
    private CurrentVideoInfoBean currentVideoInfo;
    private String likeCountShow;
    private int giftPeachCount;
    private String viewCountShow;
    private String dougaId;
    private boolean isRewardSupportted;
    private String commentCountShow;
    private int durationMillis;
    private String commentCountTenThousandShow;
    @SerializedName("host-name")
    private String hostname;
    private int commentCount;
    private String coverUrl;
    private long createTimeMillis;
    private String danmakuCountShow;
    private String createTime;
    private boolean superUbb;
    private String shareUrl;
    private UserBean user;
    private int status;
    private boolean isFavorite;
    private String mkey;
    private int priority;
    private List<VideoListBean> videoList;
    private List<CoverCdnUrlsBean> coverCdnUrls;
    private List<TagListBean> tagList;

    public int getCurrentVideoId() {
        return currentVideoId;
    }

    public void setCurrentVideoId(int currentVideoId) {
        this.currentVideoId = currentVideoId;
    }

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public double getPctr() {
        return pctr;
    }

    public void setPctr(double pctr) {
        this.pctr = pctr;
    }

    public int getCommentCountRealValue() {
        return commentCountRealValue;
    }

    public void setCommentCountRealValue(int commentCountRealValue) {
        this.commentCountRealValue = commentCountRealValue;
    }

    public String getBananaCountShow() {
        return bananaCountShow;
    }

    public void setBananaCountShow(String bananaCountShow) {
        this.bananaCountShow = bananaCountShow;
    }

    public int getStowCount() {
        return stowCount;
    }

    public void setStowCount(int stowCount) {
        this.stowCount = stowCount;
    }

    public String getStowCountShow() {
        return stowCountShow;
    }

    public void setStowCountShow(String stowCountShow) {
        this.stowCountShow = stowCountShow;
    }

    public String getGiftPeachCountShow() {
        return giftPeachCountShow;
    }

    public void setGiftPeachCountShow(String giftPeachCountShow) {
        this.giftPeachCountShow = giftPeachCountShow;
    }

    public ChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ChannelBean channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasHotComment() {
        return hasHotComment;
    }

    public void setHasHotComment(boolean hasHotComment) {
        this.hasHotComment = hasHotComment;
    }

    public String getShareCountShow() {
        return shareCountShow;
    }

    public void setShareCountShow(String shareCountShow) {
        this.shareCountShow = shareCountShow;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getPicShareUrl() {
        return picShareUrl;
    }

    public void setPicShareUrl(String picShareUrl) {
        this.picShareUrl = picShareUrl;
    }

    public int getOriginalDeclare() {
        return originalDeclare;
    }

    public void setOriginalDeclare(int originalDeclare) {
        this.originalDeclare = originalDeclare;
    }

    public int getDanmakuCount() {
        return danmakuCount;
    }

    public void setDanmakuCount(int danmakuCount) {
        this.danmakuCount = danmakuCount;
    }

    public boolean isIsThrowBanana() {
        return isThrowBanana;
    }

    public void setIsThrowBanana(boolean isThrowBanana) {
        this.isThrowBanana = isThrowBanana;
    }

    public int getBananaCount() {
        return bananaCount;
    }

    public void setBananaCount(int bananaCount) {
        this.bananaCount = bananaCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public CurrentVideoInfoBean getCurrentVideoInfo() {
        return currentVideoInfo;
    }

    public void setCurrentVideoInfo(CurrentVideoInfoBean currentVideoInfo) {
        this.currentVideoInfo = currentVideoInfo;
    }

    public String getLikeCountShow() {
        return likeCountShow;
    }

    public void setLikeCountShow(String likeCountShow) {
        this.likeCountShow = likeCountShow;
    }

    public int getGiftPeachCount() {
        return giftPeachCount;
    }

    public void setGiftPeachCount(int giftPeachCount) {
        this.giftPeachCount = giftPeachCount;
    }

    public String getViewCountShow() {
        return viewCountShow;
    }

    public void setViewCountShow(String viewCountShow) {
        this.viewCountShow = viewCountShow;
    }

    public String getDougaId() {
        return dougaId;
    }

    public void setDougaId(String dougaId) {
        this.dougaId = dougaId;
    }

    public boolean isIsRewardSupportted() {
        return isRewardSupportted;
    }

    public void setIsRewardSupportted(boolean isRewardSupportted) {
        this.isRewardSupportted = isRewardSupportted;
    }

    public String getCommentCountShow() {
        return commentCountShow;
    }

    public void setCommentCountShow(String commentCountShow) {
        this.commentCountShow = commentCountShow;
    }

    public int getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(int durationMillis) {
        this.durationMillis = durationMillis;
    }

    public String getCommentCountTenThousandShow() {
        return commentCountTenThousandShow;
    }

    public void setCommentCountTenThousandShow(String commentCountTenThousandShow) {
        this.commentCountTenThousandShow = commentCountTenThousandShow;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public long getCreateTimeMillis() {
        return createTimeMillis;
    }

    public void setCreateTimeMillis(long createTimeMillis) {
        this.createTimeMillis = createTimeMillis;
    }

    public String getDanmakuCountShow() {
        return danmakuCountShow;
    }

    public void setDanmakuCountShow(String danmakuCountShow) {
        this.danmakuCountShow = danmakuCountShow;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isSuperUbb() {
        return superUbb;
    }

    public void setSuperUbb(boolean superUbb) {
        this.superUbb = superUbb;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getMkey() {
        return mkey;
    }

    public void setMkey(String mkey) {
        this.mkey = mkey;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public List<CoverCdnUrlsBean> getCoverCdnUrls() {
        return coverCdnUrls;
    }

    public void setCoverCdnUrls(List<CoverCdnUrlsBean> coverCdnUrls) {
        this.coverCdnUrls = coverCdnUrls;
    }

    public List<TagListBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagListBean> tagList) {
        this.tagList = tagList;
    }

    public static class ChannelBean {
        /**
         * parentId : 123
         * parentName : 舞蹈·偶像
         * name : 宅舞
         * id : 134
         */

        private int parentId;
        private String parentName;
        private String name;
        private int id;

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class CurrentVideoInfoBean {
        /**
         * priority : 0
         * ksPlayJson : {"version":"1.0","type":"hls","adaptationSet":{"representation":[{"bandwidth":6881600,"codecs":"avc1.640028,mp4a.40.2","width":1920,"height":1080,"frameRate":25,"qualityType":"1080p+","qualityLabel":"1080P+","url":"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.m3u8?pkey=AAKkR99H0khHWhnDr03sy5a7-lvKFYn28moEcq2DUjrH4rSs44XZF06gvONkAiEFQQIFHu30BJ-3y9iKQH9TbjYe9fWM5OpgQpFgFtPjHUlqhbpPo-_AD00Gd9VfnttpC_7Z8W_4-HB7fbydtVLKmcMNEkdKWbL_A9ly1cnnzhW_spPnqwmDuUuJBtZxaTSY_hNeP5VUh53ThUUKoASGO7x9cgE3B6Lgrn_AVpDSr-eaqcT5yDtPpNsBs965wnczSaw&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr","backupUrl":["https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.m3u8?pkey=AAKkR99H0khHWhnDr03sy5a7-lvKFYn28moEcq2DUjrH4uPjbGF1mUYat7RMItsNBg7hjDdwf9mOTnA6vkqzb9VIxCwlVPNX1d1FDMfKy-D-PnynKUrSW5oLgQha7SOQQXXVuGTPs4q7KrRAZBgx5VVP5mHBR1JXjsEVypmLFnLDNQNB2gnFm29SQQvwQlbm-HuOTVmfd0QCrtZOPzC9fI0MRSEb7cWCTu6jWNmGd-I1ov5Oh1FkLsxtVWH8cuAL4-Q&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr"],"m3u8Slice":"#EXTM3U\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:5\n#EXT-X-MEDIA-SEQUENCE:0\n#EXTINF:5.0,\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/PetZmTa5H3x9UstA8S_XJ5EaJV7s6kipIyr3mfsqTz1rBPBEJZIUVnBAEhv6Ukmq.00000.ts?pkey=AAInQuqKAAklPUwp1yXCy8rQAvW313_x-K0HuEMh9-D_DtA8-kx-IqPfXTWQVkmtlkBHJAQSOnz6etejehWvADm8PoR7f_b2MEVR4_zQT3UVDNdv46lQXIZCHpNEvzqjvMQCf-m9pYTfZgIDbs5cj3rCepTyhlaJw7dCOWMH6bk6Yo7T65ovpno4CYt_152YEUADYdyWsqdX8yFtytw5flPeSvP4vDTa3xggBijKxQ5qhwjzb6tYZsUUXM52Y0FOe20&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\n","duration":209.16},{"bandwidth":3581600,"codecs":"avc1.640028,mp4a.40.2","width":1920,"height":1080,"frameRate":25,"qualityType":"1080p","qualityLabel":"1080P","url":"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.m3u8?pkey=AALouV_UGLcvM0EPyC5ErDs50k5Mu-9L0swfMXZ22gRxFMl7GI-VgyxR0c9P0K_Jy1OqyfyyyUthXTWoz4YX7J2EOuMo-LVxrBiXa_RkaPIbmb6anupegLyaQqmCsz5vEK2y8YSMxTERk2jSoxSFwlMVa0GGmZcix6XGg2dAAlm5PABzn3aItRdmfvxNsmzcSlZ3gCNvlRsQBtRBE3lt57tJdRp5HIx6TpuIg_AhaylZwof5YQxiPq9eljxLuzwdyW4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr","backupUrl":["https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.m3u8?pkey=AALouV_UGLcvM0EPyC5ErDs50k5Mu-9L0swfMXZ22gRxFF6WBNBiIiYmnDu7PxqTxX-DZHaf7mj4-pUO4f8re-LnEfbMasb5vbk5wPW4W-tjLhicTkhdUqZ6ut75wo5XjQRXWUxxB_G1YB35hyiOQuPY7Ng6Nd8wGiU3lF0RiEH93SvpYE-afeaw1BY_OtUQo_f9BkCovGahSUGiW9rGpAlN_x2RXOJaDEURNAMbxmYzId2PYrnqoG2POT9Clay7Twk&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr"],"m3u8Slice":"#EXTM3U\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:5\n#EXT-X-MEDIA-SEQUENCE:0\n#EXTINF:5.0,\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/THt_9XRZTDu2ZmBo0IEpt9wDMZPnz4PPQYwJKf8vNayJepCcTD54QeQdFy17PYSj.00000.ts?pkey=AAJ47w34rJ0ovYUVUoqHwdDnDU2zvV8r7FLNDDHCzikgB4dNEOC45V8vipks9vZTuwj_tmtFsLGCQFe_6UqmOJOleGLmaXN_sCfr2--SjyTM7rn0HNlp6_TSJHgU--vdSa83tk87Z0BOx0_vbQnD2EMEr88oIC_aW9vnNiXO9yEPzeLZ_v3h7R6Me5CWOPVjAnqPB0GI9lpUORBE1Cr4W1wR6x1a42Vp6L5BampPrwuxEsJrv_nNUC50Fb1ssPwHaj0&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\n","duration":209.16},{"bandwidth":2340800,"codecs":"avc1.64001f,mp4a.40.2","width":1280,"height":720,"frameRate":25,"qualityType":"720p","qualityLabel":"超清","url":"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.m3u8?pkey=AAKgdQPPZEUdS_XUWCIapogJ2WeV88ZS3vs83hrTQ6FtnUvYCkUwCC02lYY76SNMHHiN3GeCwJGfgYk78UxHkBsMj0WHw4WNQqBmIQptMw28d1rETRDETl4jCHvqqzSD_-AxGluGpTTM9Wgqf8BjbwRUMB-iM598k_OL1XOt6noW9_FiFF3VFtqaeAWhZxJTB0RaozSXig-_IQ4mfFkierjXjBMWy92O9gXzCa2cZOO6yQrgM3FM_D410Pv0BIgPAXk&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr","backupUrl":["https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.m3u8?pkey=AAKgdQPPZEUdS_XUWCIapogJ2WeV88ZS3vs83hrTQ6FtnQ0vt1rZ-hyyT_p6SD1UwAtcvczUFaAqO3EiY8VnUvG3ZK02vNY0E9qTH6yEaHT2sucwHGNJkY7pbSHQyG9wi9vPQFiHIDaMgkDlDxD6i4zas_7ZsHrlFUZHxR8FZfmvQN0VQpYnjwqScvK06mYrmcbN9h8QRukLJNSQJ8kCr_1u2QDqY2dmEeMcD_0RNP_BXnupaStGK8LjO4lHYAKeEcM&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr"],"m3u8Slice":"#EXTM3U\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:5\n#EXT-X-MEDIA-SEQUENCE:0\n#EXTINF:5.0,\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3Cwomn_2tjVlLzkcsehbz0CAQNYUOfYBcdHDnFLGPSYZOp7UZ.00000.ts?pkey=AAKG9SY9Hr_DIo6-bbTFsPINedv89Nf54R-Qg6vLORLduLldolVva3c2bfFn-6qQ9vCWbo4ovwj3LV-fvvaEpl0RDgU2PnsMdqNUEse7lzUy7duyFjyf1tEkDPk8Qk-b4jwdYVnrHoCF_irK1EBqcI98ECQA_XBEfCZZRESUr4JjV1c_a53h1e1OnZJSssTbAIjIO8yKKK3wWsDwwDiNI9A1JnLFLSl38sb_5zmqiOaR4FOBs2JyWay6ymVTMw1NtxI&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\n","duration":209.16,"defaultSelect":true},{"bandwidth":1060400,"codecs":"avc1.64001f,mp4a.40.2","width":960,"height":540,"frameRate":25,"qualityType":"540p","qualityLabel":"高清","url":"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.m3u8?pkey=AAJiBzh4HOVnqpECnXY1STRsH58p7Bm_OHe58c_7cYT15DLEqTEDGenXJcfW1uHA2epOcM5jB7vpYng2IfoJ9H32fxruP4mhSUyIklpuxfk5vdF7k0tO1gzUsE0QrP60kuaBOdMJBExth67UcKUw5H9ZX7B9pHsJIsm0ZzmBf4vbtkZXti_Ceaj1OZ07FCDuadANbtIgNfi8Rrq5UJT2UvLn-raMuMPZbVQkE0n8Uy67sQt2oDS3EKb7Mksql4c9rYI&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr","backupUrl":["https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.m3u8?pkey=AAJiBzh4HOVnqpECnXY1STRsH58p7Bm_OHe58c_7cYT15PtpfaJuP0HiuHyYJuiAXtRTabjX-O8MvwOxjsnjFvt4LF4qxcJoN-hsmzi3pqI8RIq3HiPnyyJV3WpGTpHH6GI1WtR_uIT7RFRFwJ_SPThRpv4FUS42KQpbD4sxYQArG3h_EGCJB03eXb4aBjouoXpzuHZDZehFqTam8_102Bfg-lozaqvsWbfM94oprfibAm944wzdoZETynbks48vGW8&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr"],"m3u8Slice":"#EXTM3U\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:5\n#EXT-X-MEDIA-SEQUENCE:0\n#EXTINF:5.0,\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnwRSCNP5N-dDTjhUnoWHXFLpDai2ljazFxinInuENMEQ.00000.ts?pkey=AAJt3Xt49PbOZHy43HkH8u6JaRw9_K57q--zK2RDEkEL8rxo3LKybi0NWQXamJfMy-zDp-FYEljDbUChR0Cf1xxHNKktpGGUptDK2K0Z-6IE-vLGta8Z4QY2e-mu9TAgo1dY_ByrXUM0LogF9GnskQANGLM-QWfxgSu9nXhw8UJjWWxLEz5tkfvCrJo1-evtJgocsuglcg4Tq_rbGGnzK7oPm2j_LDCKBJzEas8U_awNZePnM8sEtAxIXFLHVcHIrW4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\n","duration":209.16},{"bandwidth":620400,"codecs":"avc1.64001e,mp4a.40.2","width":640,"height":360,"frameRate":25,"qualityType":"360p","qualityLabel":"标清","url":"https://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.m3u8?pkey=AAJDMMZ-KSQrtF2UTMT4qaLQGnnJ5xfCYKE-YqHbOXhYtnYaw5fwIuZJdNi96ClYLLhiSOYxI8QnXRMwL_EydpwMygi0cu7Ekr_cHu13kBnoVKEhupIdIGzoCC4d3JgomQM-J8Sh0nScVCYeoCr8uL9sodED7Ckl2KB_EX-rqmDcoHapUG511Fl_K2awcZY4oEhgBxiyE6qtEbApF9SJrdGcb9f2wWcZ6TC_kxocoI7omBZ_lLQQOSeIZ5C_rui0hRE&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr","backupUrl":["https://ali-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.m3u8?pkey=AAJDMMZ-KSQrtF2UTMT4qaLQGnnJ5xfCYKE-YqHbOXhYtsG1vFFsvP3wE-meVvvlHF0-h1V4eaeimYSaYxbOZg041w7ulMjAcdNaeRb9TUC_QpkXyb-Eepo_VvFd4cnOqNmbUH66hbamIgxlyK9y5AmoUhubi2EKxd6bT2KvofuMzzpOWq7nLiA4V92rEidDxTvtajN-vtqJyta-ajjaLqeLwipsLCvhCHviPc6hCur19YBH3HmzCoSSf4m9OENXWh4&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr"],"m3u8Slice":"#EXTM3U\n#EXT-X-VERSION:4\n#EXT-X-TARGETDURATION:5\n#EXT-X-MEDIA-SEQUENCE:0\n#EXTINF:5.0,\nhttps://tx-safety-video.acfun.cn/mediacloud/acfun/acfun_video/hls/ZKDHCikHDAzg_TC3CwomnyZSm4VSS1HD7DjPSY9D0yUOl4A73rPTVlwQljqbzCJw.00000.ts?pkey=AAKKn_-G3bSTl-0nKCfEi2CJMoK6pcc6asIMeNISZFY4wp572SFMpPKYxY8-fFw-ONAA5hPrPqhbdOkdXjygZ1r8mFA19O5abOkh3ja4h-g_7l2h9wxc1NQJJQt_uIfheFT1oKwx0zGeJ5-vpXHmmbvFEHZB-EXkG-sPklIrbdGQvilUGBtljZV-sFeEV4XUebjAKWx5WxRzco9z8xGUsou0wpC6_Ko9KjJqUoIfk0UmvvoRMA6z0GFyd6WDgQg-_3Y&safety_id=AAFXBpQDk+c9u8Rr94zuWjGr\n","duration":209.16}]}}
         * visibleType : 1
         * title : LP
         * danmakuCount : 31
         * sourceStatus : 3
         * durationMillis : 209211
         * sizeType : 1
         * danmakuCountShow : 31
         * uploadTime : 1592119345853
         * fileName : LP
         * id : 13271875
         */

        private int priority;
        private String ksPlayJson;
        private int visibleType;
        private String title;
        private int danmakuCount;
        private int sourceStatus;
        private int durationMillis;
        private int sizeType;
        private String danmakuCountShow;
        private long uploadTime;
        private String fileName;
        private String id;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getKsPlayJson() {
            return ksPlayJson;
        }

        public void setKsPlayJson(String ksPlayJson) {
            this.ksPlayJson = ksPlayJson;
        }

        public int getVisibleType() {
            return visibleType;
        }

        public void setVisibleType(int visibleType) {
            this.visibleType = visibleType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDanmakuCount() {
            return danmakuCount;
        }

        public void setDanmakuCount(int danmakuCount) {
            this.danmakuCount = danmakuCount;
        }

        public int getSourceStatus() {
            return sourceStatus;
        }

        public void setSourceStatus(int sourceStatus) {
            this.sourceStatus = sourceStatus;
        }

        public int getDurationMillis() {
            return durationMillis;
        }

        public void setDurationMillis(int durationMillis) {
            this.durationMillis = durationMillis;
        }

        public int getSizeType() {
            return sizeType;
        }

        public void setSizeType(int sizeType) {
            this.sizeType = sizeType;
        }

        public String getDanmakuCountShow() {
            return danmakuCountShow;
        }

        public void setDanmakuCountShow(String danmakuCountShow) {
            this.danmakuCountShow = danmakuCountShow;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class UserBean {
        /**
         * followingCount : 3
         * isFollowing : false
         * gender : 0
         * sexTrend : -1
         * contributeCount : 6
         * headUrl : https://tx-free-imgs.acfun.cn/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1
         * fanCount : 1721
         * headCdnUrls : [{"url":"https://tx-free-imgs.acfun.cn/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":true},{"url":"https://imgs.aixifan.com/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1","freeTrafficCdn":false}]
         * isJoinUpCollege : true
         * followingCountValue : 3
         * fanCountValue : 1721
         * contributeCountValue : 6
         * nameColor : 0
         * avatarFrame : 396
         * name : 网瘾少女喵桃桃
         * signature : 谢谢a站的大家！
         * id : 6704682
         * nameStyle :
         */

        private String followingCount;
        private boolean isFollowing;
        private int gender;
        private int sexTrend;
        private String contributeCount;
        private String headUrl;
        private String fanCount;
        private boolean isJoinUpCollege;
        private int followingCountValue;
        private int fanCountValue;
        private int contributeCountValue;
        private int nameColor;
        private int avatarFrame;
        private String name;
        private String signature;
        private String id;
        private String nameStyle;
        private List<HeadCdnUrlsBean> headCdnUrls;

        public String getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(String followingCount) {
            this.followingCount = followingCount;
        }

        public boolean isIsFollowing() {
            return isFollowing;
        }

        public void setIsFollowing(boolean isFollowing) {
            this.isFollowing = isFollowing;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getSexTrend() {
            return sexTrend;
        }

        public void setSexTrend(int sexTrend) {
            this.sexTrend = sexTrend;
        }

        public String getContributeCount() {
            return contributeCount;
        }

        public void setContributeCount(String contributeCount) {
            this.contributeCount = contributeCount;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getFanCount() {
            return fanCount;
        }

        public void setFanCount(String fanCount) {
            this.fanCount = fanCount;
        }

        public boolean isIsJoinUpCollege() {
            return isJoinUpCollege;
        }

        public void setIsJoinUpCollege(boolean isJoinUpCollege) {
            this.isJoinUpCollege = isJoinUpCollege;
        }

        public int getFollowingCountValue() {
            return followingCountValue;
        }

        public void setFollowingCountValue(int followingCountValue) {
            this.followingCountValue = followingCountValue;
        }

        public int getFanCountValue() {
            return fanCountValue;
        }

        public void setFanCountValue(int fanCountValue) {
            this.fanCountValue = fanCountValue;
        }

        public int getContributeCountValue() {
            return contributeCountValue;
        }

        public void setContributeCountValue(int contributeCountValue) {
            this.contributeCountValue = contributeCountValue;
        }

        public int getNameColor() {
            return nameColor;
        }

        public void setNameColor(int nameColor) {
            this.nameColor = nameColor;
        }

        public int getAvatarFrame() {
            return avatarFrame;
        }

        public void setAvatarFrame(int avatarFrame) {
            this.avatarFrame = avatarFrame;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNameStyle() {
            return nameStyle;
        }

        public void setNameStyle(String nameStyle) {
            this.nameStyle = nameStyle;
        }

        public List<HeadCdnUrlsBean> getHeadCdnUrls() {
            return headCdnUrls;
        }

        public void setHeadCdnUrls(List<HeadCdnUrlsBean> headCdnUrls) {
            this.headCdnUrls = headCdnUrls;
        }

        public static class HeadCdnUrlsBean {
            /**
             * url : https://tx-free-imgs.acfun.cn/Fls44l1Ycp_2WVkBJhCc2lgJZpUf?imageMogr2/format/webp/quality/75!/ignore-error/1
             * freeTrafficCdn : true
             */

            private String url;
            private boolean freeTrafficCdn;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public boolean isFreeTrafficCdn() {
                return freeTrafficCdn;
            }

            public void setFreeTrafficCdn(boolean freeTrafficCdn) {
                this.freeTrafficCdn = freeTrafficCdn;
            }
        }
    }

    public static class VideoListBean {
        /**
         * priority : 0
         * visibleType : 1
         * title : LP
         * danmakuCount : 31
         * sourceStatus : 3
         * durationMillis : 209211
         * sizeType : 1
         * danmakuCountShow : 31
         * uploadTime : 1592119345853
         * fileName : LP
         * id : 13271875
         */

        private int priority;
        private int visibleType;
        private String title;
        private int danmakuCount;
        private int sourceStatus;
        private int durationMillis;
        private int sizeType;
        private String danmakuCountShow;
        private long uploadTime;
        private String fileName;
        private String id;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getVisibleType() {
            return visibleType;
        }

        public void setVisibleType(int visibleType) {
            this.visibleType = visibleType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getDanmakuCount() {
            return danmakuCount;
        }

        public void setDanmakuCount(int danmakuCount) {
            this.danmakuCount = danmakuCount;
        }

        public int getSourceStatus() {
            return sourceStatus;
        }

        public void setSourceStatus(int sourceStatus) {
            this.sourceStatus = sourceStatus;
        }

        public int getDurationMillis() {
            return durationMillis;
        }

        public void setDurationMillis(int durationMillis) {
            this.durationMillis = durationMillis;
        }

        public int getSizeType() {
            return sizeType;
        }

        public void setSizeType(int sizeType) {
            this.sizeType = sizeType;
        }

        public String getDanmakuCountShow() {
            return danmakuCountShow;
        }

        public void setDanmakuCountShow(String danmakuCountShow) {
            this.danmakuCountShow = danmakuCountShow;
        }

        public long getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(long uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class CoverCdnUrlsBean {
        /**
         * url : https://tx-free-imgs.acfun.cn/o_1eaor9h8n1qtj1ban9kr1k0371t0.jpeg?imageMogr2/format/webp/quality/75!/ignore-error/1
         * freeTrafficCdn : true
         */

        private String url;
        private boolean freeTrafficCdn;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isFreeTrafficCdn() {
            return freeTrafficCdn;
        }

        public void setFreeTrafficCdn(boolean freeTrafficCdn) {
            this.freeTrafficCdn = freeTrafficCdn;
        }
    }

    public static class TagListBean {
        /**
         * name : 翻跳
         * id : 124
         */

        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
