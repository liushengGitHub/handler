package liusheng.handler.youtube.handler;

import liusheng.handler.youtube.handler.entity.Format;
import liusheng.handler.youtube.handler.entity.VideoDetails;
import liusheng.handler.youtube.handler.entity.VideoMeta;
import liusheng.handler.youtube.handler.entity.YtFile;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 年: 2020  月: 03 日: 28 小时: 17 分钟: 49
 * 用户名: LiuSheng
 */

public abstract class AbstractYouTubeExtractor {

    private final static boolean CACHING = true;

    static boolean LOGGING = false;

    private final static String CACHE_FILE_NAME = "decipher_js_funct";

    /*  private WeakReference<Context> refContext;*/
    private String videoID;
    private VideoMeta videoMeta;
    private boolean includeWebM = true;
    private boolean useHttp = false;
    private String cacheDirPath;

    private volatile String decipheredSignature;

    private static String decipherJsFileName;
    private static String decipherFunctions;
    private static String decipherFunctionName;


    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36";

    private static final Pattern patYouTubePageLink = Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)( |\\z|&)");
    private static final Pattern patYouTubeShortLink = Pattern.compile("(http|https)://(www\\.|)youtu.be/(.+?)( |\\z|&)");

    private static final Pattern patTitle = Pattern.compile("\"title\"\\s*:\\s*\"(.*?)\"");
    private static final Pattern patAuthor = Pattern.compile("\"author\"\\s*:\\s*\"(.+?)\"");
    private static final Pattern patChannelId = Pattern.compile("\"channelId\"\\s*:\\s*\"(.+?)\"");
    private static final Pattern patLength = Pattern.compile("\"lengthSeconds\"\\s*:\\s*\"(\\d+?)\"");
    private static final Pattern patViewCount = Pattern.compile("\"viewCount\"\\s*:\\s*\"(\\d+?)\"");
    private static final Pattern patShortDescript = Pattern.compile("\"shortDescription\"\\s*:\\s*\"(.+?)\"");
    private static final Pattern patStatusOk = Pattern.compile("status=ok(&|,|\\z)");

    private static final Pattern patHlsvp = Pattern.compile("hlsvp=(.+?)(&|\\z)");
    private static final Pattern patHlsItag = Pattern.compile("/itag/(\\d+?)/");

    private static final Pattern patItag = Pattern.compile("itag=([0-9]+?)(&|\\z)");
    private static final Pattern patEncSig = Pattern.compile("s=(.{10,}?)(\\\\\\\\u0026|\\z)");
    private static final Pattern patUrl = Pattern.compile("\"url\"\\s*:\\s*\"(.+?)\"");
    private static final Pattern patCipher = Pattern.compile("\"(signatureCipher|cipher)\"\\s*:\\s*\"(.+?)\"");
    private static final Pattern patCipherUrl = Pattern.compile("url=(.+?)(\\\\\\\\u0026|\\z)");
    private static final Pattern patYtPlayer = Pattern.compile("<\\s*script\\s*>((.+?)\\\\/s\\\\/player\\\\/(.+?)\\\\/(player(_ias)?.+?.js)(.+?))</\\s*script\\s*>");

    /**
     *
     * sp=sig\\u0026s=5O5Oq0QJ8wRAIhAN9IOZXWzLX-4nzO6ef2K_UBgo8TlJyyq_fhuqcevY4nAh9KlLHLar4g-AvRZYg_R8YwErNsAk7R4hnKeYwHa9Qz9Qz\\u0026url=https%3A%2F%2Fr6---sn-3pm7sn7r.googlevideo.com%2Fvideoplayback%3Fexpire%3D1590409092%26ei%3DJGPLXsH7EOKpigb6_ZvoAQ%26ip%3D13.125.200.246%26id%3Do-AOpjZVVMrn47onWkKKCKp_wU82DxMD5pL2UAcGQzyY4q%26itag%3D18%26source%3Dyoutube%26requiressl%3Dyes%26mh%3DL7%26mm%3D31%252C29%26mn%3Dsn-3pm7sn7r%252Csn-3pm76n7r%26ms%3Dau%252Crdu%26mv%3Dm%26mvi%3D5%26pl%3D21%26initcwndbps%3D496250%26vprv%3D1%26mime%3Dvideo%252Fmp4%26gir%3Dyes%26clen%3D16096411%26ratebypass%3Dyes%26dur%3D181.556%26lmt%3D1576223074616082%26mt%3D1590387391%26fvip%3D6%26c%3DWEB%26txp%3D2311222%26sparams%3Dexpire%252Cei%252Cip%252Cid%252Citag%252Csource%252Crequiressl%252Cvprv%252Cmime%252Cgir%252Cclen%252Cratebypass%252Cdur%252Clmt%26lsparams%3Dmh%252Cmm%252Cmn%252Cms%252Cmv%252Cmvi%252Cpl%252Cinitcwndbps%26lsig%3DAG3C_xAwRgIhANu1G80L60sHvWGRMhihWNKQkYoqWPU2xZubkefVoSNvAiEAmIH11YeyBq5iif4KtyIxtuHVOFuDF5KtAPx7UepBy3A%253D
     * s=vOvOq0QJ8wRQIgDbjss9WYPW6vQeMtNS4rmqD%3DbHoPpntPJqS4_oW7TvACIQCmrxolP9bN6AFewvg6vhU4t5uSY9ae--YNQr_Ghu4-qg%3Dgg%3Dg\\u0026sp=sig\\u0026url=https%3A%2F%2Fr6---sn-3pm76n7r.googlevideo.com%2Fvideoplayback%3Fexpire%3D1590408811%26ei%3DC2LLXuedNsiLlQSL7pGgCA%26ip%3D13.125.200.246%26id%3Do-AGHxqr9v0Jcf5XRLyVuskhmjw8_U9OS6SnKdNCksh9pq%26itag%3D18%26source%3Dyoutube%26requiressl%3Dyes%26mh%3DL7%26mm%3D31%252C29%26mn%3Dsn-3pm76n7r%252Csn-3pm7sn7r%26ms%3Dau%252Crdu%26mv%3Dm%26mvi%3D5%26pl%3D21%26initcwndbps%3D767500%26vprv%3D1%26mime%3Dvideo%252Fmp4%26gir%3Dyes%26clen%3D16096411%26ratebypass%3Dyes%26dur%3D181.556%26lmt%3D1576223074616082%26mt%3D1590387094%26fvip%3D6%26c%3DWEB%26txp%3D2311222%26sparams%3Dexpire%252Cei%252Cip%252Cid%252Citag%252Csource%252Crequiressl%252Cvprv%252Cmime%252Cgir%252Cclen%252Cratebypass%252Cdur%252Clmt%26lsparams%3Dmh%252Cmm%252Cmn%252Cms%252Cmv%252Cmvi%252Cpl%252Cinitcwndbps%26lsig%3DAG3C_xAwRgIhANiBdlvZB9-EFh6zkPS3liE6QypogxSilsNS2mD7cE-yAiEAvdEch9d9hpE60xAxRNCYK-s5XfcXuVXCI6MGALCRU-Q%253D\
     */
    private static final Pattern patVariableFunction = Pattern.compile("([{; =])([a-zA-Z$][a-zA-Z0-9$]{0,2})\\.([a-zA-Z$][a-zA-Z0-9$]{0,2})\\(");
    private static final Pattern patFunction = Pattern.compile("([{; =])([a-zA-Z$_][a-zA-Z0-9$]{0,2})\\(");

    private static final Pattern patDecryptionJsFile = Pattern.compile("s\\\\/player\\\\/[\\da-zA-Z]+\\\\/(player(_ias)?.+?\\.js)");
    private static final Pattern patSignatureDecFunction = Pattern.compile("\\b([\\w$]{2})\\s*=\\s*function\\((\\w+)\\)\\{\\s*\\2=\\s*\\2\\.split\\(\"\"\\)\\s*;");

    private static final Map<Integer, Format> FORMAT_MAP = new HashMap<>();

    static {
        // http://en.wikipedia.org/wiki/YouTube#Quality_and_formats

        // Video and Audio
        FORMAT_MAP.put(17, new Format(17, "3gp", 144, Format.VCodec.MPEG4, Format.ACodec.AAC, 24, false));
        FORMAT_MAP.put(36, new Format(36, "3gp", 240, Format.VCodec.MPEG4, Format.ACodec.AAC, 32, false));
        FORMAT_MAP.put(5, new Format(5, "flv", 240, Format.VCodec.H263, Format.ACodec.MP3, 64, false));
        FORMAT_MAP.put(43, new Format(43, "webm", 360, Format.VCodec.VP8, Format.ACodec.VORBIS, 128, false));
        FORMAT_MAP.put(18, new Format(18, "mp4", 360, Format.VCodec.H264, Format.ACodec.AAC, 96, false));
        FORMAT_MAP.put(22, new Format(22, "mp4", 720, Format.VCodec.H264, Format.ACodec.AAC, 192, false));

        // Dash Video
        FORMAT_MAP.put(160, new Format(160, "mp4", 144, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(133, new Format(133, "mp4", 240, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(134, new Format(134, "mp4", 360, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(135, new Format(135, "mp4", 480, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(136, new Format(136, "mp4", 720, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(137, new Format(137, "mp4", 1080, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(264, new Format(264, "mp4", 1440, Format.VCodec.H264, Format.ACodec.NONE, true));
        FORMAT_MAP.put(266, new Format(266, "mp4", 2160, Format.VCodec.H264, Format.ACodec.NONE, true));

        FORMAT_MAP.put(298, new Format(298, "mp4", 720, Format.VCodec.H264, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(299, new Format(299, "mp4", 1080, Format.VCodec.H264, 60, Format.ACodec.NONE, true));

        // Dash Audio
        FORMAT_MAP.put(140, new Format(140, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 128, true));
        FORMAT_MAP.put(141, new Format(141, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 256, true));
        FORMAT_MAP.put(256, new Format(256, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 192, true));
        FORMAT_MAP.put(258, new Format(258, "m4a", Format.VCodec.NONE, Format.ACodec.AAC, 384, true));

        // WEBM Dash Video
        FORMAT_MAP.put(278, new Format(278, "webm", 144, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(242, new Format(242, "webm", 240, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(243, new Format(243, "webm", 360, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(244, new Format(244, "webm", 480, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(247, new Format(247, "webm", 720, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(248, new Format(248, "webm", 1080, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(271, new Format(271, "webm", 1440, Format.VCodec.VP9, Format.ACodec.NONE, true));
        FORMAT_MAP.put(313, new Format(313, "webm", 2160, Format.VCodec.VP9, Format.ACodec.NONE, true));

        FORMAT_MAP.put(302, new Format(302, "webm", 720, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(308, new Format(308, "webm", 1440, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(303, new Format(303, "webm", 1080, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));
        FORMAT_MAP.put(315, new Format(315, "webm", 2160, Format.VCodec.VP9, 60, Format.ACodec.NONE, true));

        // WEBM Dash Audio
        FORMAT_MAP.put(171, new Format(171, "webm", Format.VCodec.NONE, Format.ACodec.VORBIS, 128, true));

        FORMAT_MAP.put(249, new Format(249, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 48, true));
        FORMAT_MAP.put(250, new Format(250, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 64, true));
        FORMAT_MAP.put(251, new Format(251, "webm", Format.VCodec.NONE, Format.ACodec.OPUS, 160, true));

        // HLS Live Stream
        FORMAT_MAP.put(91, new Format(91, "mp4", 144, Format.VCodec.H264, Format.ACodec.AAC, 48, false, true));
        FORMAT_MAP.put(92, new Format(92, "mp4", 240, Format.VCodec.H264, Format.ACodec.AAC, 48, false, true));
        FORMAT_MAP.put(93, new Format(93, "mp4", 360, Format.VCodec.H264, Format.ACodec.AAC, 128, false, true));
        FORMAT_MAP.put(94, new Format(94, "mp4", 480, Format.VCodec.H264, Format.ACodec.AAC, 128, false, true));
        FORMAT_MAP.put(95, new Format(95, "mp4", 720, Format.VCodec.H264, Format.ACodec.AAC, 256, false, true));
        FORMAT_MAP.put(96, new Format(96, "mp4", 1080, Format.VCodec.H264, Format.ACodec.AAC, 256, false, true));
    }

    private String responseStr = "";

    public String getResponseStr() {
        return responseStr;
    }

    public AbstractYouTubeExtractor() {
        cacheDirPath = "app";
    }


    /**
     * Start the extraction.
     *
     * @param youtubeLink the youtube page link or video id
     * @return
     */
    public VideoDetails extract(String youtubeLink) {
        Map<Integer, YtFile> fileMap = this.getYoutubeFileMap(youtubeLink);
      /*  if(fileMap == null) {
            System.out.println(youtubeLink);
        }*/
        VideoDetails videoDetails = new VideoDetails();
        videoDetails.setVideoMeta(videoMeta);
        videoDetails.setYtFileMap(fileMap);
        return videoDetails;
    }


    private Map<Integer, YtFile> getYoutubeFileMap(String... params) {
        videoID = null;
        String ytUrl = params[0];
        if (ytUrl == null) {
            return null;
        }
        Matcher mat = patYouTubePageLink.matcher(ytUrl);
        if (mat.find()) {
            videoID = mat.group(3);
        } else {
            mat = patYouTubeShortLink.matcher(ytUrl);
            if (mat.find()) {
                videoID = mat.group(3);
            } else if (ytUrl.matches("\\p{Graph}+?")) {
                videoID = ytUrl;
            }
        }
        if (videoID != null) {
            try {
                return getStreamUrls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong YouTube link format");
        }
        return null;
    }

    private Map<Integer, YtFile> getStreamUrls() throws IOException, InterruptedException {

        String ytInfoUrl = (useHttp) ? "http://" : "https://";
        ytInfoUrl += "www.youtube.com/get_video_info?video_id=" + videoID + "&eurl="
                + URLEncoder.encode("https://youtube.googleapis.com/v/" + videoID, "UTF-8");

        String streamMap;

        try {
            streamMap = getVideoInfoLine(ytInfoUrl);
        } finally {

        }
        Matcher mat;
        String curJsFileName;
        Map<Integer, String> encSignatures = null;

        streamMap = URLDecoder.decode(streamMap, "UTF-8");
        streamMap = streamMap.replace("\\u0026", "&");

        parseVideoMeta(streamMap);

        if (videoMeta.isLiveStream()) {
            mat = patHlsvp.matcher(streamMap);
            if (mat.find()) {
                String hlsvp = URLDecoder.decode(mat.group(1), "UTF-8");
                Map<Integer, YtFile> ytFiles = new HashMap<>();

                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(getAllLine(hlsvp));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("https://") || line.startsWith("http://")) {
                            mat = patHlsItag.matcher(line);
                            if (mat.find()) {
                                int itag = Integer.parseInt(mat.group(1));
                                YtFile newFile = new YtFile(FORMAT_MAP.get(itag), line);
                                ytFiles.put(itag, newFile);
                            }
                        }
                    }
                } finally {
                    if (Objects.nonNull(reader))
                    reader.close();
                    //urlConnection.disconnect();
                }

                if (ytFiles.size() == 0) {
                    if (LOGGING)
                        System.out.println(streamMap);
                    return null;
                }
                return ytFiles;
            }
            return null;
        }

        // "use_cipher_signature" disappeared, we check whether at least one ciphered signature
        // exists int the stream_map.
        boolean sigEnc = true, statusFail = false;
        if (!patCipher.matcher(streamMap).find()) {
            sigEnc = false;
            if (!patStatusOk.matcher(streamMap).find()) {
                statusFail = true;
            }
        }

        // Some videos are using a ciphered signature we need to get the
        // deciphering js-file from the youtubepage.
        if (sigEnc || statusFail) {
            // Get the video directly from the youtubepage
            if (CACHING
                    && (decipherJsFileName == null || decipherFunctions == null || decipherFunctionName == null)) {
                readDecipherFunctFromCache();
            }
            if (LOGGING)
                System.out.println("Get from youtube page");


            String url = "https://youtube.com/watch?v=" + videoID;
           /* urlConnection = (HttpURLConnection) getUrl.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);*/
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(getAllLineFromCache(url));
                String line;
                boolean player_api = false;
                while ((line = reader.readLine()) != null) {
                    // Log.d("line", line);
                    if (line.contains("id=\"player-api\"")) {
                        player_api = true;
                        continue;
                    }
                    mat = patYtPlayer.matcher(line);

                    if (mat.find() && player_api) {
                        streamMap = line.replace("\\\"", "\"");
                        break;
                    }
                }
            } finally {
                if (Objects.nonNull(reader))
                reader.close();
                //urlConnection.disconnect();
            }
            encSignatures = new HashMap<>();

            mat = patDecryptionJsFile.matcher(streamMap);
            if (mat.find()) {
                curJsFileName = mat.group(0).replace("\\/", "/");
                if (mat.group(2) != null)
                    curJsFileName.replace(mat.group(2), "");
                if (decipherJsFileName == null || !decipherJsFileName.equals(curJsFileName)) {
                    decipherFunctions = null;
                    decipherFunctionName = null;
                }
                decipherJsFileName = curJsFileName;
            }
        }

        Map<Integer, YtFile> ytFiles = new HashMap<>();

        if (sigEnc) {
            mat = patCipher.matcher(streamMap);
        } else {
            mat = patUrl.matcher(streamMap);
        }

        while (mat.find()) {
            String sig = null;
            String url;
            if (sigEnc) {
                String cipher = mat.group(2);
                if (cipher == null) {
                    System.out.println(cipher);
                }
                Matcher mat2 = patCipherUrl.matcher(cipher);
                if (mat2.find()) {
                    url = URLDecoder.decode(mat2.group(1), "UTF-8");
                    mat2 = patEncSig.matcher(cipher);
                    if (mat2.find()) {
                        sig = URLDecoder.decode(mat2.group(1), "UTF-8");
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } else {
                url = mat.group(1);
            }

            Matcher mat2 = patItag.matcher(url);
            if (!mat2.find())
                continue;

            int itag = Integer.parseInt(mat2.group(1));

            if (FORMAT_MAP.get(itag) == null) {
                if (LOGGING)
                    System.out.println("Itag not in list:" + itag);
                continue;
            } else if (!includeWebM && FORMAT_MAP.get(itag).getExt().equals("webm")) {
                continue;
            }

            // Unsupported
            if (url.contains("&source=yt_otf&"))
                continue;

            if (LOGGING)
                System.out.println("Itag found:" + itag);

            if (sig != null) {
                encSignatures.put(itag, sig);
            }

            Format format = FORMAT_MAP.get(itag);
            YtFile newVideo = new YtFile(format, url);
            ytFiles.put(itag, newVideo);
        }

        if (encSignatures != null) {

            if (LOGGING)
                System.out.println("Decipher signatures: " + encSignatures.size() + ", videos: " + ytFiles.size());
            String signature;
            decipheredSignature = null;
            decipherSignature(encSignatures);
            signature = decipheredSignature;
            if (signature == null) {
                return null;
            } else {
                String[] sigs = signature.split("\n");
                ArrayList<Map.Entry<Integer, String>> entries = new ArrayList<>(encSignatures.entrySet());
                for (int i = 0; i < entries.size() && i < sigs.length; i++) {
                    Map.Entry<Integer, String> entry = entries.get(i);
                    Integer key = entry.getKey();
                    String url = ytFiles.get(key).getUrl();
                    url += "&sig=" + sigs[i];
                    YtFile newFile = new YtFile(FORMAT_MAP.get(key), url);
                    ytFiles.put(key, newFile);
                }
            }
        }

        if (ytFiles.size() == 0) {
            if (LOGGING)
                System.out.println(streamMap);
            return null;
        }
        return ytFiles;
    }

    protected abstract Reader getAllLineFromCache(String url) throws IOException;

    protected abstract Reader getAllLine(String hlsvp) throws IOException;

    protected abstract String getVideoInfoLine(String ytInfoUrl) throws IOException;

    private boolean decipherSignature(final Map<Integer, String> encSignatures) throws IOException {
        // Assume the functions don't change that much
        if (decipherFunctionName == null || decipherFunctions == null) {
            String decipherFunctUrl = /*"https://s.ytimg.com/yts/jsbin/"*/
                    "https://www.youtube.com/" + decipherJsFileName;

            BufferedReader reader = null;
            String javascriptFile;
            URL url = new URL(decipherFunctUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("User-Agent", USER_AGENT);
            try {
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder("");
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(" ");
                }
                javascriptFile = sb.toString();
            } finally {
                if (reader != null)
                    reader.close();
                urlConnection.disconnect();
            }

            if (LOGGING)
                System.out.println("Decipher FunctURL: " + decipherFunctUrl);
            Matcher mat = patSignatureDecFunction.matcher(javascriptFile);
            if (mat.find()) {
                decipherFunctionName = mat.group(1);
                if (LOGGING)
                    System.out.println("Decipher Functname: " + decipherFunctionName);

                Pattern patMainVariable = Pattern.compile("(var |\\s|,|;)" + decipherFunctionName.replace("$", "\\$") +
                        "(=function\\((.{1,3})\\)\\{)");

                String mainDecipherFunct;

                mat = patMainVariable.matcher(javascriptFile);
                if (mat.find()) {
                    mainDecipherFunct = "var " + decipherFunctionName + mat.group(2);
                } else {
                    Pattern patMainFunction = Pattern.compile("function " + decipherFunctionName.replace("$", "\\$") +
                            "(\\((.{1,3})\\)\\{)");
                    mat = patMainFunction.matcher(javascriptFile);
                    if (!mat.find())
                        return false;
                    mainDecipherFunct = "function " + decipherFunctionName + mat.group(2);
                }

                int startIndex = mat.end();

                for (int braces = 1, i = startIndex; i < javascriptFile.length(); i++) {
                    if (braces == 0 && startIndex + 5 < i) {
                        mainDecipherFunct += javascriptFile.substring(startIndex, i) + ";";
                        break;
                    }
                    if (javascriptFile.charAt(i) == '{')
                        braces++;
                    else if (javascriptFile.charAt(i) == '}')
                        braces--;
                }
                decipherFunctions = mainDecipherFunct;
                // Search the main function for extra functions and variables
                // needed for deciphering
                // Search for variables
                mat = patVariableFunction.matcher(mainDecipherFunct);
                while (mat.find()) {
                    String variableDef = "var " + mat.group(2) + "={";
                    if (decipherFunctions.contains(variableDef)) {
                        continue;
                    }
                    startIndex = javascriptFile.indexOf(variableDef) + variableDef.length();
                    for (int braces = 1, i = startIndex; i < javascriptFile.length(); i++) {
                        if (braces == 0) {
                            decipherFunctions += variableDef + javascriptFile.substring(startIndex, i) + ";";
                            break;
                        }
                        if (javascriptFile.charAt(i) == '{')
                            braces++;
                        else if (javascriptFile.charAt(i) == '}')
                            braces--;
                    }
                }
                // Search for functions
                mat = patFunction.matcher(mainDecipherFunct);
                while (mat.find()) {
                    String functionDef = "function " + mat.group(2) + "(";
                    if (decipherFunctions.contains(functionDef)) {
                        continue;
                    }
                    startIndex = javascriptFile.indexOf(functionDef) + functionDef.length();
                    for (int braces = 0, i = startIndex; i < javascriptFile.length(); i++) {
                        if (braces == 0 && startIndex + 5 < i) {
                            decipherFunctions += functionDef + javascriptFile.substring(startIndex, i) + ";";
                            break;
                        }
                        if (javascriptFile.charAt(i) == '{')
                            braces++;
                        else if (javascriptFile.charAt(i) == '}')
                            braces--;
                    }
                }

                if (LOGGING)
                    System.out.println("Decipher Function: " + decipherFunctions);
                decipherViaWebView(encSignatures);
                if (CACHING) {
                    writeDeciperFunctToChache();
                }
            } else {
                return false;
            }
        } else {
            decipherViaWebView(encSignatures);
        }
        return true;
    }

    private void parseVideoMeta(String getVideoInfo) {
        boolean isLiveStream = false;
        String title = null, author = null, channelId = null, shortDescript = null;
        long viewCount = 0, length = 0;
        Matcher mat = patTitle.matcher(getVideoInfo);
        if (mat.find()) {
            title = mat.group(1);
        }

        mat = patHlsvp.matcher(getVideoInfo);
        if (mat.find())
            isLiveStream = true;

        mat = patAuthor.matcher(getVideoInfo);
        if (mat.find()) {
            author = mat.group(1);
        }
        mat = patChannelId.matcher(getVideoInfo);
        if (mat.find()) {
            channelId = mat.group(1);
        }
        mat = patShortDescript.matcher(getVideoInfo);
        if (mat.find()) {
            shortDescript = mat.group(1);
        }

        mat = patLength.matcher(getVideoInfo);
        if (mat.find()) {
            length = Long.parseLong(mat.group(1));
        }
        mat = patViewCount.matcher(getVideoInfo);
        if (mat.find()) {
            viewCount = Long.parseLong(mat.group(1));
        }
        videoMeta = new VideoMeta(videoID, title, author, channelId, length, viewCount, isLiveStream, shortDescript);
    }

    private void readDecipherFunctFromCache() {
        File cacheFile = new File(cacheDirPath + "/" + CACHE_FILE_NAME);
        // The cached functions are valid for 2 weeks
        if (cacheFile.exists() && (System.currentTimeMillis() - cacheFile.lastModified()) < 1209600000) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile), "UTF-8"));
                decipherJsFileName = reader.readLine();
                decipherFunctionName = reader.readLine();
                decipherFunctions = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * Include the webm format files into the result. Default: true
     */
    public void setIncludeWebM(boolean includeWebM) {
        this.includeWebM = includeWebM;
    }


    /**
     * Set default protocol of the returned urls to HTTP instead of HTTPS.
     * HTTP may be blocked in some regions so HTTPS is the default value.
     * <p/>
     * Note: Enciphered videos require HTTPS so they are not affected by
     * this.
     */
    public void setDefaultHttpProtocol(boolean useHttp) {
        this.useHttp = useHttp;
    }

    private void writeDeciperFunctToChache() {
        File cacheFile = new File(cacheDirPath + "/" + CACHE_FILE_NAME);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cacheFile), "UTF-8"));
            writer.write(decipherJsFileName + "\n");
            writer.write(decipherFunctionName + "\n");
            writer.write(decipherFunctions);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void decipherViaWebView(final Map<Integer, String> encSignatures) {

        try {
            final StringBuilder stb = new StringBuilder(decipherFunctions + " function decipher(");
            stb.append("){return ");
            ArrayList<Map.Entry<Integer, String>> entries = new ArrayList<>(encSignatures.entrySet());
            for (int i = 0; i < encSignatures.size(); i++) {
                Map.Entry<Integer, String> entry = entries.get(i);
                Integer key = entry.getKey();
                if (i < encSignatures.size() - 1)
                    stb.append(decipherFunctionName).append("('").append(encSignatures.get(key)).
                            append("')+\"\\n\"+");
                else
                    stb.append(decipherFunctionName).append("('").append(encSignatures.get(key)).
                            append("')");
            }
            stb.append("};var resultA=decipher();");

            ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
            engine.eval(stb.toString());
            Object resultA = engine.get("resultA");
            decipheredSignature = (String) resultA;

        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }

    }


}
