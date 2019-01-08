package vip.ifmm.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vip.ifmm.executor.build.BuildExecutor;
import vip.ifmm.executor.build.MavenBuildExecutor;
import vip.ifmm.executor.deploy.DeployExecutor;
import vip.ifmm.executor.deploy.UnixDeployExecutor;
import vip.ifmm.executor.vcs.GitVCSExecutor;
import vip.ifmm.executor.vcs.VCSExecutor;

/**
 * 环境变量工具类
 * 比如具体实现类是哪个
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 23:26:28
 */
public class Environment {

    // 记录日志
    private static final Log log = LogFactory.getLog(Environment.class);

    // 获得部署执行器
    public static DeployExecutor getDeployExecutor() {
        return (DeployExecutor) getObjectSafely(Config.DEPLOY_EXECUTOR_CLASS, new UnixDeployExecutor());
    }

    // 获得版本控制工具执行器
    public static VCSExecutor getVCSExecutor() {
        return (VCSExecutor) getObjectSafely(Config.VCS_EXECUTOR_CLASS, new GitVCSExecutor());
    }

    // 获得项目构建工具执行器
    public static BuildExecutor getBuildExecutor() {
        return (BuildExecutor) getObjectSafely(Config.BUILD_EXECUTOR_CLASS, new MavenBuildExecutor());
    }

    // 获得 WebSocket 端口
    public static int getWebSocketPort() {
        return parseInteger(Config.WEBSOCKET_PORT);
    }

    // 获得 http 端口
    public static int getHttpPort() {
        return parseInteger(Config.HTTP_PORT);
    }

    // 优先加载 className 这个类，如果出现异常，则返回 defaultObject
    private static Object getObjectSafely(String className, Object defaultObject) {
        try {
            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

            log.error(className + " 加载失败！使用默认加载类！", e);
            // 返回默认实例化对象
            return defaultObject;
        }
    }

    // 如果数字格式不对就会返回 -1
    private static int parseInteger(String integer) {
        try {
            return Integer.valueOf(integer);
        } catch (NumberFormatException e) {
            log.error("数字格式不对！integer: " + integer, e);
            return -1;
        }
    }
}
