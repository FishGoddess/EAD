package cn.com.fishin.ead.executor.vcs;

/**
 * 版本控制工具执行器接口
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/03 00:10:32
 */
public interface VCSExecutor {

    // 从远程仓库克隆项目
    boolean cloneProject();

    // 从当前分支更新项目
    boolean updateProject();
}
