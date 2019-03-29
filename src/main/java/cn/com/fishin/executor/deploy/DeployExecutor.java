package cn.com.fishin.executor.deploy;

/**
 * 部署处理器接口
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 23:08:43
 */
public interface DeployExecutor {

    // 将 projectDirectory 部署到 deployLocation 下面，部署名字为 deployName
    boolean deploy(String projectRootDirectory, String projectDirectory, String deployLocation, String deployName);
}
