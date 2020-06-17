package liusheng.handler.http;

public interface DownloaderController {
    int INIT = -1;
    int EXECUTE = 0;
    int PAUSE = 1;
    int CANCEL = 2;
    int DOWNLOAD_FINISHED = 3;
    int MERGE_FINISHED = 4;
    int EXCEPTION = 5;

    void pause();

    void cancel();

    int getState();

    void setState(int state);
}