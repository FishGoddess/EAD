package vip.ifmm.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 全局配置
 * 一些常量，在 classpath 中定义的 config.properties 文件的常量类版本
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/02 23:42:45
 */
public class Config {

    // 记录日志
    private static final Log log = LogFactory.getLog(Config.class);

    // 全局配置
    private static Properties config = new Properties();

    static {
        try {
            config.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            log.warn("加载配置异常！", e);
        }
    }

    // 如果得到的值是 null 或 "", "  " 这类的空串就返回默认值
    private static String getValueSafely(String key, String defaultValue) {
        String value = config.getProperty(key);
        return (value == null || "".equals(value.trim())) ? defaultValue : value;
    }

    // 默认字符集
    public static final String DEFAULT_CHARSET = getValueSafely("charset", "UTF8");

    // 克隆项目地址
    public static final String CLONE_URL = getValueSafely("clone_url", "");

    // 克隆项目保存位置
    public static final String CLONE_LOCATION = getValueSafely("clone_location", "");

    // GIT 命令位置
    public static final String VCS_COMMAND = getValueSafely("vcs_command", "git");

    // MAVEN 命令位置
    public static final String BUILD_COMMAND = getValueSafely("build_command", "mvn");

    // 项目部署位置
    public static final String DEPLOY_LOCATION = getValueSafely("deploy_location", "");

    // 项目部署名称
    public static final String DEPLOY_NAME = getValueSafely("deploy_name", "");

    // 部署实现类名称
    public static final String DEPLOY_EXECUTOR_CLASS = getValueSafely("deploy_executor_class", "vip.ifmm.executor.deploy.UnixDeployExecutor");

    // 版本控制工具执行器
    public static final String VCS_EXECUTOR_CLASS = getValueSafely("vcs_executor_class", "vip.ifmm.executor.vcs.GitVCSExecutor");

    // 项目构建工具执行器
    public static final String BUILD_EXECUTOR_CLASS = getValueSafely("build_executor_class", "vip.ifmm.executor.build.MavenBuildExecutor");

    // http web 使用的端口
    public static final String HTTP_PORT = getValueSafely("http_port", "9090");

    // WebSocket 连接使用的端口
    public static final String WEBSOCKET_PORT = getValueSafely("websocket_port", "9091");
}
