package cn.com.fishin.ead;

import cn.com.fishin.ead.common.Environment;
import cn.com.fishin.ead.executor.build.BuildExecutor;
import cn.com.fishin.ead.executor.vcs.VCSExecutor;
import cn.com.fishin.ead.net.NioServer;
import cn.com.fishin.ead.net.http.HttpServerInitializer;
import cn.com.fishin.ead.net.websocket.WebSocketServerInitializer;

/**
 * 主程序
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:51:00
 */
public class Main {

    // 执行器
    private static final VCSExecutor vcsExecutor = Environment.getVCSExecutor();
    private static final BuildExecutor buildExecutor = Environment.getBuildExecutor();

    // 初始化项目
    public static final String INIT = "init";

    // 更新项目
    public static final String UPDATE = "update";

    private static volatile boolean running = false;

    public synchronized static void setRunning(boolean isRunning) {
        Main.running = isRunning;
    }

    public synchronized static boolean isRunning() {
        return running;
    }

    public static void main(String[] args) throws InterruptedException {

        // 初始化 websocket 服务器
        NioServer webSocketServer = new NioServer();
        prepareWebSocketServer(webSocketServer);
        while (!webSocketServer.isReady()) {
            Thread.sleep(100); // 等待 100 ms 看看服务器是否准备完毕
        }

        // 初始化 http 服务器
        NioServer httpServer = new NioServer();
        httpServer.open(Environment.getHttpPort(), new HttpServerInitializer());
        httpServer.closeGracefully();
    }

    // 初始化 websocket 服务器
    private static void prepareWebSocketServer(NioServer webSocketServer) {

        // 新线程作为 websocket 服务器
        new Thread(()->{
            // open 方法是阻塞的
            try {
                webSocketServer.open(Environment.getWebSocketPort(), new WebSocketServerInitializer());
            } catch (InterruptedException e) {
                // ignore 不处理这个异常
            }
            webSocketServer.closeGracefully();
        }).start();
    }

    // 初始化项目
    // 该方法必须是同步而且强制锁整个对象
    public synchronized static boolean init() {
        // 只有前一个步骤执行成功才继续执行
        return vcsExecutor.cloneProject() && buildExecutor.packageProject() && buildExecutor.deployProject();
    }

    // 更新项目
    // 该方法必须是同步而且强制锁整个对象
    public synchronized static boolean update() {
        // 只有前一个步骤执行成功才继续执行
        return vcsExecutor.updateProject() && buildExecutor.packageProject() && buildExecutor.deployProject();
    }
}
