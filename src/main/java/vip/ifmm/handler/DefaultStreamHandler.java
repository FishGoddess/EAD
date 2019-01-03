package vip.ifmm.handler;

import java.io.IOException;

/**
 * 默认流处理器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:29:51
 */
public class DefaultStreamHandler implements StreamHandler {

    // 是否终止程序
    private volatile boolean exited = false;

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
        System.out.println(line); // TODO 暂时通过控制台显示出来
    }
}
