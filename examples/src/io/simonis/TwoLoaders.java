package io.simonis;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;

public class TwoLoaders {

	public static class XXX {}
	
	public static void main(String[] args) throws Exception {
    URL url = TwoLoaders.class.getResource("TwoLoaders.class");
    if ("jar".equals(url.getProtocol())) {
    	url = ((JarURLConnection)url.openConnection()).getJarFileURL();
    } else {
    	// file:// protocol
    	url = new URL(url.toString().replace("io/simonis/TwoLoaders.class", ""));
    }
    System.out.println(url);
    //Object x0 = ClassLoader.getSystemClassLoader().loadClass("io.simonis.TwoLoaders$XXX").newInstance();
    Object x0 = new XXX();
    System.out.println(x0.getClass().getClassLoader());
    System.out.println(x0.getClass().getClassLoader().getParent());
    System.out.println(x0.getClass().getClassLoader().getParent().getParent());
    ClassLoader cl1 = new URLClassLoader(new URL[] { url }, null);
    ClassLoader cl2 = new URLClassLoader(new URL[] { url }, null);
    Object x1 = cl1.loadClass("io.simonis.TwoLoaders$XXX").newInstance();
    Object x2 = cl2.loadClass("io.simonis.TwoLoaders$XXX").newInstance();
    System.out.println(x1.getClass().getName().equals(x2.getClass().getName()));
    System.out.println(x1.getClass().equals(x2.getClass()));
  }
}
