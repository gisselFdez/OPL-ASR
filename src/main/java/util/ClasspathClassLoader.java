package util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class ClasspathClassLoader extends URLClassLoader {

  private HashMap<String, Class<?>> loadedClasses;

  public ClasspathClassLoader(URL[] urls) {
    super(urls);
    loadedClasses = new HashMap<String, Class<?>>();
  }

  public void addFileToClassPath(URL url) throws MalformedURLException {
    this.addURL(url);
  }

  /**
   * Find or load classes in the loadedClasses HashMap 
   * @param className 
   * @return
   */
  public Class<?> findOrLoadClass(String className) {
    try {
      Class<?> cls = loadedClasses.get(className);

      if (cls == null) {
        cls = this.loadClass(className);
        loadedClasses.put(className, cls);
      }
      return cls;
    } catch (ClassNotFoundException | IllegalAccessError ex) {
      System.out.println(ex);
      return null;
    }
  }

}
