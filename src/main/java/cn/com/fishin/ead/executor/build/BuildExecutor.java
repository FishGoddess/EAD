package cn.com.fishin.ead.executor.build;

/**
 * 编译代码执行器
 *
 * @author Fish
 * ------> 1149062639@qq.com
 * created by 2019/01/04 00:09:16
 */
public interface BuildExecutor {

    // 打包项目为 war 文件和文件夹
    boolean packageProject();

    // 将项目部署到指定文件夹
    boolean deployProject();
}
