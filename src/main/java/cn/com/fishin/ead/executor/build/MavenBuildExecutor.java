package cn.com.fishin.ead.executor.build;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.com.fishin.ead.common.Config;
import cn.com.fishin.ead.common.Environment;
import cn.com.fishin.ead.executor.ProcessExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 默认的代码打包工具执行器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 00:10:02
 */
public class MavenBuildExecutor implements BuildExecutor {

    // 记录日志
    private static final Log log = LogFactory.getLog(MavenBuildExecutor.class);

    // build 之后产生 war 的 target 文件夹
    private static final String WAR_DIRECTORY = Config.CLONE_LOCATION + File.separator + "target";

    @Override
    public boolean packageProject() {

        try {
            // 执行 mvn clean package 命令
            ProcessExecutor.execute(Runtime.getRuntime().exec(new String[]{
                    Config.BUILD_COMMAND,
                    "clean",
                    "package",
                    "-Dmaven.test.skip=true"
            }, null, new File(Config.CLONE_LOCATION)));

            return true;
        } catch (IOException | InterruptedException e) {
            log.error("clean package 失败！project: [" + Config.CLONE_LOCATION + "]", e);

            return false;
        }
    }

    @Override
    public boolean deployProject() {
        String projectDirectory = getProjectDirectory();
        if (projectDirectory == null) {
            return false;
        }

        // 执行部署
        Environment.getDeployExecutor().deploy(WAR_DIRECTORY,
                projectDirectory,
                Config.DEPLOY_LOCATION,
                Config.DEPLOY_NAME);

        return true;
    }

    // 从 target 目录中找到项目打包好的目录名
    private static String getProjectDirectory() {
        // 找到 .war 文件的名称即可
        Stream<Path> paths = null;
        try {
            paths = Files.list(Paths.get(WAR_DIRECTORY));
        } catch (IOException e) {
            log.error("deploy 失败！war_directory: [" + WAR_DIRECTORY + "]", e);
            return null;
        }

        // paths 为 null 的话就说明没有打包好的项目了
        if (paths == null) {
            return null;
        }

        String war = null;
        Object[] pathsArray = paths.toArray();
        String fileName = null;
        Path path = null;
        for (Object obj : pathsArray) {
            path = (Path) obj;
            fileName = path.toFile().getName();
            if (fileName.endsWith(".war")) {
                war = fileName;
                break;
            }
        }

        // 返回去掉 .war 后缀的
        return war != null ? war.substring(0, war.indexOf(".war")) :null;
    }
}
