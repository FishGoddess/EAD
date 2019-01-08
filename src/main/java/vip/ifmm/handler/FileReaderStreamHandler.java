package vip.ifmm.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 默认流处理器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:29:51
 */
public class FileReaderStreamHandler implements StreamHandler {

    // 是否终止程序
    private volatile boolean exited = false;

    // 读取到的所有文件行
    private List<String> lines = new ArrayList<>(80);

    @Override
    public void close() {
        exited = true;
    }

    @Override
    public boolean keepGoing() {
        return !exited;
    }

    @Override
    public void handleReadString(String line) throws IOException {
        lines.add(line);
    }

    @Override
    public void handleNullString() throws Exception {
        close(); // 读取文件的一行为 null，可以退出了
    }

    // 获得读取到的所有行
    public List<String> getLines() {
        return lines;
    }
}
