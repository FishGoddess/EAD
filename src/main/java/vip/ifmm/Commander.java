package vip.ifmm;

import java.io.*;

/**
 * 执行外部命令
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2018/08/27 23:02:46
 */
public final class Commander {

    private volatile boolean isExited = false;

    /*public synchronized void setExited(boolean exited) {
        isExited = exited;
    }*/

    public static void main(String[] args) throws Exception {

        //Process p = Runtime.getRuntime().exec(new String[]{"git", "clone", "https://gitee.com/IFMM/BastFiberCrop.git", "Z:/BastFiberCrop"});
        Process p = Runtime.getRuntime().exec(new String[]{"D:\\apache-maven-3.5.4\\bin\\mvn.cmd", "clean", "package", "-Dmaven.test.skip=true"}, null, new File("Z:/t"));

        final Commander commander = new Commander();

        // 标准输入流
        new Thread(() -> commander.handleInput(p.getInputStream())).start();

        // 错误流
        new Thread(() -> commander.handleInput(p.getErrorStream())).start();

        p.waitFor();
        p.destroy();

        commander.close();

    }

    // 处理输入流
    private void handleInput(InputStream is) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "GBK"));
            String line = null;
            while (!this.isExited) {
                line = reader.readLine();
                if (line != null) {
                    System.out.println(line);
                } else {
                    Thread.sleep(500);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            closeInput(reader);
        }
    }

    // 处理输出流
    private void handleOutput(OutputStream os) {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = null;
            while (!this.isExited) {
                line = reader.readLine();
                if (line != null) {

                    writer.write(line);
                    writer.newLine();
                    writer.flush();

                    if ("exit".equals(line.trim())) {
                        System.out.println("结束啦~~");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("程序已经关闭...");
        } finally {
            closeOutput(writer);
            closeInput(reader);
        }
    }

    // 关闭输入流
    private void closeInput(BufferedReader reader) {

        close(); // 释放资源
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭输出流
    private void closeOutput(BufferedWriter writer) {

        close(); // 释放资源
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    // 关闭对象
    private void close() {
        //t.setExited(true);
        this.isExited = true;
    }
}
