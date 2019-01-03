package vip.ifmm.executor;

import vip.ifmm.handler.DefaultStreamHandler;
import vip.ifmm.handler.StreamHandlerWrapper;

/**
 * 默认进程执行器
 * 内部使用默认流处理器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 00:19:31
 */
public final class ProcessExecutor {

    // 执行程序
    public static void execute(Process p) throws InterruptedException {

        // 处理程序数据
        StreamHandlerWrapper streamHandlerWrapper = new StreamHandlerWrapper(new DefaultStreamHandler());
        new Thread(() -> streamHandlerWrapper.handleInput(p.getInputStream())).start(); // 为了让下面的代码进行下去
        new Thread(() -> streamHandlerWrapper.handleInput(p.getErrorStream())).start(); // 为了让下面的代码进行下去

        p.waitFor();
        p.destroy();

        // 关闭流处理器资源
        streamHandlerWrapper.finish();
    }
}
