package vip.ifmm;

import vip.ifmm.common.Environment;
import vip.ifmm.executor.build.BuildExecutor;
import vip.ifmm.executor.build.MavenBuildExecutor;
import vip.ifmm.executor.vcs.GitVCSExecutor;
import vip.ifmm.executor.vcs.VCSExecutor;

/**
 * 主程序
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:51:00
 */
public class Main {

    // 执行器
    private static final VCSExecutor git = Environment.getVCSExecutor();
    private static final BuildExecutor mvn = Environment.getBuildExecutor();

    // 初始化项目
    private static final String INIT = "init";

    // 更新项目
    private static final String UPDATE = "update";

    public static void main(String[] args) {

        //init();
        update();
    }

    // 初始化项目
    private static boolean init() {
        // 只有前一个步骤执行成功才继续执行
        return git.cloneProject() && mvn.packageProject() && mvn.deployProject();
    }

    // 更新项目
    private static boolean update() {
        // 只有前一个步骤执行成功才继续执行
        return git.updateProject() && mvn.packageProject() && mvn.deployProject();
    }
}
