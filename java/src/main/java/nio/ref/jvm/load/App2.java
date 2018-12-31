package nio.ref.jvm.load;

import nio.ref.jvm.ClassLoader1;

import java.lang.reflect.Method;
/**
 *
 */
public class App2 {
	public static void main(String[] args) throws Exception {
		ClassLoader loader = new ClassLoader1();//new MyClassLoader();
		Class clz = loader.loadClass("Student") ;

//		Method m = clz.getDeclaredMethod("say", String.class);
//		m.invoke(null,"aaa");

		Object o = clz.newInstance();
		Method m2 = clz.getDeclaredMethod("say2", String.class);
		m2.invoke(o,"aaa");
	}
}
