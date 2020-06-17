package liusheng.handler.youtube;

import liusheng.handler.RetryFailurerHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 2020年:  05 月:  26 日:  19小时:  00分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class FileRetryFailurerHandler implements RetryFailurerHandler {
    private final String fileName;
    private final BufferedWriter writer;
    private List<String> list = new ArrayList<>();

    public String getFileName() {
        return fileName;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public FileRetryFailurerHandler(String fileName) {
        this.fileName = fileName;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void handle(Object source, Throwable throwable) throws Exception {
        String string = source.toString();
        if (list.contains(string)) {
            return;
        }
        list.add(string);
        writer.write(string);
        writer.newLine();
        writer.flush();
    }
}
