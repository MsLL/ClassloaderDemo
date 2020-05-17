package util;

import cl.ReCL;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Created by Frank on 2019/9/22.
 */
public class ReRun {

    public static void reRun(String classpath,String mainClass,String[] args) throws Exception{
        //NOTE-UPUP 2020/5/18 上午12:19 : 直接用自己的ClassLoader重新加载Main类，这样Main类的类加载器就是我们的reCL了，并默认一直传承下去
        ReCL reCL = new ReCL(classpath);
        Class clazz = reCL.loadClass(mainClass);
        Method mainMethod = clazz.getMethod("main",String[].class);

        //NOTE-UPUP 2020/5/18 上午12:12 : 调用setFlag方法，设置标志，避免死递归
        Method setFlagMethod = clazz.getDeclaredMethod("setFlag");
        setFlagMethod.invoke(null);

        //NOTE-UPUP 2020/5/18 上午12:15 : 重新跑main方法
        mainMethod.invoke(null, (Object) args);

        //NOTE-UPUP 2020/5/18 上午12:14 : 直接退出进程，这样外层的mian就不会继续往下执行了
        System.exit(0);
    }
}
