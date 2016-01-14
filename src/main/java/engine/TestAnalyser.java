package engine;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import util.ClasspathClassLoader;

public class TestAnalyser {

  ClasspathClassLoader clsLoader;
  String actualPath;
  private HashMap<String, List<String>> testClassFailed = new HashMap<String, List<String>>();
  private List<String> noTestClasses = new ArrayList<String>();
  public static final String JUNIT_ANNOTATION = "org.junit.Test";

  public List<String> getNoTestClasses() {
    return noTestClasses;
  }

  public HashMap<String, List<String>> getTestClassFailed() {
    return testClassFailed;
  }

  public TestAnalyser(ClasspathClassLoader clsLoader) {
    this.clsLoader = clsLoader;
  }

  /**
   * Look for every test class in classpath and return every bugged method from every class
   */
  public void analyseWhiteBoxTests() {
    // Get all the folders/jars added to classpath
    URL[] urls = this.clsLoader.getURLs();
    for (URL url : urls) {
      // for every folder run testClasses
      actualPath = url.getFile();
      runAllWhiteBoxTestClasses(new File(actualPath));
    }
  }

  /**
   * Runs a test 
   * @param className the test classname 
   */
  public void runTest(Class<?> className) {
    List<String> testsFailed = new ArrayList<String>();

    for (Method m : className.getDeclaredMethods()) {
      if (m.getAnnotations().length != 0)
        if (m.getAnnotations()[0].annotationType().getName().equals("org.junit.Test")) {
          Request request = Request.method(className, m.getName());
          Result result = new JUnitCore().run(request);
          List<Failure> failures = result.getFailures();

          if (failures.size() != 0) {
            System.out.println("Test failed: " + m.getName());
            testsFailed.add(m.getName());
          }
        }
    }

    // if any test of the class failed
    if (testsFailed.size() != 0)
      testClassFailed.put(className.getName(), testsFailed);
  }

  /**
   * Runs all "WhiteBoxé tests
   * @param path the path
   */
  public void runAllWhiteBoxTestClasses(File path) {
    File listFile[] = path.listFiles();
    if (listFile != null) {
      for (int i = 0; i < listFile.length; i++) {
        if (listFile[i].isDirectory())
          runAllWhiteBoxTestClasses(listFile[i]);
        else if (listFile[i].toString().contains(".class") && listFile[i].toString().contains("Whitebox")) {
          runTest(this.clsLoader.findOrLoadClass(getNameClass(actualPath, listFile[i].toString())));
        } else
          isNotTestClass(this.clsLoader.findOrLoadClass(getNameClass(actualPath, listFile[i].toString())));
      }
    }
  }

  private void isNotTestClass(Class<?> className) {
    Boolean hasTest = false;

    for (Method m : className.getDeclaredMethods()) {
      if (m.getAnnotations().length != 0)
        if (m.getAnnotations()[0].annotationType().getName().equals(JUNIT_ANNOTATION)) {
          hasTest = true;
        }
    }
    if (!hasTest)
      noTestClasses.add(className.getName());
  }

  private String getNameClass(String path, String file) {
    // windows
    String name;
    if (file.contains("\\"))
      name = file.replace(path.substring(1).replace("/", "\\"), "").replace("\\", ".").replace(" ", "").replace(".class", "");
    else
      // linux
      name = file.replace(path.substring(1), "").replace("/", ".").replace(" ", "").replace(".class", "");
    System.out.println(name);
    return name;
  }
}
