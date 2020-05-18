package em;

import com.company.*;

/**
 * Created by Frank on 2019/9/22.
 */
//NOTE-UPUP 2020/5/17 下午11:08 : 类名真用真用中文名--有时候也可以以，
//NOTE-UPUP 2020/5/18 上午5:49 : 可以通过调整classpath中A.jar和B.jar的顺序来确定用哪个。eclipse中可以，idea自然也可以。或者直接在.iml文件中调orderEntry顺序---666
public class 一啥也不改报错 {
    public static void main(String[] args) {
        new A().run();
        new B().run();
    }
}
