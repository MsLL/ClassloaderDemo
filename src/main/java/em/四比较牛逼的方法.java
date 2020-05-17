package em;

import cl.ReCL;
import com.company.A;
import com.company.B;
import util.ReRun;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Frank on 2019/9/22.
 */
public class 四比较牛逼的方法 {
    private static boolean flag = true;

    public static void setFlag(){
        flag = false;
    }
    public static void main(String[] args) throws Exception{
        //NOTE-UPUP 2020/5/18 上午12:12 : 检查标志，避免死递归  reRun里面会用自己的loader重新加载Main类，跑main---666
        if(flag) ReRun.reRun(System.getProperty("user.dir")+ File.separator +"target"+ File.separator +"classes",四比较牛逼的方法.class.getName(),args);

        //NOTE-UPUP 2020/5/18 上午12:17 : 放在else里面 而不是在ReRun.reRun里面调System.exit是不是优雅一些？--
        new A().run();
        new B().run();
    }
}
