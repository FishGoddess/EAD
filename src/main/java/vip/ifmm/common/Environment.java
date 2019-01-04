package vip.ifmm.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import vip.ifmm.executor.deploy.DeployExecutor;
import vip.ifmm.executor.deploy.UnixDeployExecutor;

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
        try {
            // 实例化一个部署实现类
            return (DeployExecutor) Class.forName(Config.DEPLOY_EXECUTOR_CLASS).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // 使用默认的实现类
            return new UnixDeployExecutor();
        }
    }
}
