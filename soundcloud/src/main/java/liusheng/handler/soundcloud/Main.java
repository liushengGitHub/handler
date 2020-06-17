package liusheng.handler.soundcloud;

import liusheng.handler.BootStrap;
import liusheng.handler.DataInsert;
import liusheng.handler.soundcloud.handler.SoundCloudDocumentHandler;
import liusheng.handler.soundcloud.handler.SoundCloudUserHandler;
import liusheng.handler.soundcloud.handler.SoundDownloaderHandler;
import liusheng.handler.soundcloud.handler.SoundUrlHandler;

import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * 2020年:  05 月:  27 日:  10小时:  40分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Main {
    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();

        DataInsert insert = bootStrap
                .addHandler(new SoundCloudDocumentHandler())
                .addHandler(new SoundUrlHandler())
                .addHandler(new SoundDownloaderHandler("F:\\cloudHello"))
                .addHandler(new SoundCloudUserHandler())
                .mainExecutorService(Executors.newFixedThreadPool(2))
                .helpExecutorService(Executors.newFixedThreadPool(2))
                .start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            insert.addData(scanner.nextLine());
        }
    }
}
