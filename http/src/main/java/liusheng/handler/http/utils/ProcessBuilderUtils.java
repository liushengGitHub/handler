package liusheng.handler.http.utils;


import java.util.Objects;
import java.util.Scanner;

public class ProcessBuilderUtils {
    public static void executeAndDiscardOuput(String... commands) throws Exception {
        executeAndDiscardOuput(null,"", commands);
    }

    public static void main(String[] args) throws Exception {
        executeAndDiscardOuput("explorer","/e,","\"F:\\mp3\"");
    }

    public static void executeAndDiscardOuput(ConverterListener converterListener, String type,String... commands) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(commands);
        Process process = builder
                .redirectErrorStream(true)
                .start();
        try {


            Scanner scanner = new Scanner(process.getInputStream());
            boolean isNull = Objects.isNull(converterListener);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!isNull) {
                    try {
                        converterListener.listen(line, type);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            process.waitFor();

        } catch (Exception e) {
            throw e;
        } finally {
            process.destroy();
        }
    }
}
