package liusheng.handler;


import java.util.Map;
import java.util.Objects;

/**
 * 2020年:  05 月:  25 日:  22小时:  10分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public abstract class AbstractRetryHandler implements Handler {
    private RetryFailurerHandler retryFailurerHandler;

    public AbstractRetryHandler(RetryFailurerHandler retryFailurerHandler) {
        this.retryFailurerHandler = retryFailurerHandler;
    }

    public AbstractRetryHandler() {
        this(null);
    }

    @Override
    public void handle(Object o, HandlerContext handlerContext) throws Exception {
        Object data = null;
        if (o instanceof HandlerData) {
            data = ((HandlerData) o).getData();
        }
        if (data == null) {
            data = o;
        }
        int i = 0;
        Map<String, Object> dataMap = handlerContext.data();
        HandlerListener youtubeDownlaodListener = (HandlerListener) dataMap.getOrDefault("youtubeDownloadListener", HandlerListener.DEFAULT);

        if (youtubeDownlaodListener != HandlerListener.DEFAULT && !getExecuteListen()) {
            youtubeDownlaodListener = HandlerListener.DEFAULT;
        }
        Throwable throwable = null;
        int retryCount = getRetryCount();
        for (; i < retryCount; i++) {
            try {
                handle0(data, o, handlerContext);
                youtubeDownlaodListener.downloadSuccuce();
                break;
            } catch (ParseExcetion parseExcetion) {
                youtubeDownlaodListener.parseFail();
                return;
            } catch (NoRetryExcetion e) {
                e.printStackTrace();
                youtubeDownlaodListener.cancel();
                return;
            } catch (Exception e) {
                throwable = e;
                e.printStackTrace();
            }
        }

        if (i == retryCount && Objects.nonNull(retryFailurerHandler)) {
            youtubeDownlaodListener.downloadException();
            retryFailurerHandler.handle(data, throwable);
        }
    }

    protected boolean getExecuteListen() {
        return false;
    }

    protected int getRetryCount() {
        return 3;
    }

    protected abstract void handle0(Object handlerDataData, Object origin, HandlerContext handlerContext) throws Exception;

    @Override
    public boolean support(Object o, HandlerContext handlerContext) throws Exception {
        if (Objects.isNull(o)) return false;
        Object data = null;
        if (o instanceof HandlerData) {
            data = ((HandlerData) o).getData();
        }
        if (data == null) {
            data = o;
        }
        return support0(data, o, handlerContext);
    }

    protected abstract boolean support0(Object handlerDataData, Object origin, HandlerContext handlerContext);
}
