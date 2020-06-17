package liusheng.handler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 2020年:  05 月:  25 日:  10小时:  31分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class BootStrap {
    private BlockingQueue<Object> blockingQueue = new LinkedBlockingQueue<>();

    private ExecutorService dataThread = Executors.newSingleThreadExecutor();
    private ExecutorService mainExecutorService;
    private ExecutorService helpExecutorService;

    private List<Handler> handlers = new LinkedList<>();

    public BootStrap helpExecutorService(ExecutorService helpExecutorService) {
        this.helpExecutorService = helpExecutorService;
        return this;
    }

    public BootStrap mainExecutorService(ExecutorService mainExecutorService) {
        this.mainExecutorService = mainExecutorService;
        return this;
    }

    public DataInsert start() {
        checkExecuteService();
        QueueDataInsert queueDataInsert = new QueueDataInsert();
        startThread(queueDataInsert);
        return queueDataInsert;
    }

    private void startThread(QueueDataInsert queueDataInsert) {
        dataThread.execute(() -> {
            DefaultPipeline pipeline = new DefaultPipeline(mainExecutorService,helpExecutorService,queueDataInsert);
            handlers.forEach(pipeline::addHandler);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Object object = blockingQueue.take();
                    mainExecutorService.execute(() -> {
                        try {
                            pipeline.handle(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void checkExecuteService() {
        Assert.notNull(mainExecutorService, "mainExecutorService 不能是空");
        Assert.notNull(helpExecutorService, "helpExecutorService 不能是空");
    }

    public BootStrap addHandler(Handler handler) {
        handlers.add(handler);
        return this;
    }

    class QueueDataInsert implements DataInsert {

        @Override
        public void addData(Object data) {
            try {
                blockingQueue.put(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
