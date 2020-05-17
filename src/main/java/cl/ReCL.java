package cl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Frank on 2019/9/22.
 */
public class ReCL extends ClassLoader {

    private String classpath;

    private Map<String, ClassLoader> classLoaderMap = new Hashtable<>();

    public ReCL(String classpath) {
        this.classpath = classpath;
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(name.startsWith("em")) {
            try {
                byte[] classDate = getDate(name);

                if (classDate == null) {
                } else {
                    //defineClass方法将字节码转化为类
                    return defineClass(name, classDate, 0, classDate.length);
                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        //NOTE-UPUP 2020/5/18 上午12:22 : 对于A和B类，各自用自己的loader加载，做到A，B隔离。----to tomcat隔离？666
        if(name.equals("com.company.A")){
            String baseLibDir = System.getProperty("user.dir") + File.separator + "lib";
            try {
                ClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + baseLibDir + File.separator + "A.jar")},null);
                classLoaderMap.computeIfAbsent(name, k->loader);
                return loader.loadClass(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(name.equals("com.company.B")){
            String baseLibDir = System.getProperty("user.dir") + File.separator + "lib";
            try {
                ClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + baseLibDir + File.separator + "B.jar")},null);
                classLoaderMap.computeIfAbsent(name, k->loader);
                return loader.loadClass(name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //NOTE-UPUP 2020/5/18 上午12:33 : A,B里面还要加载除类C额外的类的话(包括rt下面的类)，这里估计有点问题，ReCL继承的是ClassLoader,但是ClassLoader#loadClass#findClass默认是直接抛异常的
        // ，这里可以再全局维护一个已有的ClassLoader子类示例，用来非A，B类的加载，如：AppClassLoader ---666
        return super.loadClass(name);
    }
    //返回类的字节码
    private byte[] getDate(String className) throws IOException{
        InputStream in = null;
        ByteArrayOutputStream out = null;
        String path=classpath + File.separatorChar +
                className.replace('.',File.separatorChar)+".class";
        try {
            in=new FileInputStream(path);
            out=new ByteArrayOutputStream();
            byte[] buffer=new byte[2048];
            int len=0;
            while((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            return out.toByteArray();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            in.close();
            out.close();
        }
        return null;
    }
}