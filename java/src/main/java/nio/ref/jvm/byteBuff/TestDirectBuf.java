package nio.ref.jvm.byteBuff;

import sun.misc.Cleaner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TestDirectBuf {
    /**
     * 测试： 离堆内存的回收
     */

    public static void main(String[] args) throws Exception {
        ByteBuffer buf22 = ByteBuffer.allocateDirect(1024 * 1024 * 2000);

        /**
         * 创建： 回收器 cleaner
         */
        Class<?> directBuf = Class.forName("java.nio.DirectByteBuffer");
        Method clean = directBuf.getDeclaredMethod("cleaner");
        clean.setAccessible(true);

        //分配， 回收
//        Constructor<?> cons = directBuf.getDeclaredConstructor(int.class);
//        cons.setAccessible(true);
//        Object buf = cons.newInstance(1024 * 1024 * 500);

        //回收
        Cleaner cleaner = (Cleaner) clean.invoke(buf22);
        cleaner.clean();

    }
}
